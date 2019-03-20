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

public class BorrowedBooksDeadlineController extends Thread
{
    private BorrowedBooksDeadline bBDeadline;
    private Request request;
    private EstadoPedidoControler ePControler;
    private EmprestimoController eController;

    public BorrowedBooksDeadlineController(BorrowedBooksDeadline bBDeadline,
                                           Request request,
                                           EstadoPedidoControler ePControler,
                                           EmprestimoController eController)
    {
        this.bBDeadline = bBDeadline;
        this.request = request;
        this.ePControler = ePControler;
        this.eController = eController;
    }

    public void run()
    {
        try
        {
            List<Emprestimo> borrowedBooks = this.eController.getRequest(ePControler.ACCEPTED);

            if (borrowedBooks != null)
                {
                    for (Emprestimo e: borrowedBooks)
                    {
                        boolean  exceededDeadline =
                                this.bBDeadline.exceededDeadline(e.getEmprestimoPK().getDataentradapedido(), Calendar.getInstance());

                        if (exceededDeadline)
                        {
                            this.bBDeadline.multar(e);
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