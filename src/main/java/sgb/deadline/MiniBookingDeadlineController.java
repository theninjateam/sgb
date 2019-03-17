package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.request.Request;

import java.lang.Thread;
import java.util.Calendar;
import java.util.List;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class MiniBookingDeadlineController extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private MiniBookingDeadline mBDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;

    private int minuto = 1;

    public MiniBookingDeadlineController(MiniBookingDeadline mBDeadline,
                                         Request request,
                                         EstadoPedidoControler ePControler,
                                         EmprestimoController eController)
    {
        this.mBDeadline = mBDeadline;
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
                List<Emprestimo> pendigMiniBooking = this.eController.getRequest(ePControler.PENDING_MINI_BOOKING);

                if (pendigMiniBooking != null)
                {
                    for (Emprestimo e: pendigMiniBooking)
                    {
                        boolean  exceededDeadline =
                                this.mBDeadline.exceededDeadline(e.getEmprestimoPK().getDataentradapedido(), Calendar.getInstance());
                                this.mBDeadline.exceededDeadline(e.getEmprestimoPK().getDataentradapedido(), Calendar.getInstance());

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