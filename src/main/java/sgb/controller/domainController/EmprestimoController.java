package sgb.controller.domainController;

import org.zkoss.zul.ListModelList;
import sgb.domain.Emprestimo;
import sgb.domain.Users;
import sgb.service.CRUDService;
import java.util.HashMap;
import java.util.List;


public class EmprestimoController
{
    private CRUDService crudService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    public EmprestimoController(CRUDService crudService) { this.crudService = crudService; }

    public ListModelList<Emprestimo> getRequisicoes(Users user, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("userId", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.user.id = :userId");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(),parameters);

        return new ListModelList<Emprestimo>(list);
    }


    public ListModelList<Emprestimo> getAllRequisicoes(Users user)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("userId", user.getId());
        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = :userId");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(),parameters);

        return new ListModelList<Emprestimo>(list);
    }

    public ListModelList<Emprestimo> getRequisicoes(int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido");

        List<Emprestimo> list = crudService.findByJPQuery(query.toString(),parameters);

        return new ListModelList<Emprestimo>(list);
    }

}
