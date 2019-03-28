package sgb.request;

import sgb.concurrence.ObraConcurrenceControl;
import sgb.controller.domainController.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.time.Duration;
import java.util.Calendar;

public class Request
{
    private CRUDService crudService;
    private ConfigControler configControler;
    private EstadoPedidoControler estadoPedidoControler;
    private EstadoDevolucaoControler estadoDevolucaoControler;
    private ObraConcurrenceControl obraConcurrenceControl;
    private TipoRequisicaoControler tipoRequisicaoControler;
    private Queue queue;
    private EmprestimoController eController;
    
    public Request(CRUDService crudService,
                   ConfigControler configControler,
                   EstadoPedidoControler estadoPedidoControler,
                   ObraConcurrenceControl obraConcurrenceControl,
                   Queue queue,
                   EmprestimoController eController)
    {
        this.configControler = configControler;
        this.crudService = crudService;
        this.estadoPedidoControler = estadoPedidoControler;
        this.obraConcurrenceControl = obraConcurrenceControl;
        this.queue = queue;
        this.eController = eController;
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
        int qtd =  this.eController.getRequest(obra, this.estadoPedidoControler.PENDING_MINI_BOOKING).size();
        qtd += this.eController.getRequest(obra, this.estadoPedidoControler.ACCEPTED).size();
        qtd += this.eController.getRequest(obra, this.estadoPedidoControler.PENDING_BOOKING).size();
        qtd += this.crudService.get(Obra.class, obra.getCota()).getQuantidade();

        return qtd;
    }

    public void cancel(Emprestimo e)
    {
        int idEstadoPedido = e.getEstadoPedido().getIdestadopedido();

        boolean wasReserved = false;
        try
        {
            Emprestimo emprestimo = this.eController.getRequest(e.getEmprestimoPK());
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
                try
                {
                    this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());
                    Obra obra = this.crudService.get(Obra.class, e.getEmprestimoPK().getObra().getCota());
                    obra.setQuantidade(obra.getQuantidade() + e.getQuantidade());
                    this.crudService.update(obra);
                    this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());
                }
            }

            this.crudService.update(emprestimo);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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

            e.setEstadoPedido(this.crudService.get(EstadoPedido.class, this.estadoPedidoControler.PENDING_BOOKING));
            e.setDataaprovacao(Calendar.getInstance());

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

        EstadoPedido estadoPedido = crudService.get(EstadoPedido.class,estadoPedidoControler.PENDING_MINI_BOOKING);
        EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, estadoDevolucaoControler.UNDETERMINED);
        EstadoRenovacao estadoRenovacao = null;

        TipoRequisicao tipoRequisicao = crudService.get(TipoRequisicao.class,tipoRequisicaoControler.INTERNAL_REQUISITION);

        if (item.getIsHomeRequisition()) {
            tipoRequisicao = crudService.get(TipoRequisicao.class, tipoRequisicaoControler.HOME_REQUISITION);
        }
        emprestimo.setTipoRequisicao(tipoRequisicao);


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
}
