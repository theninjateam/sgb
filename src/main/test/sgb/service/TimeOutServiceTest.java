package sgb.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.domain.Config;
import sgb.service.CRUDService;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class TimeOutServiceTest
{
    @Autowired
    private ApplicationContext context;
    private CRUDService crudService;
    private TimeOutService timeOutService;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.timeOutService = (TimeOutService) context.getBean("timeOutService");
    }

    @Test
    @Transactional
    public void isTimeOutTest() throws Exception
    {
        int maximumTime = timeOutService.geteCSingleton().eRSingleton.MAXIMUM_TIME;

        //weekday
        Calendar entryDate = getCalendar(2018, Calendar.DECEMBER , 21, 10 ,8);
        Calendar current = getCalendar(2018, Calendar.DECEMBER , 21, 10 ,maximumTime - 8);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(false);

        current = getCalendar(2018, Calendar.DECEMBER , 21, 10 ,maximumTime + 8 + 1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(true);

        //weekdays after exit time
        entryDate = getCalendar(2018, Calendar.DECEMBER , 20, 22 ,8);
        current = getCalendar(2018, Calendar.DECEMBER , 20, 23 , 8);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(false);

        current = getCalendar(2018, Calendar.DECEMBER , 21, 8 ,1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(true);

        //friday after exit time
        entryDate = getCalendar(2018, Calendar.DECEMBER , 21, 22 ,8);
        current = getCalendar(2018, Calendar.DECEMBER , 22, 8 , 8);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(false);

        current = getCalendar(2018, Calendar.DECEMBER , 22,
                timeOutService.geteCSingleton().eRSingleton.ENTRY_TIME_ON_SATURDAY ,maximumTime + 1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(true);

        //Saturday
        entryDate = getCalendar(2018, Calendar.DECEMBER , 22, 10 ,8);
        //sunday
        current = getCalendar(2018, Calendar.DECEMBER , 23, 10 ,maximumTime + 8 + 1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(false);

        //monday
        current = getCalendar(2018, Calendar.DECEMBER , 24, 10 ,maximumTime + 8 + 1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(true);


        //Sunday
        entryDate = getCalendar(2018, Calendar.DECEMBER , 23, 10 ,8);
        //monday
        current = getCalendar(2018, Calendar.DECEMBER , 24, 4 ,maximumTime + 8 + 1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(false);

        //monday
        current = getCalendar(2018, Calendar.DECEMBER , 24, 7 ,maximumTime + 1);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(true);

//        System.out.println("********************************** I ************************");
//        System.out.println("entryDate: "+ entryDate.getTime());
//        System.out.println("deadLine: "+ timeOutService.getLiftingTimeout(entryDate).getTime());
//        System.out.println("currentDate: "+ current.getTime());

    }

    public Calendar getCalendar(int year, int month, int date, int hours ,int minutes)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);

        calendar.set(Calendar.MONTH, month);

        calendar.set(Calendar.DAY_OF_MONTH, date);

        calendar.set(Calendar.HOUR_OF_DAY, hours);

        calendar.set(Calendar.MINUTE, minutes);

        calendar.set(Calendar.SECOND, 00);

        calendar.set(Calendar.MILLISECOND, 00);

        return calendar;
    }
}