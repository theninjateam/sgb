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

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class DeadlineReservedBooksTest
{
    @Autowired
    private ApplicationContext context;
    private DeadlineReservedBooks deadlineReservedBooks;
    private ConfigControler configControler;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");
        this.deadlineReservedBooks = (DeadlineReservedBooks) context.getBean("deadlineReservedBooks");
        this.configControler = (ConfigControler) context.getBean("configControler");
    }

    @Test
    @Transactional
    public void ExcededDeadlineTest() throws Exception
    {
        Calendar reserverdDate;
        Calendar currentDate;
        Calendar deadline;

        /**
         *
         * DeadlineReservedBooks on friday
         *
         **/

        reserverdDate = Calendar.getInstance();
        reserverdDate.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        deadline = deadlineReservedBooks.getDeadline(reserverdDate);
        currentDate = deadline;
        assertThat(deadlineReservedBooks.exceededDeadline(reserverdDate, currentDate)).isFalse();

        currentDate.set(Calendar.DATE, deadline.get(Calendar.DATE) +1);

        assertThat(deadlineReservedBooks.exceededDeadline(reserverdDate, currentDate)).isTrue();

        /**
         *
         * DeadlineReservedBooks on monday
         *
         **/

        reserverdDate = Calendar.getInstance();
        reserverdDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        deadline = deadlineReservedBooks.getDeadline(reserverdDate);
        currentDate = deadline;
        assertThat(deadlineReservedBooks.exceededDeadline(reserverdDate, currentDate)).isFalse();

        currentDate.set(Calendar.DATE, deadline.get(Calendar.DATE) +1);

        assertThat(deadlineReservedBooks.exceededDeadline(reserverdDate, currentDate)).isTrue();

        /**
         *
         * DeadlineReservedBooks on saturday
         *
         * */

        reserverdDate = Calendar.getInstance();
        reserverdDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        deadline = deadlineReservedBooks.getDeadline(reserverdDate);
        currentDate = deadline;
        assertThat(deadlineReservedBooks.exceededDeadline(reserverdDate, currentDate)).isFalse();

        currentDate.set(Calendar.DATE, deadline.get(Calendar.DATE) +1);

        assertThat(deadlineReservedBooks.exceededDeadline(reserverdDate, currentDate)).isTrue();
    }
}