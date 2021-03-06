package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.Calendar;
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
        parameters.put("userId", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.utente.id = :userId");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Emprestimo> getRequest(Users user, int idEstadoPedido, int idEstadoDevolucao)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("userId", user.getId());
        parameters.put("idEstadoDevolucao",idEstadoDevolucao);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and ");
        query.append("e.emprestimoPK.utente.id = :userId");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Emprestimo> getRequest(Users user, Obra obra, int idEstadoDevolucao)
    {
        parameters = new HashMap<String, Object>(3);
        query = new StringBuilder();

        parameters.put("cota",obra.getCota());
        parameters.put("userId", user.getId());
        parameters.put("idEstadoDevolucao",idEstadoDevolucao);

        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.obra.cota= :cota and ");
        query.append("e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and ");
        query.append("e.emprestimoPK.utente.id = :userId");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }


    public List<Emprestimo> getRequest(int idEstadoPedido, int idEstadoDevolucao)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", idEstadoPedido);
        parameters.put("idEstadoDevolucao",idEstadoDevolucao);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao");

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

    public List<Emprestimo> getBorrowedBooks(int idEstadoDevolucao)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("idEstadoDevolucao", idEstadoDevolucao);

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Emprestimo> getBorrowedBooks(Users user, int idEstadoDevolucao)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoDevolucao", idEstadoDevolucao);
        parameters.put("userId", user.getId());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and ");
        query.append("e.emprestimoPK.utente.id = :userId");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Emprestimo> getBorrowedBooks(Obra obra, int idEstadoDevolucao)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoDevolucao", idEstadoDevolucao);
        parameters.put("cota", obra.getCota());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and ");
        query.append("e.emprestimoPK.obra.cota = :cota");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Emprestimo> getBorrowedBooksByDate(Calendar dataI, Calendar dataF, EstadoDevolucao estadoDevolucao){
        if(estadoDevolucao!=null){
            parameters = new HashMap<>(3);
        }
        else{
            parameters = new HashMap<>(2);
        }

        parameters.put("datai", dataI);
        parameters.put("dataf", dataF);

        query = new StringBuilder();

        query.append("SELECT e FROM Emprestimo e WHERE e.datadevolucao >= :datai ");
        query.append("and e.datadevolucao <= :dataf");
        if(estadoDevolucao!=null){
            parameters.put("idEstado",estadoDevolucao.getIdestadodevolucao());
            query.append(" and e.estadoDevolucao.idestadodevolucao = :idEstado");
        }

        return this.crudService.findByJPQuery(query.toString(),parameters);
    }

    public List<Emprestimo> getBorrowedBooks(){
        query = new StringBuilder();

        query.append("SELECT e FROM Emprestimo e");

        return this.crudService.findByJPQuery(query.toString(),null);
    }

    public Emprestimo getRequest(EmprestimoPK emprestimoPK)
    {
        parameters = new HashMap<String, Object>(3);
        query = new StringBuilder();
        parameters.put("user_id", emprestimoPK.getUtente().getId());
        parameters.put("cota", emprestimoPK.getObra().getCota());
        parameters.put("dataentrada", emprestimoPK.getDataentradapedido());

        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.utente.id = :user_id and ");
        query.append("e.emprestimoPK.obra.cota = :cota and e.emprestimoPK.dataentradapedido = :dataentrada");

        return this.crudService.findEntByJPQueryT(query.toString(), parameters);
    }
}
