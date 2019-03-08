package sgb.controller.domainController;

import org.zkoss.zul.ListModelList;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;


/**
 * @author Fonseca
 *
 *@Description: We use Singleton because there are several classes that use methods present in
 * this class to read data from DB, so we decide that, the instace of that class can be shared.
 */


public class EmprestimoControllerSingleton
{
    private HashMap<String, Object> parameters;
    public HashMap<String, Semaphore> resources = new HashMap<String, Semaphore>();
    private StringBuilder query;
    private CRUDService CRUDService;
    public ConfigSingleton eRSingleton;
    private EstadoPedidoSingleton ePSingleton;

    public  EmprestimoControllerSingleton(CRUDService crudService, ConfigSingleton eRSingleton,
                                          EstadoPedidoSingleton ePSingleton)
    {
        this.CRUDService = crudService;
        this.eRSingleton = eRSingleton;
        this.ePSingleton = ePSingleton;
    }

    public ListModelList<Emprestimo> getRequisicoes(Users user, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("userId", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.user.id = :userId");

        List<Emprestimo> list = this.CRUDService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public ListModelList<Emprestimo> getAllRequisicoes(Users user)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("userId", user.getId());
        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = :userId");

        List<Emprestimo> list = this.CRUDService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public ListModelList<Emprestimo> getRequisicoes(int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido");

        List<Emprestimo> list = this.CRUDService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public ListModelList<Emprestimo> getRequisicoes(Obra obra, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("cota", obra.getCota());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.obra.cota = :cota");

        List<Emprestimo> list = this.CRUDService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public Emprestimo getRequest(EmprestimoPK emprestimoPK)
    {
        parameters = new HashMap<String, Object>(3);
        query = new StringBuilder();

        parameters.put("user_id", emprestimoPK.getUser().getId());
        parameters.put("cota", emprestimoPK.getObra().getCota());
        parameters.put("dataentrada", emprestimoPK.getDataentrada());


        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = :user_id and ");
        query.append("e.emprestimoPK.obra.cota = :cota and e.emprestimoPK.dataentrada = :dataentrada");

        return this.CRUDService.findEntByJPQueryT(query.toString(), parameters);
    }

    public void saveEmprestimo(Emprestimo emprestimo)
    {
        this.CRUDService.Save(emprestimo);
    }

    public synchronized void enterInCriticalRegion(Item item)
    {
        try
        {
            if (this.resources.get(item.getObra().getCota()) == null)
            {
                this.resources.put(item.getObra().getCota(), new Semaphore(1));
                this.resources.get(item.getObra().getCota()).acquire();
            }
            else
            {
                this.resources.get(item.getObra().getCota()).acquire();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void leaveInCriticalRegion(Item item)
    {
        try
        {
            this.resources.get(item.getObra().getCota()).release();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void requisitar(Item item, Users user)
    {
        try
        {
            this.enterInCriticalRegion(item);


            validate(item);
            Emprestimo emprestimo = getEmprestimo(item, user);

            if (!item.getIsLineUp())
            {
                Obra obra = this.CRUDService.get(Obra.class, item.getObra().getCota());
                obra.setQuantidade(obra.getQuantidade() - item.getQuantidade());
                this.CRUDService.update(obra);
            }
            this.CRUDService.Save(emprestimo);

            this.leaveInCriticalRegion(item);
        }
        catch (Exception ex)
        {
            this.leaveInCriticalRegion(item);
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

    public Emprestimo getEmprestimo(Item item, Users user)
    {
        EmprestimoPK emprestimoPK = new EmprestimoPK();
        Emprestimo emprestimo = new Emprestimo();

        int idEstadoPedido = getIdEstadoPedido(item);
        int idTipoRequisicao = getIdTipoPedido(item);
        int idEstadoDevolucao = getIdEstadoDevolucao();
        int idEstadoRenovacao = getIdEstadoRenovacao();

        EstadoPedido estadoPedido = this.CRUDService.get(EstadoPedido.class, idEstadoPedido);
        EstadoDevolucao estadoDevolucao = this.CRUDService.get(EstadoDevolucao.class, idEstadoDevolucao);
        EstadoRenovacao estadoRenovacao = this.CRUDService.get(EstadoRenovacao.class, idEstadoRenovacao);
        TipoRequisicao tipoRequisicao = this.CRUDService.get(TipoRequisicao.class, idTipoRequisicao);

        emprestimoPK.setObra(item.getObra());
        emprestimoPK.setUser(user);
        emprestimoPK.setDataentrada(Calendar.getInstance());

        emprestimo.setEstadoDevolucao(estadoDevolucao);
        emprestimo.setEstadoPedido(estadoPedido);
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setComentario("--");
        emprestimo.setDataaprovacao(null);
        emprestimo.setDatadevolucao(null);
        emprestimo.setQuantidade(item.getQuantidade());
        emprestimo.setEstadoRenovacao(estadoRenovacao);
        emprestimo.setDatarenovacao(null);
        emprestimo.setDatadevolucaorenovacao(null);
        emprestimo.setTipoRequisicao(tipoRequisicao);

        return emprestimo;
     }

     public int getIdEstadoPedido(Item item)
     {
         return item.getIsLineUp() ? 4 : 1;
     }

    public int getIdTipoPedido(Item item)
    {
        return item.getIsHomeRequisition() ? 1 : 2;
    }

    public int getIdEstadoDevolucao()
    {
        return 1;
    }

    public int getIdEstadoRenovacao()
    {
        return 1;
    }

    public boolean canDoHomeRequisition(Obra obra)
    {
        int qtdMin = this.eRSingleton.MINIMUM_NUMBER_OF_COPIES;
        int qtd = getAvailableNumberOfCopies(obra);

        return qtd > qtdMin ? true : false;
    }

    public int getAvailableNumberOfCopies(Obra obra)
    {
        int qtd =  this.getRequisicoes(obra, 1).getSize();
        qtd += this.getRequisicoes(obra, 3).getSize();
        qtd += this.CRUDService.get(Obra.class, obra.getCota()).getQuantidade();

        return qtd;
    }
    public boolean canLineUp(Obra obra, int qtd)
    {
        int qtdMin = this.eRSingleton.MINIMUM_NUMBER_OF_COPIES;
        int qtdDis = this.CRUDService.get(Obra.class, obra.getCota()).getQuantidade();

        if (canDoHomeRequisition(obra))
            return  qtdDis - qtd < qtdMin ? true : false;
        else
            return  qtdDis  - qtd < 0 ? true : false;
    }

    public PriorityQueue<Emprestimo> generateDomiciliarQueueFor(Obra obra)
    {
        PriorityQueue<Emprestimo> domiciliarQueue = new PriorityQueue<>();

        for (Emprestimo e: this.getRequisicoes(obra, 4))
        {
            domiciliarQueue.add(e);
        }

        return domiciliarQueue;
    }

    //must be a transaction
    /*
    * A DeadlineRequestedBooks as only on book.
    * */
    public void cancelEmprestimo(Emprestimo e)
    {
        Item item = new Item();

        try
        {
            Emprestimo emprestimo = getRequest(e.getEmprestimoPK());
            EstadoPedido estadoPedido = this.ePSingleton.getEstadoPedido(ePSingleton.CANCELED);

            emprestimo.setEstadoPedido(estadoPedido);
            emprestimo.setComentario("Pedido Cancelado Pelo Sistema, excedeu o limite maximo de levantamento");

            item.setObra(e.getEmprestimoPK().getObra());
            item.setQuantidade(e.getQuantidade());

            this.enterInCriticalRegion(item);

            Obra obra = this.CRUDService.get(Obra.class, item.getObra().getCota());
            obra.setQuantidade(obra.getQuantidade() + item.getQuantidade());
            this.CRUDService.update(obra);

            this.leaveInCriticalRegion(item);

            this.CRUDService.update(emprestimo);
        }
        catch (Exception ex)
        {
            this.leaveInCriticalRegion(item);
            ex.printStackTrace();
        }
    }
}
