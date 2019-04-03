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



    public List<ObraCategoria> getObrasCategorias (AreaCientifica areaCientifica){

        List<ObraCategoria> obraCategorias = new ArrayList<>();
        //List <AreaCientifica>areaCientifica1 = areaCientificaController.getAreaCientifica();

        if(areaCientifica != null)
            obraCategorias.add(new ObraCategoria(areaCientifica,getObras(areaCientifica)));
        else {
            for (AreaCientifica a : areaCientificaController.getAreaCientifica()) {
                System.out.println("areaa ======  "+a.getDescricao());
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
            obraCategorias.add(new ObraCategoria(areaCientifica,obraList));
        else {
            for (AreaCientifica a : areaCientificaController.getAreaCientifica()) {

                obraCategorias.add(new ObraCategoria(a, obraList));
            }
        }

        return obraCategorias;
    }

}
