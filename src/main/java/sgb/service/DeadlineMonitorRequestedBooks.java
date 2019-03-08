package sgb.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.controller.domainController.EstadoPedidoSingleton;
import sgb.domain.Emprestimo;

import java.lang.Thread;
import java.util.Calendar;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class DeadlineMonitorRequestedBooks extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private DeadlineRequestedBooks dRBooks;
    private EmprestimoControllerSingleton eCSingleton;
    private EstadoPedidoSingleton ePSingleton;
    private int minuto = 1;

    public DeadlineMonitorRequestedBooks(DeadlineRequestedBooks dRBooks,
                                         EmprestimoControllerSingleton eCSingleton,
                                         EstadoPedidoSingleton ePSingleton)
    {
        this.dRBooks = dRBooks;
        this.eCSingleton = eCSingleton;
        this.ePSingleton = ePSingleton;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                for (Emprestimo e: eCSingleton.getRequisicoes(ePSingleton.PENDING))
                {
                    boolean  exceededDeadline =
                            this.dRBooks.exceededDeadline(e.getEmprestimoPK().getDataentrada(), Calendar.getInstance());

                    if (exceededDeadline)
                    {
                        eCSingleton.cancelEmprestimo(e);
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