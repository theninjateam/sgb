package sgb.deadline;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.ConfigControler;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.Emprestimo;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class BookingDeadlineControllerTest
{
    @Autowired
    private ApplicationContext context;
    private BookingDeadline bookingDeadline;
    private BookingDeadlineController bookingDeadlineController;
    private ConfigControler configControler;
    private CRUDService crudService;
    private EstadoPedidoControler estadoPedidoControler;
    private List<Emprestimo>  emprestimos;
    Calendar bookingTime = Calendar.getInstance();

    @Before
    @Transactional
    public void before() throws Exception {
        System.out.println("Setting it up!");

        this.bookingDeadline = (BookingDeadline) context.getBean("bookingDeadline");
        this.configControler = (ConfigControler) context.getBean("configControler");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.bookingDeadlineController = (BookingDeadlineController) context.getBean("bookingDeadlineController");
        this.estadoPedidoControler = (EstadoPedidoControler) context.getBean("estadoPedidoControler");

        emprestimos = new ArrayList<Emprestimo>();

        bookingTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        bookingTime.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);
        bookingTime.set(Calendar.MINUTE, 00);
        bookingTime.set(Calendar.SECOND, 00);
        bookingTime.set(Calendar.MILLISECOND, 00);

        for (Emprestimo e : this.crudService.getAll(Emprestimo.class))
        {
            if (e.getDataaprovacao() == null)
            {
                continue;
            }

            e.getEstadoPedido().setIdestadopedido(this.estadoPedidoControler.PENDING_BOOKING);
            e.getDataaprovacao().setTime(bookingTime.getTime());

            emprestimos.add(e);

            break;
        }

        if (emprestimos.size() < 1)
        {
            throw new Exception("Deve existir uma tupla na tabela emprestimo com vampo data de aprovacao diferente de null");
        }
    }

    @Test
    @Transactional
    public void processTest() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Calendar deadline = this.bookingDeadline.getDeadline(this.bookingTime);
        boolean thereIsCanceledRequest;

        /*
        * request before exceed deadline
        * */

        now.setTime(deadline.getTime());

        thereIsCanceledRequest = this.bookingDeadlineController.process(emprestimos, now);
        assertThat(thereIsCanceledRequest).isFalse();

        /*
         * request after deadline
         * */

        now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);
        thereIsCanceledRequest = this.bookingDeadlineController.process(emprestimos, now);
        assertThat(thereIsCanceledRequest).isTrue();
    }
}