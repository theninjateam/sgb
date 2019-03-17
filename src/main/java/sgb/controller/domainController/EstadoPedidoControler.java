package sgb.controller.domainController;
import sgb.domain.EstadoPedido;
import sgb.service.CRUDService;
import java.util.HashMap;

public class EstadoPedidoControler
{
    private CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    public final int PENDING_BOOKING;
    public final int PENDING_MINI_BOOKING;
    public final int REJECTED;
    public final int ON_WAINTING_QUEUE;
    public final int ACCEPTED;
    public final int CANCELED;

    public EstadoPedidoControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        PENDING_BOOKING = getValue("PENDING_BOOKING");
        PENDING_MINI_BOOKING = getValue("PENDING_MINI_BOOKING");
        REJECTED = getValue("REJECTED");
        ON_WAINTING_QUEUE = getValue("ON_WAINTING_QUEUE");
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