package sgb.controller.domainController;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import sgb.domain.*;
import sgb.controller.domainController.EmprestimoRuleSingleton;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


/**
 * @author Fonseca
 *
 *@Description: We use Singleton because there are several classes that use methods present in
 * this class to read data from DB, so we decide that, the instace of that class can be shared.
 */


public class EmprestimoControllerSingleton
{
    private HashMap<String, Object> parameters;
    private StringBuilder query;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;
    private EstadoRenovacao estadoRenovacao;
    private Config config;
    private CRUDService CRUDService;
    public EmprestimoRuleSingleton eRSingleton;

    public  EmprestimoControllerSingleton(CRUDService crudService, EmprestimoRuleSingleton eRSingleton)
    {
        this.CRUDService = crudService;
        this.eRSingleton = eRSingleton;
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

    /*that method must be a transaction*/
    public synchronized void requisitar(ListModelList<Item> cestaListModel, Users user)
    {
        try
        {
            validate(cestaListModel);

            for (Item item : cestaListModel)
            {
                setEmprestimo(item, user);

                if (!item.getIsLineUp())
                {
                    Obra obra = this.CRUDService.get(Obra.class, item.getObra().getCota());
                    obra.setQuantidade(obra.getQuantidade() - item.getQuantidade());
                    this.CRUDService.update(obra);
                }

                this.CRUDService.Save(this.emprestimo);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

     public void setEmprestimo(Item item, Users user)
     {
         int idEstadoPedido = item.getIsLineUp() ? 4 : 1;
         this.emprestimo = new Emprestimo();
         this.emprestimoPK = new EmprestimoPK();

         this.estadoDevolucao = this.CRUDService.get(EstadoDevolucao.class, 1);
         this.estadoPedido = this.CRUDService.get(EstadoPedido.class, idEstadoPedido);
         this.estadoRenovacao = this.CRUDService.get(EstadoRenovacao.class,1);

         this.emprestimoPK.setObra(item.getObra());
         this.emprestimoPK.setUser(user);
         this.emprestimoPK.setDataentrada(Calendar.getInstance());

         this.emprestimo.setEstadoDevolucao(estadoDevolucao);
         this.emprestimo.setEstadoPedido(estadoPedido);
         this.emprestimo.setEmprestimoPK(emprestimoPK);
         this.emprestimo.setComentario("--");
         this.emprestimo.setDataaprovacao(null);
         this.emprestimo.setDatadevolucao(null);
         this.emprestimo.setQuantidade(item.getQuantidade());
         this.emprestimo.setEstadoRenovacao(estadoRenovacao);
         this.emprestimo.setDatarenovacao(null);
         this.emprestimo.setDatadevolucaorenovacao(null);
     }

    public void validate(ListModelList<Item> cestaListModel)
    {
        for (int i = 0; i < cestaListModel.size(); i++)
        {
            Item item = cestaListModel.get(i);
            item.setHomeRequisition(canDoHomeRequisition(item.getObra()));
            item.setLineUp(canLineUp(item.getObra(),1));
        }
    }

    public boolean canDoHomeRequisition(Obra obra)
    {

        int qtdMin = eRSingleton.MINIMUM_NUMBER_OF_COPIES;
        int qtd =  this.getRequisicoes(obra, 1).getSize();
        qtd += this.getRequisicoes(obra, 3).getSize();
        qtd += this.CRUDService.get(Obra.class, obra.getCota()).getQuantidade();

        return qtd > qtdMin ? true : false;
    }

    public boolean canLineUp(Obra obra, int qtd)
    {
        int qtdMin = eRSingleton.MINIMUM_NUMBER_OF_COPIES;
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
}
