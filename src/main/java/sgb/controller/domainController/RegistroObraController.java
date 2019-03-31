package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RegistroObraController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public RegistroObraController(CRUDService crudService)
    {
        this.crudService = crudService;
    }

    public List<RegistroObra> getObrasRegistadas()
    {
        query = new StringBuilder();

        query.append("SELECT r FROM RegistroObra r");

        return this.crudService.findByJPQuery(query.toString(),null);
    }

    public List<RegistroObra> getObrasByDate(Calendar dataI, Calendar dataF){

        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("datai", dataI);
        parameters.put("dataf", dataF);

        query.append("SELECT r FROM RegistroObra r WHERE r.registroObraPK.dataRegisto >= :datai and r.registroObraPK.dataRegisto <= :dataf");

        return this.crudService.findByJPQuery(query.toString(),parameters);


    }
}
