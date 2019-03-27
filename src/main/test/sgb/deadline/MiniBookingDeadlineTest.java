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
import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class MiniBookingDeadlineTest
{
    @Autowired
    private ApplicationContext context;
    private MiniBookingDeadline miniBookingDeadline;
    private ConfigControler configControler;


    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");

        this.miniBookingDeadline = (MiniBookingDeadline) context.getBean("miniBookingDeadline");
        this.configControler = (ConfigControler) context.getBean("configControler");

    }

    @Test
    @Transactional
    public void getDeadlineTest() throws Exception
    {
        Calendar miniBookingTime;
        Calendar actualDeadline = Calendar.getInstance();
        Calendar expectedDeadline = Calendar.getInstance();

        /*******************************************
        * miniBooking on monday
        * ***************************************/

        /*
        * miniBooking before entry time
        * */

        miniBookingTime = this.getMiniBooking(Calendar.MONDAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS -1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline.setTime(miniBookingTime.getTime());
        expectedDeadline.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);
        expectedDeadline.set(Calendar.MINUTE, this.configControler.DEADLINE_REQUESTED_BOOKS);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);

        /*
         * miniBooking after exit time
         * */

        miniBookingTime = this.getMiniBooking(Calendar.MONDAY, this.configControler.EXIT_TIME_ON_WEEKDAYS + 1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline = this.getMiniBooking(Calendar.TUESDAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS + 1);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);

        /*
         * miniBooking on working time
         * */

        miniBookingTime = this.getMiniBooking(Calendar.MONDAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS + 1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline.setTime(miniBookingTime.getTime());
        expectedDeadline.set(Calendar.MINUTE, this.configControler.DEADLINE_REQUESTED_BOOKS);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);


        /*******************************************
         * miniBooking on friday after exit time
         * ***************************************/

        miniBookingTime = this.getMiniBooking(Calendar.FRIDAY, this.configControler.EXIT_TIME_ON_WEEKDAYS + 1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline = this.getMiniBooking(Calendar.SATURDAY, this.configControler.ENTRY_TIME_ON_SATURDAY + 1);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);


        /*******************************************
         * miniBooking on Saturday
         * ***************************************/

        /*
         * miniBooking before entry time
         * */

        miniBookingTime = this.getMiniBooking(Calendar.SATURDAY, this.configControler.ENTRY_TIME_ON_SATURDAY -1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline.setTime(miniBookingTime.getTime());
        expectedDeadline.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_SATURDAY);
        expectedDeadline.set(Calendar.MINUTE, this.configControler.DEADLINE_REQUESTED_BOOKS);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);

        /*
         * miniBooking on after exit time
         * */

        miniBookingTime = this.getMiniBooking(Calendar.SATURDAY, this.configControler.EXIT_TIME_ON_SATURDAY + 1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline.setTime(miniBookingTime.getTime());

        while (true)
        {
            expectedDeadline.set(Calendar.DATE, expectedDeadline.get(Calendar.DATE) + 1);
            if ( expectedDeadline.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) { break; }
        }

        expectedDeadline.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);
        expectedDeadline.set(Calendar.MINUTE, this.configControler.DEADLINE_REQUESTED_BOOKS);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);

        /*
         * miniBooking on working time
         * */

        miniBookingTime = this.getMiniBooking(Calendar.SATURDAY, this.configControler.ENTRY_TIME_ON_SATURDAY+ 1);
        actualDeadline = this.miniBookingDeadline.getDeadline(miniBookingTime);

        expectedDeadline.setTime(miniBookingTime.getTime());
        expectedDeadline.set(Calendar.MINUTE, this.configControler.DEADLINE_REQUESTED_BOOKS);

        assertThat(actualDeadline).isEqualByComparingTo(expectedDeadline);

    }

    public Calendar getMiniBooking(int day, int hour)
    {
        Calendar calendar  = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);

        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }
}