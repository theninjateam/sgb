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
import sgb.controller.domainController.RoleController;
import sgb.domain.Emprestimo;
import sgb.domain.EmprestimoPK;
import sgb.domain.Role;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class BorrowedBooksDeadlineTest
{
    @Autowired
    private ApplicationContext context;
    private BorrowedBooksDeadline  borrowedBooksDeadline;
    private Emprestimo eStudent;
    private Emprestimo eTeacher;
    private CRUDService crudService;
    private RoleController roleController;
    private ConfigControler configControler;
    private Deadline deadline;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");

        this.borrowedBooksDeadline = (BorrowedBooksDeadline) context.getBean("borrowedBooksDeadline");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.roleController = (RoleController) context.getBean("roleController");
        this.configControler = (ConfigControler) context.getBean("configControler");
        this.deadline = (Deadline) context.getBean("deadline");

        List<Emprestimo> es = this.crudService.getAll(Emprestimo.class);
        Role rStudent = this.crudService.get(Role.class, this.roleController.STUDENT);
        Role rTeacher = this.crudService.get(Role.class, this.roleController.TEACHER);

        for (Emprestimo e: es)
        {
            if (e.getEmprestimoPK().getUtente().getRoles().contains(rStudent)
                && e.getDataaprovacao() != null)
            {
                eStudent = e;
                break;
            }
        }

        for (Emprestimo e: es)
        {
            if (e.getEmprestimoPK().getUtente().getRoles().contains(rTeacher)
                    && e.getDataaprovacao() != null)
            {
                eTeacher = e;
                break;
            }
        }
    }

    @Test
    @Transactional
    public void getDeadlineTest() throws Exception
    {
        Calendar actualDeadline = Calendar.getInstance();
        Calendar expetedDeadline = Calendar.getInstance();

        /************************************
        * STUDENT ROLE
        * **********************************/

        /*
        * Deadline For books borrowed on weekdays
        * */

        eStudent.getDataaprovacao().set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        actualDeadline  = this.borrowedBooksDeadline.getDeadline(eStudent);
        expetedDeadline.setTime(eStudent.getDataaprovacao().getTime());
        expetedDeadline.set(Calendar.DATE,
                expetedDeadline.get(Calendar.DATE) + this.configControler.DEADLINE_STUDENT_BORROWED_BOOKS);

        deadline.goToNextWorkingDay(expetedDeadline);

        assertThat(actualDeadline).isEqualByComparingTo(expetedDeadline);

        /*
         * Deadline For books borrowed on weekend
         * */

        eStudent.getDataaprovacao().set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        actualDeadline  = this.borrowedBooksDeadline.getDeadline(eStudent);
        expetedDeadline.setTime(eStudent.getDataaprovacao().getTime());
        expetedDeadline.set(Calendar.DATE,
                expetedDeadline.get(Calendar.DATE) + this.configControler.DEADLINE_STUDENT_BORROWED_BOOKS);

        deadline.goToNextWorkingDay(expetedDeadline);

        assertThat(actualDeadline).isEqualByComparingTo(expetedDeadline);

        /************************************
         * TEACHER ROLE
         * **********************************/

        /*
         * Deadline For books borrowed on weekdays
         * */

//        eTeacher.getDataaprovacao().set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//        actualDeadline  = this.borrowedBooksDeadline.getDeadline(eTeacher);
//        expetedDeadline.setTime(eTeacher.getDataaprovacao().getTime());
//        expetedDeadline.set(Calendar.DATE,
//                expetedDeadline.get(Calendar.DATE) + this.configControler.DEADLINE_TEACHER_BORROWED_BOOKS);
//
//        deadline.goToNextWorkingDay(expetedDeadline);
//
//        assertThat(actualDeadline).isEqualByComparingTo(expetedDeadline);

        /*
         * Deadline For books borrowed on weekend
         * */

//        eTeacher.getDataaprovacao().set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
//        actualDeadline  = this.borrowedBooksDeadline.getDeadline(eTeacher);
//        expetedDeadline.setTime(eTeacher.getDataaprovacao().getTime());
//        expetedDeadline.set(Calendar.DATE,
//                expetedDeadline.get(Calendar.DATE) + this.configControler.DEADLINE_TEACHER_BORROWED_BOOKS);
//
//        deadline.goToNextWorkingDay(expetedDeadline);
//
//        assertThat(actualDeadline).isEqualByComparingTo(expetedDeadline);
    }
}