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
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.controller.domainController.RoleController;
import sgb.domain.Emprestimo;
import sgb.domain.EstadoDevolucao;
import sgb.domain.Role;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */



public class BorrowedBooksDeadlineControllerTest
{
    @Autowired
    private ApplicationContext context;
    private BorrowedBooksDeadline borrowedBooksDeadline;
    private BorrowedBooksDeadlineController borrowedBooksDeadlineController;
    private ConfigControler configControler;
    private CRUDService crudService;
    private EstadoPedidoControler estadoPedidoControler;
    private RoleController roleController;
    private EstadoDevolucaoControler estadoDevolucaoControler;
    private List<Emprestimo>  emprestimos;
    Calendar dataAprovacao = Calendar.getInstance();
    private Emprestimo eStudent;
    private Emprestimo eTeacher;

    @Before
    @Transactional
    public void before() throws Exception {
        System.out.println("Setting it up!");

        this.borrowedBooksDeadline = (BorrowedBooksDeadline) context.getBean("borrowedBooksDeadline");
        this.configControler = (ConfigControler) context.getBean("configControler");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.borrowedBooksDeadlineController = (BorrowedBooksDeadlineController) context.getBean("borrowedBooksDeadlineController");
        this.estadoPedidoControler = (EstadoPedidoControler) context.getBean("estadoPedidoControler");
        this.roleController  = (RoleController) context.getBean("roleController");
        this.estadoDevolucaoControler = (EstadoDevolucaoControler) context.getBean("estadoDevolucaoControler");
        emprestimos = new ArrayList<Emprestimo>();

        dataAprovacao.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        dataAprovacao.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS + 1);
        dataAprovacao.set(Calendar.MINUTE, 00);
        dataAprovacao.set(Calendar.SECOND, 00);
        dataAprovacao.set(Calendar.MILLISECOND, 00);

        Role rStudent = this.crudService.get(Role.class, this.roleController.STUDENT);
        Role rTeacher = this.crudService.get(Role.class, this.roleController.TEACHER);

        List<Emprestimo> es = this.crudService.getAll(Emprestimo.class);
        emprestimos = new ArrayList<Emprestimo>();


        EstadoDevolucao estadoDevolucao = this.crudService.get(EstadoDevolucao.class, this.estadoDevolucaoControler.NAO_DEVOLVIDO);

        for (Emprestimo e: es)
        {
            if (e.getEmprestimoPK().getUtente().getRoles().contains(rStudent)
                    && e.getDataaprovacao() != null)
            {
                eStudent = e;
                eStudent.setEstadoDevolucao(estadoDevolucao);
                break;
            }
        }

        for (Emprestimo e: es)
        {
            if (e.getEmprestimoPK().getUtente().getRoles().contains(rTeacher)
                    && e.getDataaprovacao() != null)
            {
                eTeacher = e;
                eTeacher.setEstadoDevolucao(estadoDevolucao);
                eTeacher.setQuantidade(0);
                break;
            }
        }

        eStudent.setDataaprovacao(dataAprovacao);
        eStudent.setDatadevolucao(this.borrowedBooksDeadline.getDeadline(eStudent));
        eTeacher.setDataaprovacao(dataAprovacao);
        eTeacher.setDatadevolucao(this.borrowedBooksDeadline.getDeadline(eTeacher));

        emprestimos.add(eStudent);
        emprestimos.add(eTeacher);

        if (emprestimos.size() < 2)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Deve existir duas tuplas na tabela emprestimo com o campo data de aprovacao diferente de null\n");
            builder.append("uma tupla para professor uma para estudante\n");

            throw new Exception(builder.toString());
        }
    }

    @Test
    @Transactional
    public void processTest() throws Exception
    {
        Calendar now = Calendar.getInstance();
        Calendar deadline = this.borrowedBooksDeadline.getDeadline(emprestimos.get(0));
        boolean thereIsFinedBorrow;

        /**********************************************
        * STUDENT
        * **********************************************/
        /*
        * before exceed deadline
        * */

        emprestimos.remove(1);

        now.setTime(deadline.getTime());

        thereIsFinedBorrow = this.borrowedBooksDeadlineController.process(emprestimos, now);
        assertThat(thereIsFinedBorrow).isFalse();

        /*
         * after deadline
         * */

        now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);
        this.borrowedBooksDeadline.goToNextWorkingDay(now);

        thereIsFinedBorrow = this.borrowedBooksDeadlineController.process(emprestimos, now);
        assertThat(thereIsFinedBorrow).isTrue();

        /**********************************************
         * TEACHER
         * **********************************************/
        /*
         * before exceed deadline
         * */

        emprestimos = new ArrayList<Emprestimo>();
        emprestimos.add(eTeacher);

        deadline = this.borrowedBooksDeadline.getDeadline(emprestimos.get(0));
        now.setTime(deadline.getTime());

        thereIsFinedBorrow = this.borrowedBooksDeadlineController.process(emprestimos, now);
        assertThat(thereIsFinedBorrow).isFalse();

        /*
         * after deadline
         * */

        now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);
        this.borrowedBooksDeadline.goToNextWorkingDay(now);

        thereIsFinedBorrow = this.borrowedBooksDeadlineController.process(emprestimos, now);
        assertThat(thereIsFinedBorrow).isTrue();

    }
}