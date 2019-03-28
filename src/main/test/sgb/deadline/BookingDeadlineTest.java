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
import sgb.domain.Emprestimo;
import sgb.service.CRUDService;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class BookingDeadlineTest
{
    @Autowired
    private ApplicationContext context;
    private BookingDeadline bookingDeadline;
    private CRUDService crudService;
    private ConfigControler configControler;
    private Emprestimo emprestimo;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");

        this.configControler = (ConfigControler) context.getBean("configControler");
        this.bookingDeadline = (BookingDeadline) context.getBean("bookingDeadline");
        this.crudService = (CRUDService) context.getBean("CRUDService");

        for (Emprestimo e: this.crudService.getAll(Emprestimo.class))
        {
            if (e.getDataaprovacao() != null)
            {
                emprestimo = e;
                break;
            }
        }
    }

    @Test
    @Transactional
    public void getDeadlineTest() throws Exception
    {
        Calendar actualDeadline = Calendar.getInstance();
        Calendar expectedDeadline = Calendar.getInstance();

        /*
        * Booking made on weekdays
        * */

        emprestimo.getDataaprovacao().set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        actualDeadline = this.bookingDeadline.getDeadline(emprestimo.getDataaprovacao());

        expectedDeadline.setTime(emprestimo.getDataaprovacao().getTime());
        expectedDeadline.set(Calendar.DATE, expectedDeadline.get(Calendar.DATE) + this.configControler.DEADLINE_RESERVED_BOOKS);
        this.bookingDeadline.goToNextWorkingDay(expectedDeadline);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);

        /*
         * Booking made on weekend
         * */

        emprestimo.getDataaprovacao().set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        actualDeadline = this.bookingDeadline.getDeadline(emprestimo.getDataaprovacao());

        expectedDeadline.setTime(emprestimo.getDataaprovacao().getTime());
        expectedDeadline.set(Calendar.DATE, expectedDeadline.get(Calendar.DATE) + this.configControler.DEADLINE_RESERVED_BOOKS);
        this.bookingDeadline.goToNextWorkingDay(expectedDeadline);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);
    }
}