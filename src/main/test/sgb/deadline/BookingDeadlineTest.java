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

        this.bookingDeadline = (BookingDeadline) context.getBean("bookingDeadline");
    }

    @Test
    @Transactional
    public void getDeadlineTest() throws Exception
    {
        Calendar currentDate = Calendar.getInstance();
        Calendar reserverdDate = Calendar.getInstance();
        reserverdDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        Calendar deadline = bookingDeadline.getDeadline(reserverdDate);

        /**
         * BookingDeadline on friday
         **/

        currentDate.setTime(deadline.getTime());
        assertThat(bookingDeadline.exceededDeadline(deadline, currentDate)).isFalse();
        currentDate.set(Calendar.DATE, deadline.get(Calendar.DATE) + 1);
        assertThat(bookingDeadline.exceededDeadline(deadline, currentDate)).isTrue();

        /**
         * BookingDeadline on monday
         **/
        reserverdDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        deadline = bookingDeadline.getDeadline(reserverdDate);
        currentDate.setTime(deadline.getTime());

        assertThat(bookingDeadline.exceededDeadline(deadline, currentDate)).isFalse();
        currentDate.set(Calendar.DATE, deadline.get(Calendar.DATE) +1);
        assertThat(bookingDeadline.exceededDeadline(deadline, currentDate)).isTrue();

        /**
         * BookingDeadline on saturday
         * */
        reserverdDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        deadline = bookingDeadline.getDeadline(reserverdDate);
        currentDate.setTime(deadline.getTime());

        assertThat(bookingDeadline.exceededDeadline(deadline, currentDate)).isFalse();
        currentDate.set(Calendar.DATE, deadline.get(Calendar.DATE) +1);
        assertThat(bookingDeadline.exceededDeadline(deadline, currentDate)).isTrue();
    }
}