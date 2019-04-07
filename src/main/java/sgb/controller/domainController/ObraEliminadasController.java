package sgb.controller.domainController;

import sgb.domain.ObraEliminadas;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;

public class ObraEliminadasController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public ObraEliminadasController(CRUDService crudService)
    {
        this.crudService = crudService;
    }

    public List<ObraEliminadas> getObrasEliminadas()
    {
        query = new StringBuilder();

        query.append("SELECT oe FROM ObraEliminadas oe");

        return this.crudService.findByJPQuery(query.toString(),null);
    }
}
