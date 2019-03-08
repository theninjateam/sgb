package sgb.deadline;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.deadline.DeadlineRequestedBooks;
import sgb.service.CRUDService;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class DeadlineRequestedBooksTest
{
    @Autowired
    private ApplicationContext context;
    private CRUDService crudService;
    private DeadlineRequestedBooks deadlineRequestedBooks;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.deadlineRequestedBooks = (DeadlineRequestedBooks) context.getBean("deadlineRequestedBooks");
    }

    @Test
    @Transactional
    public void ExceededDeadlineForWeekDaysTest() throws Exception
    {
        int maximumTime = deadlineRequestedBooks.getConfigSingleton().MAXIMUM_TIME;

        int entryTime = deadlineRequestedBooks.getConfigSingleton().ENTRY_TIME_ON_WEEKDAYS;

        int exitTime = deadlineRequestedBooks.getConfigSingleton().EXIT_TIME_ON_WEEKDAYS;

        int entryTimeOnSaturday = deadlineRequestedBooks.getConfigSingleton().ENTRY_TIME_ON_SATURDAY;


        /***
         *
         * DeadlineRequestedBooks Before Entry Time
         *
         * */

        Calendar requestDate = getCalendar(Calendar.MONDAY, entryTime - 1, 30);

        Calendar currentDate = getCalendar(Calendar.MONDAY, entryTime - 1, 40);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.MONDAY, entryTime, maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();


        /***
         *
         * DeadlineRequestedBooks After Exit Time
         *
         * */

        requestDate = getCalendar(Calendar.MONDAY, exitTime, 00);

        currentDate = getCalendar(Calendar.MONDAY, exitTime + 2, maximumTime + 2);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.TUESDAY, entryTime, maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();


        /***
         *
         * DeadlineRequestedBooks After Exit Time on friday
         *
         * */

        requestDate = getCalendar(Calendar.FRIDAY, exitTime, 00);

        currentDate = getCalendar(Calendar.FRIDAY, exitTime + 2, maximumTime + 2);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday, maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();

        /***
         *
         * DeadlineRequestedBooks in work Time
         *
         * */

        requestDate = getCalendar(Calendar.FRIDAY, entryTime, 20);

        currentDate = getCalendar(Calendar.FRIDAY, entryTime, 20);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.FRIDAY, entryTime, 20 + maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();


        /***
         *
         * DeadlineRequestedBooks in work Time, N minutes to exit time
         *
         * */

        requestDate = getCalendar(Calendar.FRIDAY, exitTime - 1, 20);

        currentDate = getCalendar(Calendar.FRIDAY, exitTime - 1, 20);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.FRIDAY, exitTime -1, 20 + maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();
    }

    @Test
    @Transactional
    public void exceededDeadlineForWeekendTest() throws Exception
    {
        int maximumTime = deadlineRequestedBooks.getConfigSingleton().MAXIMUM_TIME;

        int entryTime = deadlineRequestedBooks.getConfigSingleton().ENTRY_TIME_ON_WEEKDAYS;

        int exitTime = deadlineRequestedBooks.getConfigSingleton().EXIT_TIME_ON_WEEKDAYS;

        int entryTimeOnSaturday = deadlineRequestedBooks.getConfigSingleton().ENTRY_TIME_ON_SATURDAY;

        int exitTimeOnSaturday = deadlineRequestedBooks.getConfigSingleton().EXIT_TIME_ON_SATURDAY;

        /***
         *
         * DeadlineRequestedBooks Before Entry Time
         *
         * */

        Calendar requestDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday - 1, 30);

        Calendar currentDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday - 1, 40);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday, maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();


        /***
         *
         * DeadlineRequestedBooks After Exit Time
         *
         * */

        requestDate = getCalendar(Calendar.SATURDAY, exitTimeOnSaturday, 00);

        currentDate = getCalendar(Calendar.SUNDAY, exitTimeOnSaturday + 2, maximumTime + 2);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();

        currentDate = getCalendar(Calendar.SATURDAY, entryTime, maximumTime + 1);
        deadlineRequestedBooks.incrementNDays(currentDate, 2); // monday

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();


        /***
         *
         * DeadlineRequestedBooks in work Time
         *
         * */

        requestDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday, 20);

        currentDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday, 20);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.SATURDAY, entryTimeOnSaturday, 20 + maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();


        /***
         *
         * DeadlineRequestedBooks in work Time, N minutes to exit time
         *
         * */

        requestDate = getCalendar(Calendar.SATURDAY, exitTimeOnSaturday - 1, 20);

        currentDate = getCalendar(Calendar.SATURDAY, exitTimeOnSaturday - 1, 20);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isFalse();


        currentDate = getCalendar(Calendar.SATURDAY, exitTimeOnSaturday - 1, 20 + maximumTime + 1);

        assertThat(deadlineRequestedBooks.exceededDeadline(requestDate, currentDate)).isTrue();
    }


    public Calendar getCalendar(int dayOfWeek, int hours ,int minutes)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);


        calendar.set(Calendar.HOUR_OF_DAY, hours);

        calendar.set(Calendar.MINUTE, minutes);

        calendar.set(Calendar.SECOND, 00);

        calendar.set(Calendar.MILLISECOND, 00);

        return calendar;
    }
}