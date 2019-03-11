package sgb.request;

import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.domain.EstadoPedido;
import sgb.domain.Obra;

import java.util.PriorityQueue;

public class Queue
{
    private Request request;
    private EstadoPedidoControler estadoPedidoControler;

    public Queue(Request request, EstadoPedidoControler estadoPedidoControler)
    {
        this.request = request;
        this.estadoPedidoControler = estadoPedidoControler;
    }

    public PriorityQueue<Emprestimo> getQueue(Obra obra)
    {
        PriorityQueue<Emprestimo> queue = new PriorityQueue<>();

        for (Emprestimo e: this.request.getRequisicoes(obra, this.estadoPedidoControler.IN_QUEUE))
            queue.add(e);

        return queue;
    }
}
