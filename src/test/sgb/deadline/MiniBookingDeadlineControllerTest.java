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
import sgb.domain.EstadoPedido;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class MiniBookingDeadlineControllerTest
{
    @Autowired
    private ApplicationContext context;
    private MiniBookingDeadline miniBookingDeadline;
    private MiniBookingDeadlineController miniBookingDeadlineController;
    private ConfigControler configControler;
    private CRUDService crudService;
    private EstadoPedidoControler estadoPedidoControler;
    private List<Emprestimo>  emprestimos;
    Calendar miniBookingTime = Calendar.getInstance();

    @Before
    @Transactional
    public void before() throws Exception {
        System.out.println("Setting it up!");

        this.miniBookingDeadline = (MiniBookingDeadline) context.getBean("miniBookingDeadline");
        this.configControler = (ConfigControler) context.getBean("configControler");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.miniBookingDeadlineController = (MiniBookingDeadlineController) context.getBean("miniBookingDeadlineController");
        this.estadoPedidoControler = (EstadoPedidoControler) context.getBean("estadoPedidoControler");

        emprestimos = new ArrayList<Emprestimo>();

        miniBookingTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        miniBookingTime.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);
        miniBookingTime.set(Calendar.MINUTE, 00);
        miniBookingTime.set(Calendar.SECOND, 00);
        miniBookingTime.set(Calendar.MILLISECOND, 00);

        for (Emprestimo e : this.crudService.getAll(Emprestimo.class))
        {
            e.getEstadoPedido().setIdestadopedido(this.estadoPedidoControler.PENDING_MINI_BOOKING);
            e.getEmprestimoPK().getDataentradapedido().setTime(miniBookingTime.getTime());

            emprestimos.add(e);

            break;
        }

    }

    @Test
    @Transactional
    public void processTest() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Calendar deadline = this.miniBookingDeadline.getDeadline(this.miniBookingTime);
        boolean thereIsCanceledRequest;

        /*
        * request before exceed deadline
        * */

        now.setTime(deadline.getTime());

        thereIsCanceledRequest = this.miniBookingDeadlineController.process(emprestimos, now);
        assertThat(thereIsCanceledRequest).isFalse();

        /*
         * request after deadline
         * */

        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + 1);
        thereIsCanceledRequest = this.miniBookingDeadlineController.process(emprestimos, now);
        assertThat(thereIsCanceledRequest).isTrue();

    }
}