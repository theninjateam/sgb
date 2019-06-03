package sgb.deadline;

import org.junit.After;
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
 * @author Bania Fonseca, bfonseca@unilurio.ac.mz
 */

public class DeadlineThreadManagerTest
{
    @Autowired
    private ApplicationContext context;
    private DeadlineThreadManager deadlineThreadManager;
    private ConfigControler configControler;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");
        this.deadlineThreadManager = (DeadlineThreadManager) context.getBean("deadlineThreadManager");
        this.deadlineThreadManager.running.set(false);
        this.configControler = (ConfigControler) context.getBean("configControler");
    }

    @Test
    @Transactional
    public void startMiniBookingDeadlineControllerTest()
    {
        Calendar date = Calendar.getInstance();

        /***************************************************************
         * Weekend
         ****************************************************************/

        /**
         * SATURDAY when server is starting before exit time*/

        this.deadlineThreadManager.isServerStarting.set(true);
        this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * SATURDAY when time is between [ENTRY_TIME_ON_SATURDAY - 2, EXIT_TIME_ON_SATURDAY)
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY + 2);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isTrue();

        /**
        * SATURDAY after exit time
        * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isFalse();

        /***************************************************************
         * Weekdays
         ****************************************************************/

        /**
         * MONDAY when server is starting before exit time*/

        this.deadlineThreadManager.isServerStarting.set(true);
        this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_WEEKDAYS - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * MONDAY when time is between [ENTRY_TIME_ON_WEEKDAYS - 2, EXIT_TIME_ON_WEEKDAYS )
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_WEEKDAYS + 2);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * MONDAY after exit
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_WEEKDAYS);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isFalse();

    }

    @Test
    @Transactional
    public void startBookingDeadlineControllerTest()
    {
        Calendar date = Calendar.getInstance();

        /***************************************************************
         * Weekend
         ****************************************************************/

        /**
         * SATURDAY when server is starting before exit time*/

        this.deadlineThreadManager.isServerStarting.set(true);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * SATURDAY when time is between [ENTRY_TIME_ON_SATURDAY - 2, ENTRY_TIME_ON_SATURDAY)
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * SATURDAY after ENTRY_TIME_ON_SATURDAY
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY + 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isFalse();

        /**
         * SATURDAY after exit time
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isFalse();

        /***************************************************************
         * Weekdays
         ****************************************************************/

        /**
         * MONDAY when server is starting before exit time*/

        this.deadlineThreadManager.isServerStarting.set(true);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_WEEKDAYS - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * MONDAY when time is between [ENTRY_TIME_ON_WEEKDAYS - 2, ENTRY_TIME_ON_WEEKDAYS)
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_WEEKDAYS - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isTrue();

        /**
         * MONDAY after ENTRY_TIME_ON_WEEKDAYS
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_WEEKDAYS + 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isFalse();

        /**
         * MONDAY after exit
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBookingDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_WEEKDAYS);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBookingDeadlineControllerStarted.get()).isFalse();

    }

    @Test
    @Transactional
    public void startBorrowedBooksDeadlineControllerTest()
    {
        Calendar date = Calendar.getInstance();

        /***************************************************************
         * Weekend
         ****************************************************************/

        /**
         * SATURDAY when server is starting before exit time*/

        this.deadlineThreadManager.isServerStarting.set(true);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isTrue();

        /**
         * SATURDAY when time is between [ENTRY_TIME_ON_SATURDAY - 2, ENTRY_TIME_ON_SATURDAY)
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isTrue();

        /**
         * SATURDAY after ENTRY_TIME_ON_SATURDAY
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY + 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isFalse();

        /**
         * SATURDAY after exit time
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasMiniBookingDeadlineControllerStarted.get()).isFalse();

        /***************************************************************
         * Weekdays
         ****************************************************************/

        /**
         * MONDAY when server is starting before exit time*/

        this.deadlineThreadManager.isServerStarting.set(true);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_WEEKDAYS - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isTrue();

        /**
         * MONDAY when time is between [ENTRY_TIME_ON_WEEKDAYS - 2, ENTRY_TIME_ON_WEEKDAYS)
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_WEEKDAYS - 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isTrue();

        /**
         * MONDAY after ENTRY_TIME_ON_WEEKDAYS
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_WEEKDAYS + 1);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startBorrowedBooksDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isFalse();

        /**
         * MONDAY after exit
         * */

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.set(false);

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_WEEKDAYS);

        this.deadlineThreadManager.today =  date;
        this.deadlineThreadManager.startMiniBookingDeadlineController();

        assertThat(this.deadlineThreadManager.wasBorrowedBooksDeadlineControllerStarted.get()).isFalse();
    }
    @Test
    @Transactional
    public void startSendEmailControllerTest()
    {


        this.deadlineThreadManager.isServerStarting.set(true);

        this.deadlineThreadManager.delayForEmail = 12*60*1000;
        this.deadlineThreadManager.startSendEmailController();

        assertThat(this.deadlineThreadManager.wasSendEmailControllerStarted.get()).isTrue();

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.delayForEmail = 120*60*1000;
        this.deadlineThreadManager.startSendEmailController();
        assertThat(this.deadlineThreadManager.wasSendEmailControllerStarted.get()).isTrue();

        this.deadlineThreadManager.isServerStarting.set(false);
        this.deadlineThreadManager.delayForEmail = 12*60*1000;
        this.deadlineThreadManager.startSendEmailController();
        assertThat(this.deadlineThreadManager.wasSendEmailControllerStarted.get()).isFalse();

    }
    @After
    @Transactional
    public void after() throws Exception
    {
        System.out.println("down !");
    }
}
