package sgb.request;

import sgb.concurrence.ObraConcurrenceControl;
import sgb.controller.domainController.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
                   EmprestimoController eController,
                   EstadoDevolucaoControler estadoDevolucaoControler,
                   TipoRequisicaoControler tipoRequisicaoControler)
    {
        this.configControler = configControler;
        this.crudService = crudService;
        this.estadoPedidoControler = estadoPedidoControler;
        this.obraConcurrenceControl = obraConcurrenceControl;
        this.queue = queue;
        this.eController = eController;
        this.estadoDevolucaoControler = estadoDevolucaoControler;
        this.tipoRequisicaoControler = tipoRequisicaoControler;
    }

    public void request(List<Item> itens, Users user)
    {
        for (Item item: itens)
        {
            try
            {
                this.obraConcurrenceControl.enterInCriticalRegion(item.getObra());

                if (item.isHomeRequisition() && this.canDoHomeRequisition(item.getObra(), item.getQuantidade()))
                {
                    if (item.getObra().getQuantidade() - item.getQuantidade() >=
                            this.configControler.MINIMUM_NUMBER_OF_COPIES)
                    {
                        item.setEstadoPedido(this.crudService.get(EstadoPedido.class, this.estadoPedidoControler.PENDING_MINI_BOOKING));
                        Emprestimo emprestimo = getEmprestimo(item, user);

                        Obra obra = this.crudService.get(Obra.class, item.getObra().getCota());
                        obra.setQuantidade(obra.getQuantidade() - item.getQuantidade());
                        this.crudService.update(obra);
                        this.crudService.Save(emprestimo);
                    }
                    else if(item.isLineUp())
                    {
                        item.setEstadoPedido(this.crudService.get(EstadoPedido.class, this.estadoPedidoControler.ON_WAINTING_QUEUE));
                        Emprestimo emprestimo = getEmprestimo(item, user);
                        this.crudService.Save(emprestimo);
                    }
                }
                else if (item.isInternalRequisition() && this.canDoInternalRequisition(item.getObra(), item.getQuantidade()))
                {
                    item.setEstadoPedido(this.crudService.get(EstadoPedido.class, this.estadoPedidoControler.PENDING_MINI_BOOKING));
                    Emprestimo emprestimo = getEmprestimo(item, user);

                    Obra obra = this.crudService.get(Obra.class, item.getObra().getCota());
                    obra.setQuantidade(obra.getQuantidade() - item.getQuantidade());
                    this.crudService.update(obra);
                    this.crudService.Save(emprestimo);
                }

                this.obraConcurrenceControl.leaveInCriticalRegion(item.getObra());
            }
            catch (Exception ex)
            {
                this.obraConcurrenceControl.leaveInCriticalRegion(item.getObra());
                ex.printStackTrace();
            }
        }
    }

    public boolean canDoHomeRequisition(Obra obra, int qtd)
    {
        int qtdMin = this.configControler.MINIMUM_NUMBER_OF_COPIES;
        int all = getAvailableNumberOfCopies(obra);

        return all - qtd >= qtdMin ? true : false;
    }

    public boolean canDoInternalRequisition(Obra obra, int qtd)
    {
        int availabble = this.crudService.get(Obra.class, obra.getCota()).getQuantidade();;

        return availabble - qtd >= 0 ? true : false;
    }

    public int getAvailableNumberOfCopies(Obra obra)
    {
        int qtd = 0;
        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        emprestimos.addAll(this.eController.getRequest(obra, this.estadoPedidoControler.PENDING_MINI_BOOKING));
        emprestimos.addAll(this.eController.getBorrowedBooks(obra, this.estadoDevolucaoControler.NAO_DEVOLVIDO));
        emprestimos.addAll(this.eController.getRequest(obra, this.estadoPedidoControler.PENDING_BOOKING));

        for (Emprestimo e: emprestimos)
            qtd = qtd + e.getQuantidade();

        qtd = qtd + this.crudService.get(Obra.class, obra.getCota()).getQuantidade();

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
        EstadoPedido estadoPedido = item.getEstadoPedido();
        TipoRequisicao tipoRequisicao = null;
        EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, estadoDevolucaoControler.INDETERMINADO);
        EstadoRenovacao estadoRenovacao = null;

        if (item.isHomeRequisition())
        {
            tipoRequisicao = crudService.get(TipoRequisicao.class, tipoRequisicaoControler.REQUISICAO_DOMICILIAR);
        }
        else if (item.isInternalRequisition())
        {
            tipoRequisicao = crudService.get(TipoRequisicao.class, tipoRequisicaoControler.REQUISICAO_INTERNA);
        }

        emprestimo.setTipoRequisicao(tipoRequisicao);
        emprestimo.setEstadoPedido(estadoPedido);

        emprestimoPK.setObra(item.getObra());
        emprestimoPK.setUtente(user);
        emprestimoPK.setDataentradapedido(Calendar.getInstance());

        emprestimo.setEstadoDevolucao(estadoDevolucao);
        emprestimo.setEstadoPedido(item.getEstadoPedido());
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
