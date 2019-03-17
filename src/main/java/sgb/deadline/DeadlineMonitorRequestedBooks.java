package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.request.Request;

import java.lang.Thread;
import java.util.Calendar;
import java.util.List;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class DeadlineMonitorRequestedBooks extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private DeadlineRequestedBooks dRBooks;
    private Request request;
    private EstadoPedidoControler ePControler;

    private int minuto = 1;

    public DeadlineMonitorRequestedBooks(DeadlineRequestedBooks dRBooks,
                                         Request request, EstadoPedidoControler ePControler)
    {
        this.dRBooks = dRBooks;
        this.request = request;
        this.ePControler = ePControler;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                List<Emprestimo> requests = request.getRequest(ePControler.PENDING);

                if (requests != null)
                {
                    for (Emprestimo e: requests)
                    {
                        boolean  exceededDeadline =
                                this.dRBooks.exceededDeadline(e.getEmprestimoPK().getDataentradapedido(), Calendar.getInstance());

                        if (exceededDeadline)
                        {
                            request.cancel(e);
                        }
                    }
                }

                Thread.sleep(minuto * 60 * 1000);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        this.start();
    }
}