package sgb.controller.domainController;

import sgb.domain.EstadoMulta;
import sgb.domain.EstadoPedido;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;

public class EstadoMultaControler
{
    private CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    public final int NOT_PAID;
    public final int PAID;
    public final int REVOKED;

    public EstadoMultaControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        NOT_PAID = getValue("NOT_PAID");
        PAID = getValue("PAID");
        REVOKED = getValue("REVOKED");
    }

    public int getValue(String description)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("description", description);
        query.append("SELECT e FROM EstadoMulta e WHERE e.descricao = :description");

        return  ((EstadoMulta) this.CRUDService.findEntByJPQueryT(query.toString(), parameters)).getIdestadomulta();
    }

    public List<EstadoMulta> getEstadoMultas(){
        query = new StringBuilder();

        query.append("SELECT em FROM EstadoMulta em");
        return this.CRUDService.findByJPQuery(query.toString(),null);
    }
}