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
import sgb.domain.Config;
import sgb.domain.Emprestimo;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.HashMap;

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
    private CRUDService crudService;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");
        this.deadlineThreadManager = (DeadlineThreadManager) context.getBean("deadlineThreadManager");
        this.configControler = (ConfigControler) context.getBean("configControler");
        this.crudService = (CRUDService) context.getBean("CRUDService");

        Config config = this.crudService.get(Config.class, "SYS_DEBUGING");
        config.setValor("1");
        this.crudService.update(config);

    }

    @Test
    @Transactional
    public void startThreadsOnSundayAndSaturdayTest()
    {
        Calendar date = Calendar.getInstance();

        /**
         * SUNDAY*/
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);


        this.deadlineThreadManager.getIsServerStarting().set(true);
        this.deadlineThreadManager.getWasThreadsStarted().set(false);
        this.deadlineThreadManager.setToday(date);
        this.deadlineThreadManager.startThreads();

        assertThat(this.deadlineThreadManager.getWasThreadsStarted().get()).isFalse();

        /**
         * SATURDAY after exit  time*/
        this.deadlineThreadManager.getWasThreadsStarted().set(false);
        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.EXIT_TIME_ON_SATURDAY + 1);

        this.deadlineThreadManager.setToday(date);
        this.deadlineThreadManager.startThreads();

        assertThat(this.deadlineThreadManager.getWasThreadsStarted().get()).isFalse();


        /**
         * SATURDAY before entry  time*/
        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY - 1);

        this.deadlineThreadManager.getWasThreadsStarted().set(false);
        this.deadlineThreadManager.setToday(date);
        this.deadlineThreadManager.startThreads();

        assertThat(this.deadlineThreadManager.getWasThreadsStarted().get()).isTrue();


        /**
         * SATURDAY on working time after server start*/
        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY + 1);

        this.deadlineThreadManager.getWasThreadsStarted().set(false);
        this.deadlineThreadManager.getIsServerStarting().set(false);
        this.deadlineThreadManager.setToday(date);
        this.deadlineThreadManager.startThreads();

        assertThat(this.deadlineThreadManager.getWasThreadsStarted().get()).isFalse();

        /**
         * SATURDAY on working time and server is starting*/
        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        date.set(Calendar.HOUR_OF_DAY, configControler.ENTRY_TIME_ON_SATURDAY + 1);

        this.deadlineThreadManager.getWasThreadsStarted().set(false);
        this.deadlineThreadManager.getIsServerStarting().set(true);
        this.deadlineThreadManager.setToday(date);
        this.deadlineThreadManager.startThreads();

        assertThat(this.deadlineThreadManager.getWasThreadsStarted().get()).isTrue();
    }

    @After
    @Transactional
    public void after() throws Exception
    {
        System.out.println("down !");

        Config config = this.crudService.get(Config.class, "SYS_DEBUGING");
        config.setValor("0");
        this.crudService.update(config);
    }

}
