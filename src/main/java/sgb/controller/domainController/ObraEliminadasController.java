package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

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
}
