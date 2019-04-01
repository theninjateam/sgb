package sgb.request;

import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.domain.EstadoPedido;
import sgb.domain.Obra;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Queue
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;
    private EstadoPedidoControler estadoPedidoControler;

    public Queue(CRUDService crudService, EstadoPedidoControler estadoPedidoControler)
    {
        this.crudService = crudService;
        this.estadoPedidoControler = estadoPedidoControler;
    }

    public PriorityQueue<Emprestimo> getQueue(Obra obra)
    {
        PriorityQueue<Emprestimo> queue = new PriorityQueue<>();

        for (Emprestimo e: this.getOnWaitingQueueRequest(obra))
        {
            queue.add(e);
        }

        return queue;
    }

    public List<Emprestimo> getOnWaitingQueueRequest(Obra obra)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("idEstadoPedido", this.estadoPedidoControler.ON_WAINTING_QUEUE);
        parameters.put("cota", obra.getCota());

        query.append("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido = :idEstadoPedido and ");
        query.append("e.emprestimoPK.obra.cota = :cota");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }
}