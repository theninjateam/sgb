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

public class BookingDeadlineController implements Runnable
{
    private BookingDeadline bDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;

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
        System.out.println("BookingDeadlineController ...");
        this.process(this.eController.getRequest(ePControler.PENDING_BOOKING), Calendar.getInstance());
    }


    public boolean process(List<Emprestimo> pendingBooking, Calendar now)
    {
        boolean thereIsCanceledRequest = false;

        if (pendingBooking != null)
        {
            for (Emprestimo e : pendingBooking)
            {
                if (e.getDataaprovacao() != null)
                {
                    Calendar deadline = this.bDeadline.getDeadline(e.getDataaprovacao());

                    boolean exceededDeadline = this.bDeadline.exceededDeadline(deadline, now);

                    if (exceededDeadline)
                    {
                        thereIsCanceledRequest = true;
                        request.cancel(e);
                    }
                }
            }
        }
        return thereIsCanceledRequest;
    }
}