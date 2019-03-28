package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.domain.EstadoDevolucao;
import sgb.fine.Fine;
import sgb.request.Request;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class BorrowedBooksDeadlineController extends Thread
{
    private BorrowedBooksDeadline bBDeadline;
    private Fine fine;
    private EstadoPedidoControler ePControler;
    private EstadoDevolucaoControler eDController;
    private EmprestimoController eController;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public BorrowedBooksDeadlineController(BorrowedBooksDeadline bBDeadline,
                                           Fine fine,
                                           EstadoPedidoControler ePControler,
                                           EmprestimoController eController,
                                           EstadoDevolucaoControler eDController)
    {
        this.bBDeadline = bBDeadline;
        this.fine = fine;
        this.ePControler = ePControler;
        this.eDController = eDController;
        this.eController = eController;
    }

    public void run()
    {
        if (this.running.get())
        {
            System.out.println("BorrowedBooksDeadlineController ...");

            try
            {
                this.process(this.eController.getBorrowedBooks(eDController.NOT_RETURNED), Calendar.getInstance());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public boolean process(List<Emprestimo> borrowedBooks, Calendar now)
    {
        boolean thereIsFinedBorrow = false;

        if (borrowedBooks != null)
        {
            for (Emprestimo e: borrowedBooks)
            {
                if (e.getDataaprovacao() != null)
                {
                    Calendar deadline = this.bBDeadline.getDeadline(e);

                    boolean  exceededDeadline = this.bBDeadline.exceededDeadline(deadline, now);

                    if (exceededDeadline)
                    {
                        thereIsFinedBorrow = true;

                        if (e.getQuantidade() > 0)
                        {
                            this.fine.fine(e);
                        }
                    }
                }
            }
        }
        return thereIsFinedBorrow;
    }

    public AtomicBoolean getRunning()
    {
        return running;
    }
}