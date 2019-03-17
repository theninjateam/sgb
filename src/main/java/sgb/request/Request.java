package sgb.request;

import org.zkoss.zul.ListModelList;
import sgb.concurrence.ObraConcurrenceControl;
import sgb.controller.domainController.ConfigControler;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Request
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;

    private CRUDService crudService;
    private ConfigControler configControler;
    private EstadoPedidoControler estadoPedidoControler;
    private EstadoDevolucaoControler estadoDevolucaoControler;
    private ObraConcurrenceControl obraConcurrenceControl;
    private Queue queue;
    
    public Request(CRUDService crudService,
                   ConfigControler configControler,
                   EstadoPedidoControler estadoPedidoControler,
                   ObraConcurrenceControl obraConcurrenceControl,
                   Queue queue)
    {
        this.configControler = configControler;
        this.crudService = crudService;
        this.estadoPedidoControler = estadoPedidoControler;
        this.obraConcurrenceControl = obraConcurrenceControl;
        this.queue = queue;
    }

    public void request(Item item, Users user)
    {
        try
        {
            this.obraConcurrenceControl.enterInCriticalRegion(item.getObra());
            validate(item);

            Emprestimo emprestimo = getEmprestimo(item, user);

            if (!item.getIsLineUp())
            {
                Obra obra = this.crudService.get(Obra.class, item.getObra().getCota());
                obra.setQuantidade(obra.getQuantidade() - item.getQuantidade());
                this.crudService.update(obra);
            }

            this.crudService.Save(emprestimo);
            this.obraConcurrenceControl.leaveInCriticalRegion(item.getObra());
        }
        catch (Exception ex)
        {
            this.obraConcurrenceControl.leaveInCriticalRegion(item.getObra());
            ex.printStackTrace();
        }
    }

    public void validate(Item item)
    {
        if (item.getIsHomeRequisition())
        {
            item.setHomeRequisition(canDoHomeRequisition(item.getObra()));
        }

        item.setLineUp(canLineUp(item.getObra(), item.getQuantidade()));
    }

    public boolean canDoHomeRequisition(Obra obra)
    {
        int qtdMin = this.configControler.MINIMUM_NUMBER_OF_COPIES;
        int qtd = getAvailableNumberOfCopies(obra);

        return qtd > qtdMin ? true : false;
    }

    public boolean canLineUp(Obra obra, int qtd)
    {
        int qtdMin = this.configControler.MINIMUM_NUMBER_OF_COPIES;
        int qtdDis = this.crudService.get(Obra.class, obra.getCota()).getQuantidade();

        if (canDoHomeRequisition(obra))
        {
            return  qtdDis - qtd < qtdMin ? true : false;
        }
        else
        {
            return  qtdDis  - qtd < 0 ? true : false;
        }
    }

    public int getAvailableNumberOfCopies(Obra obra)
    {
        int qtd =  this.getRequest(obra, this.estadoPedidoControler.PENDING).size();
        qtd += this.getRequest(obra, this.estadoPedidoControler.ACCEPTED).size();
        qtd += this.crudService.get(Obra.class, obra.getCota()).getQuantidade();

        return qtd;
    }

    public void cancel(Emprestimo e)
    {
        boolean wasReserved = false;
        try
        {
            Emprestimo emprestimo = getRequest(e.getEmprestimoPK());
            EstadoPedido estadoPedido = this.crudService.get(EstadoPedido.class, this.estadoPedidoControler.CANCELED);
            emprestimo.setEstadoPedido(estadoPedido);
            emprestimo.setComentario("Cancelado Pelo Sistema, excedeu o deadline");

            if (!this.queue.getQueue(e.getEmprestimoPK().getObra()).isEmpty())
            {
                Emprestimo emp = this.queue.getQueue(e.getEmprestimoPK().getObra()).remove();
                wasReserved = reserve(emp, e.getQuantidade());
            }

            if (!wasReserved)
            {
                this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());
                Obra obra = this.crudService.get(Obra.class, e.getEmprestimoPK().getObra().getCota());
                obra.setQuantidade(obra.getQuantidade() + e.getQuantidade());
                this.crudService.update(obra);
                this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());
            }

            this.crudService.update(emprestimo);
        }
        catch (Exception ex)
        {
            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());
            ex.printStackTrace();
        }
    }

    public void multar (Emprestimo e) {
        Emprestimo emprestimo = getRequest(e.getEmprestimoPK());
        EstadoDevolucao estadoDevolucao = this.crudService.get(EstadoDevolucao.class, this.estadoDevolucaoControler.NOT_RETURNED);

        if(emprestimo.getEstadoDevolucao().equals(estadoDevolucao)) {
            /*
            *  Gera a multa e atualiza a tabela emprestimo com estado de devolucao NOT_RETURNED
            */
            emprestimo.setEstadoDevolucao(estadoDevolucao);

            Multa multa =new Multa();
            multa.setMultaPK(emprestimo.getEmprestimoPK());

            EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,2);

            multa.setDataemissao(Calendar.getInstance());
            multa.setDataemprestimo(emprestimo.getDataaprovacao());
            multa.setEstadoMulta(estadoMulta);

            int diaatraso = -1 * (int) Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays();

            multa.setDiasatraso(diaatraso );

            Float taxaD = Float.parseFloat(crudService.get(Config.class,"DAILY_RATE_FINE").getValor());
            multa.setTaxadiaria(taxaD);

            multa.setValorpago((diaatraso*taxaD));
            this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());
            crudService.Save(multa);
            crudService.update(emprestimo);
            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());


        }
         else
        {
            /*
            *atualiza a multa nos campos de dias de atraso e total a pagar
            */
            Multa multa =new Multa();
            multa = crudService.get(Multa.class,emprestimo.getEmprestimoPK());

            int diaatraso = -1 * (int) Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays();

            multa.setDiasatraso(diaatraso );

            Float taxaD = Float.parseFloat(crudService.get(Config.class,"DAILY_RATE_FINE").getValor());
            multa.setTaxadiaria(taxaD);

            multa.setValorpago((diaatraso*taxaD));
            this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());
            crudService.update(multa);
            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());

        }



    }


    public boolean reserve(Emprestimo e, int qtdObras)
    {
        try
        {
            if (qtdObras > e.getQuantidade())
            {
                qtdObras = qtdObras - e.getQuantidade();
            }
            else if (qtdObras < e.getQuantidade())
            {
                e.setQuantidade(qtdObras);
            }

            e.setEstadoPedido(this.crudService.get(EstadoPedido.class, this.estadoPedidoControler.PENDING_AFTER_BEING_IN_QUEUE));
            e.getEmprestimoPK().setDataentradapedido(Calendar.getInstance());

            this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());

            if (qtdObras > 0)
            {
                Obra obra = this.crudService.get(Obra.class, e.getEmprestimoPK().getObra().getCota());
                obra.setQuantidade(obra.getQuantidade() + qtdObras);
                this.crudService.update(obra);
            }

            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());
            this.crudService.update(e);

            return true;
        }
        catch (Exception ex)
        {
            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());
            ex.printStackTrace();
            return false;
        }
    }

    public Emprestimo getEmprestimo(Item item, Users user)
    {
        EmprestimoPK emprestimoPK = new EmprestimoPK();
        Emprestimo emprestimo = new Emprestimo();

        EstadoPedido estadoPedido = null;
        EstadoDevolucao estadoDevolucao = null;
        EstadoRenovacao estadoRenovacao = null;
        TipoRequisicao tipoRequisicao =  null;

        emprestimoPK.setObra(item.getObra());
        emprestimoPK.setUtente(user);
        emprestimoPK.setDataentradapedido(Calendar.getInstance());

        emprestimo.setEstadoDevolucao(estadoDevolucao);
        emprestimo.setEstadoPedido(estadoPedido);
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setComentario("--");
        emprestimo.setDataaprovacao(null);
        emprestimo.setDatadevolucao(null);
        emprestimo.setQuantidade(item.getQuantidade());
        emprestimo.setEstadoRenovacao(estadoRenovacao);
        emprestimo.setTipoRequisicao(tipoRequisicao);

        return emprestimo;
    }

    public List<Emprestimo> getRequest(Obra obra, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("cota", obra.getCota());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.obra.cota = :cota");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Emprestimo> getRequest(int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public ListModelList<Emprestimo> getRequest(Users user, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("userId", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.utente.id = :userId");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }


    public Emprestimo getRequest(EmprestimoPK emprestimoPK)
    {
        parameters = new HashMap<String, Object>(3);
        query = new StringBuilder();

        parameters.put("user_id", emprestimoPK.getUtente().getId());
        parameters.put("cota", emprestimoPK.getObra().getCota());
        parameters.put("dataentrada", emprestimoPK.getDataentradapedido());


        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = :user_id and ");
        query.append("e.emprestimoPK.obra.cota = :cota and e.emprestimoPK.dataentrada = :dataentrada");

        return this.crudService.findEntByJPQueryT(query.toString(), parameters);
    }
}
