package sgb.controller.domainController;

import sgb.domain.Emprestimo;
import sgb.domain.EmprestimoPK;
import sgb.domain.Obra;
import sgb.domain.Users;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;

public class EmprestimoController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public EmprestimoController(CRUDService crudService)
    {
        this.crudService = crudService;
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

    public List<Emprestimo> getRequest(Users user, int idEstadoPedido)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("user_id", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.user.id = :user_id");

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

    public Emprestimo getRequest(EmprestimoPK emprestimoPK)
    {
        parameters = new HashMap<String, Object>(3);
        query = new StringBuilder();

        parameters.put("user_id", emprestimoPK.getUser().getId());
        parameters.put("cota", emprestimoPK.getObra().getCota());
        parameters.put("dataentrada", emprestimoPK.getDataentrada());


        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = :user_id and ");
        query.append("e.emprestimoPK.obra.cota = :cota and e.emprestimoPK.dataentrada = :dataentrada");

        return this.crudService.findEntByJPQueryT(query.toString(), parameters);
    }
}
