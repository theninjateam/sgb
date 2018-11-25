package sgb.controller.domainController;

import org.zkoss.zul.ListModelList;
import sgb.domain.Emprestimo;
import sgb.domain.Users;
import sgb.service.CRUDService;
import java.util.HashMap;
import java.util.List;


/**
 * @author Fonseca
 *
 *@Description: We use Singleton because there are several classes that use methods present in
 * this class to read data from DB, so we decide that the instace of that class can shared.
 */

public class EmprestimoControllerSingleton
{
    private static EmprestimoControllerSingleton instace = null;
    private CRUDService crudService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    private EmprestimoControllerSingleton(){}
    private EmprestimoControllerSingleton(CRUDService crudService) { this.crudService = crudService; }

    public static EmprestimoControllerSingleton getInstance(CRUDService crudService)
    {
        if (instace == null)
        {
            synchronized (EmprestimoControllerSingleton.class)
            {
                if ( instace == null)
                {
                    instace = new EmprestimoControllerSingleton(crudService);
                }
            }
        }

        return instace;
    }

    public ListModelList<Emprestimo> getRequisicoes(Users user, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("userId", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.user.id = :userId");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public ListModelList<Emprestimo> getAllRequisicoes(Users user)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("userId", user.getId());
        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = :userId");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public ListModelList<Emprestimo> getRequisicoes(int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(), parameters);

        return new ListModelList<Emprestimo>(list);
    }
}
