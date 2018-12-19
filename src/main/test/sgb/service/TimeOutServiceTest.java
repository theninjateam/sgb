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
        Config config =  this.crudService.get(Config.class, "MAXIMUM_TIME");
        config.setValor("1");

        this.crudService.update(config);


        Calendar entryDate = Calendar.getInstance();
        entryDate.set(Calendar.AM_PM, Calendar.AM);
        entryDate.set(Calendar.DAY_OF_MONTH, 14);
        entryDate.set(Calendar.HOUR_OF_DAY, 10);
        entryDate.set(Calendar.MINUTE, 7);
        entryDate.set(Calendar.SECOND, 24);
        entryDate.set(Calendar.MILLISECOND, 2);


        Calendar current = Calendar.getInstance();
        current.set(Calendar.AM_PM, Calendar.AM);
        current.set(Calendar.DAY_OF_MONTH, 14);
        current.set(Calendar.HOUR_OF_DAY, 10);
        current.set(Calendar.MINUTE, 10);
        current.set(Calendar.SECOND, 24);
        current.set(Calendar.MILLISECOND, 2);

        Calendar liftingTimeout = this.timeOutService.getLiftingTimeout(entryDate);

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(true);


        System.out.println("entryDate: "+entryDate.getTime());
        System.out.println("current: : "+current.getTime());
        System.out.println("liftingTimeout: : "+liftingTimeout.getTime());

        current.set(Calendar.MINUTE, 8);
        current.set(Calendar.SECOND, 0);

        liftingTimeout = this.timeOutService.getLiftingTimeout(entryDate);

        System.out.println("------------------");
        System.out.println("entryDate: "+entryDate.getTime());
        System.out.println("current: : "+current.getTime());
        System.out.println("liftingTimeout: : "+liftingTimeout.getTime());

        assertThat(timeOutService.isTimeOut(entryDate, current)).isEqualTo(false);

    }

}