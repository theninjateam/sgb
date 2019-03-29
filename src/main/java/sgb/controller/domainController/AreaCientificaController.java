package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AreaCientificaController {

    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public AreaCientificaController (CRUDService crudService)
    {
        this.crudService = crudService;
    }

    public List<AreaCientifica> getAreaCientifica()
    {
        query = new StringBuilder();

        query.append("SELECT a FROM AreaCientifica a");

        return this.crudService.findByJPQuery(query.toString(),null);
    }
}
