//package sgb.deadline;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Calendar;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
//
///**
// * @author Fonseca, bfonseca@unilurio.ac.mz
// */
//
//
//
//public class DeadlineTest
//{
//    @Autowired
//    private ApplicationContext context;
//    private Deadline deadline;
//    @Before
//    @Transactional
//    public void before() throws Exception
//    {
//        System.out.println("Setting it up!");
//
//        this.deadline = (Deadline) context.getBean("deadline");
//    }
//
//    @Test
//    @Transactional
//    public void exceededDeadlineTest() throws Exception
//    {
//        Calendar deadline = Calendar.getInstance();
//        Calendar current = Calendar.getInstance();
//
//        current.setTime(deadline.getTime());
//        assertThat(this.deadline.exceededDeadline(deadline, current)).isFalse();
//
//
//        current.set(Calendar.DATE, deadline.get(Calendar.DATE) - 1);
//        assertThat(this.deadline.exceededDeadline(deadline, current)).isFalse();
//
//        current.set(Calendar.DATE, deadline.get(Calendar.DATE) + 1);
//        assertThat(this.deadline.exceededDeadline(deadline, current)).isTrue();
//    }
//
//
//}