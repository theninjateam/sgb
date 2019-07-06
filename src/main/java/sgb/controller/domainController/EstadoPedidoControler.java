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
        PENDING_BOOKING = getValue("RESERVA_PENDENTE");
        PENDING_MINI_BOOKING = getValue("MINI_RESERVA_PENDENTE");
        REJECTED = getValue("REJEITADO");
        ON_WAINTING_QUEUE = getValue("NA_FILA");
        ACCEPTED = getValue("ACEITO");
        CANCELED = getValue("CANCELADO");
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