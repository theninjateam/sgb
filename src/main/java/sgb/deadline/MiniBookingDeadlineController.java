package sgb.deadline;

import sgb.concurrence.MiniBookingConcurrenceController;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.request.Request;

import java.util.Calendar;
import java.util.List;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class MiniBookingDeadlineController implements Runnable
{
    private MiniBookingDeadline mBDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;
    private MiniBookingConcurrenceController miniBookingConcurrenceController;

    public MiniBookingDeadlineController (MiniBookingDeadline mBDeadline,
                                         Request request,
                                         EstadoPedidoControler ePControler,
                                         EmprestimoController eController,
                                          MiniBookingConcurrenceController miniBookingConcurrenceController)
    {
        this.mBDeadline = mBDeadline;
        this.request = request;
        this.ePControler = ePControler;
        this.eController = eController;
        this.miniBookingConcurrenceController = miniBookingConcurrenceController;
    }

    public  void run()
    {
            this.miniBookingConcurrenceController.enterInCriticalRegion();
            this.process(this.eController.getRequest(ePControler.PENDING_MINI_BOOKING), Calendar.getInstance());
            this.miniBookingConcurrenceController.leaveInCriticalRegion();
    }

    public  boolean  process(List<Emprestimo> pendigMiniBooking, Calendar now)
    {
        System.out.println(" Running MiniBookingDeadlineController ...");
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
}