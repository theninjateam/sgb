package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.request.Request;

import java.util.Calendar;
import java.util.List;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class BookingDeadlineController extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private BookingDeadline bDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;

    private int minuto = 1;

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
        while(true)
        {
            try
            {
                List<Emprestimo> reserves = this.eController.getRequest(ePControler.PENDING_BOOKING);

                if (reserves != null)
                {
                    for (Emprestimo e: reserves)
                    {
                        boolean  exceededDeadline =
                                this.bDeadline.exceededDeadline(e.getEmprestimoPK().getDataentrada(), Calendar.getInstance());

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