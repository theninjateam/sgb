package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObraController {

    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;
    private AreaCientificaController areaCientificaController;

    public ObraController(CRUDService crudService, AreaCientificaController areaCientificaController)

    {
        this.crudService = crudService;
        this.areaCientificaController = areaCientificaController;
    }

    public List<Obra> getObras()
    {
        query = new StringBuilder();

        query.append("SELECT o FROM Obra o");

        return this.crudService.findByJPQuery(query.toString(),null);
    }


    public List<Obra> getObras(int cat){

        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("cat", cat);

        query.append("SELECT o FROM Obra o WHERE o.areacientifica.idarea = :cat");

        return this.crudService.findByJPQuery(query.toString(),parameters);

    }

    public List<ObraCategoria> getObrasCategorias (){

        List<ObraCategoria> obraCategorias = new ArrayList<>();

        for(AreaCientifica a: areaCientificaController.getAreaCientifica()){

            obraCategorias.add(new ObraCategoria(a,getObras(a.getIdarea())));
        }

        return obraCategorias;
    }

//    public List<Obra> getRequest(int categoria)
//    {
//        parameters = new HashMap<String, Object>(2);
//        query = new StringBuilder();
//
//        parameters.put("idEstadoPedido", idEstadoPedido);
//        parameters.put("userId", user.getId());
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
//        query.append("e.emprestimoPK.utente.id = :userId");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//    }
//
//    public List<Emprestimo> getRequest(Users user, int idEstadoPedido, int idEstadoDevolucao)
//    {
//        parameters = new HashMap<String, Object>(2);
//        query = new StringBuilder();
//
//        parameters.put("idEstadoPedido", idEstadoPedido);
//        parameters.put("userId", user.getId());
//        parameters.put("idEstadoDevolucao",idEstadoDevolucao);
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
//        query.append("e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and");
//        query.append("e.emprestimoPK.utente.id = :userId");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//    }
//
//    public List<Emprestimo> getRequest(Users user, Obra obra, int idEstadoDevolucao)
//    {
//        parameters = new HashMap<String, Object>(3);
//        query = new StringBuilder();
//
//        parameters.put("cota",obra.getCota());
//        parameters.put("userId", user.getId());
//        parameters.put("idEstadoDevolucao",idEstadoDevolucao);
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.utente.obra.cota= :cota and ");
//        query.append("e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and");
//        query.append("e.emprestimoPK.utente.id = :userId");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//    }
//
//
//    public List<Emprestimo> getRequest(int idEstadoPedido, int idEstadoDevolucao)
//    {
//        parameters = new HashMap<String, Object>(2);
//        query = new StringBuilder();
//
//        parameters.put("idEstadoPedido", idEstadoPedido);
//        parameters.put("idEstadoDevolucao",idEstadoDevolucao);
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
//        query.append("e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//    }
//
//    public List<Emprestimo> getRequest(int idEstadoPedido)
//    {
//        parameters = new HashMap<String, Object>(1);
//        query = new StringBuilder();
//
//        parameters.put("idEstadoPedido", idEstadoPedido);
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//    }
//
//    public List<Emprestimo> getBorrowedBooks(int idEstadoDevolucao)
//    {
//        parameters = new HashMap<String, Object>(1);
//        query = new StringBuilder();
//
//        parameters.put("idEstadoDevolucao", idEstadoDevolucao);
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//    }
//
//    public List<Emprestimo> getBorrowedBooks(Users user, int idEstadoDevolucao)
//    {
//        parameters = new HashMap<String, Object>(2);
//        query = new StringBuilder();
//
//        parameters.put("idEstadoDevolucao", idEstadoDevolucao);
//        parameters.put("userId", user.getId());
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.estadoDevolucao.idestadodevolucao = :idEstadoDevolucao and ");
//        query.append("e.emprestimoPK.utente.id = :userId");
//
//        return this.crudService.findByJPQuery(query.toString(), parameters);
//
//    }
//
//
//    public Emprestimo getRequest(EmprestimoPK emprestimoPK)
//    {
//        parameters = new HashMap<String, Object>(3);
//        query = new StringBuilder();
//
//        parameters.put("user_id", emprestimoPK.getUtente().getId());
//        parameters.put("cota", emprestimoPK.getObra().getCota());
//        parameters.put("dataentrada", emprestimoPK.getDataentradapedido());
//
//
//        query.append("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.utente.id = :user_id and ");
//        query.append("e.emprestimoPK.obra.cota = :cota and e.emprestimoPK.dataentradapedido = :dataentrada");
//
//        return this.crudService.findEntByJPQueryT(query.toString(), parameters);
//    }


}
