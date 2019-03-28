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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class MiniBookingDeadlineController implements Runnable
{
    private MiniBookingDeadline mBDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;
    private final AtomicBoolean running = new AtomicBoolean(false);

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
        while(running.get())
        {
            System.out.println("MiniBookingDeadlineController...");

            try
            {
                this.process(this.eController.getRequest(ePControler.PENDING_MINI_BOOKING), Calendar.getInstance());
                Thread.sleep(minuto*60*1000);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public boolean process(List<Emprestimo> pendigMiniBooking, Calendar now)
    {
        boolean thereIsCanceledRequest = false;

        if (pendigMiniBooking != null)
        {
            for (Emprestimo e: pendigMiniBooking)
            {
                Calendar deadline = this.mBDeadline.getDeadline(e.getEmprestimoPK().getDataentradapedido());

                boolean  exceededDeadline = this.mBDeadline.exceededDeadline(deadline, now);

                if (exceededDeadline)
                {
                    thereIsCanceledRequest = true;
                    request.cancel(e);
                }
            }
        }

        return thereIsCanceledRequest;
    }

    public AtomicBoolean getRunning()
    {
        return running;
    }
}