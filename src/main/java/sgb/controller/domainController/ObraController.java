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


    public List<Obra> getObras(AreaCientifica areaCientifica){

        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("area", areaCientifica.getIdarea());

        query.append("SELECT o FROM Obra o WHERE o.areacientifica.idarea = :area");

        return this.crudService.findByJPQuery(query.toString(),parameters);

    }
//
//    public List<Obra> getObra(Obra obra){
//
//        parameters = new HashMap<String, Object>(1);
//        query = new StringBuilder();
//
//        parameters.put("cota", obra.getCota());
//
//        query.append("SELECT o. FROM Obra o WHERE o.cota = :cota");
//
//        return this.crudService.findByJPQuery(query.toString(),parameters);
//
//    }


    public List<ObraCategoria> getObrasCategorias (AreaCientifica areaCientifica){

        List<ObraCategoria> obraCategorias = new ArrayList<>();

        if(areaCientifica != null)
            obraCategorias.add(new ObraCategoria(areaCientifica,getObras(areaCientifica)));
        else {
            for (AreaCientifica a : areaCientificaController.getAreaCientifica()) {

                obraCategorias.add(new ObraCategoria(a, getObras(a)));
            }
        }

        return obraCategorias;
    }

    public List<ObraCategoria> getObrasCategorias (List<RegistroObra> registroObras, AreaCientifica areaCientifica){

        List<ObraCategoria> obraCategorias = new ArrayList<>();
        List<Obra> obraList = new ArrayList<>();

        for (RegistroObra r : registroObras) {

            obraList.add(r.getRegistroObraPK().getObra());
        }

        if(areaCientifica != null)
            obraCategorias.add(new ObraCategoria(areaCientifica,getObraByArea(areaCientifica,obraList)));
        else {
            for (AreaCientifica a : areaCientificaController.getAreaCientifica()) {

                obraCategorias.add(new ObraCategoria(a, getObraByArea(a,obraList)));
            }
        }

        return obraCategorias;
    }

    public List<Obra> getObraByArea(AreaCientifica areaCientifica,List<Obra> obraList){

        List<Obra> listObra = new ArrayList<>();

        for (Obra o:obraList){

            if (o.getAreacientifica().getIdarea()==areaCientifica.getIdarea()){

                listObra.add(o);
            }
        }

        return listObra;
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
