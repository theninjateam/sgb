package sgb.controller.domainController;

import sgb.domain.EstadoPedido;
import sgb.service.CRUDService;

import java.util.HashMap;

public class EstadoPedidoSingleton
{
    private CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    public final int PENDING;
    public final int REJECTED;
    public final int IN_QUEUE;
    public final int ACCEPTED;
    public final int CANCELED;

    public EstadoPedidoSingleton(CRUDService crudService)
    {
        this.CRUDService = crudService;

        PENDING = getValue("PENDING");
        REJECTED = getValue("REJECTED");
        IN_QUEUE = getValue("IN_QUEUE");
        ACCEPTED = getValue("ACCEPTED");
        CANCELED = getValue("CANCELED");
    }

    public int getValue(String description)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("description", description);
        query.append("SELECT e FROM EstadoPedido e WHERE e.descricao = :description");

        return  ((EstadoPedido) this.CRUDService.findEntByJPQueryT(query.toString(), parameters)).getIdestadopedido();
    }

    public EstadoPedido getEstadoPedido(int idEstadoPedido)
    {
        return this.CRUDService.get(EstadoPedido.class,idEstadoPedido);
    }

}