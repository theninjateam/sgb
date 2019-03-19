package sgb.controller.domainController;

import sgb.domain.EstadoDevolucao;
import sgb.domain.EstadoPedido;
import sgb.service.CRUDService;

import java.util.HashMap;

public class EstadoDevolucaoControler
{
    private CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;
    public final int UNDETERMINED;
    public final int RETURNED;
    public final int NOT_RETURNED;


    public EstadoDevolucaoControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        UNDETERMINED = getValue("UNDETERMINED");
        RETURNED = getValue("RETURNED");
        NOT_RETURNED = getValue("NOT_RETURNED");

    }

    public int getValue(String description)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("description", description);
        query.append("SELECT e FROM EstadoDevolucao e WHERE e.descricao = :description");

        return  ((EstadoDevolucao) this.CRUDService.findEntByJPQueryT(query.toString(), parameters)).getIdestadodevolucao();
    }
}