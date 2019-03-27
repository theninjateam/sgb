package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.request.Request;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class BookingDeadlineController extends Thread
{
    private BookingDeadline bDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public BookingDeadlineController(BookingDeadline bDeadline,
                                     Request request,
                                     EstadoPedidoControler ePControler,
                                     EmprestimoController eController)
    {
        this.bDeadline = bDeadline;
        this.request = request;
        this.ePControler = ePControler;
        this.eController = eController;
    }

    public void run()
    {


        if (this.running.get())
        {
            try
            {
                List<Emprestimo> pendingBooking = this.eController.getRequest(ePControler.PENDING_BOOKING);

                if (pendingBooking != null)
                {
                    for (Emprestimo e: pendingBooking)
                    {
                        if (e.getDataaprovacao() == null) { continue;}

                        Calendar deadline = this.bDeadline.getDeadline(e.getDataaprovacao());

                        boolean  exceededDeadline = this.bDeadline.exceededDeadline(deadline, Calendar.getInstance());

                        if (exceededDeadline)
                        {
                            request.cancel(e);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public AtomicBoolean getRunning()
    {
        return running;
    }
}