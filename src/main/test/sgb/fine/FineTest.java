package sgb.fine;

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
import sgb.controller.domainController.EstadoMultaControler;
import sgb.controller.domainController.MultaController;
import sgb.domain.Emprestimo;
import sgb.domain.Multa;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class FineTest
{
    @Autowired
    private ApplicationContext context;
    private Fine fine;
    private CRUDService crudService;
    private MultaController multaController;
    private Emprestimo emprestimo;
    private ConfigControler configControler;
    private EstadoMultaControler estadoMultaControler;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");

        this.fine = (Fine) context.getBean("fine");
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.multaController = (MultaController) context.getBean("multaController");
        this.configControler = (ConfigControler) context.getBean("configControler");
        this.estadoMultaControler = (EstadoMultaControler) context.getBean("estadoMultaControler");

        for (Emprestimo e : this.crudService.getAll(Emprestimo.class))
        {
            if (e.getDatadevolucao() != null)
            {
                emprestimo  = e;
                break;
            }
        }

    }

    @Test
    @Transactional
    public void getDelayDaysTest() throws Exception
    {
        Calendar deadline = Calendar.getInstance();
        Calendar current = Calendar.getInstance();

        assertThat(this.fine.getDelayDays(current, deadline)).isEqualTo(0);

        deadline.set(Calendar.MONTH, Calendar.JANUARY);
        deadline.set(Calendar.DATE, 1);

        current.setTime(deadline.getTime());
        current.set(Calendar.DATE, 7);

        assertThat(this.fine.getDelayDays(current, deadline)).isEqualTo(6);

        deadline.set(Calendar.MONTH, Calendar.JANUARY);
        deadline.set(Calendar.DATE, 1);

        current.setTime(deadline.getTime());
        current.set(Calendar.DATE, 2);

        assertThat(this.fine.getDelayDays(current, deadline)).isEqualTo(1);

    }

    @Test
    @Transactional
    public void fine_WasFinedBefore_pay_revokeTest() throws Exception
    {
        Calendar deadline = emprestimo.getDatadevolucao();
        Calendar now = Calendar.getInstance();

        /***************************************************
         * WasFinedBefore method
         ********************************************* */

        now.setTime(emprestimo.getDatadevolucao().getTime());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);

        assertThat(this.fine.wasFinedBefore(emprestimo.getEmprestimoPK())).isFalse();

        /***************************************************
         * fine method
         ********************************************* */

        Multa multa = null;

        this.fine.fine(emprestimo, now);
        multa = this.multaController.getFine(emprestimo.getEmprestimoPK());

        assertThat(this.fine.wasFinedBefore(emprestimo.getEmprestimoPK())).isTrue();
        assertThat(multa).isNotEqualTo(null);
        assertThat(multa.getEstadoMulta().getIdestadomulta()).isEqualTo(this.estadoMultaControler.NOT_PAID);

        assertThat(multa.getValorpago()).isEqualTo((float) 0.0);

        /***************************************************
         * pay method
         ********************************************* */

        this.fine.pay(emprestimo.getEmprestimoPK(), now);
        float expectedAmount = this.configControler.DAILY_RATE_FINE * this.fine.getDelayDays(now, deadline);
        multa = this.multaController.getFine(emprestimo.getEmprestimoPK());

        assertThat(multa.getValorpago()).isEqualTo(expectedAmount);
        assertThat(multa.getEstadoMulta().getIdestadomulta()).isEqualTo(this.estadoMultaControler.PAID);

        /***************************************************
         * revoke method
         ********************************************* */
        this.fine.revoke(emprestimo.getEmprestimoPK());
        multa = this.multaController.getFine(emprestimo.getEmprestimoPK());

        assertThat(multa.getEstadoMulta().getIdestadomulta()).isEqualTo(this.estadoMultaControler.REVOKED);
    }

    @Test
    @Transactional
    public void getAmoutToPayTest() throws Exception
    {
        Calendar deadline = emprestimo.getDatadevolucao();
        Calendar now = Calendar.getInstance();
        now.setTime(emprestimo.getDatadevolucao().getTime());

        now.set(Calendar.DATE, now.get(Calendar.DATE) + 7);

        float expectedAmount = this.configControler.DAILY_RATE_FINE * this.fine.getDelayDays(now, deadline);
        float actualAmount = this.fine.getAmoutToPay(emprestimo.getEmprestimoPK(), now);

        assertThat(actualAmount).isEqualTo(expectedAmount);

        now.setTime(emprestimo.getDatadevolucao().getTime());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + 1);

        expectedAmount = this.configControler.DAILY_RATE_FINE;
        actualAmount = this.fine.getAmoutToPay(emprestimo.getEmprestimoPK(), now);

        assertThat(actualAmount).isEqualTo(expectedAmount);
    }

//
    @Test
    @Transactional
    public void updateDelayDaysTest() {
        List<Multa> multas = multaController.getMultas();

        int diasActuais = multas.get(7).getDiasatraso();
        Calendar now = Calendar.getInstance();

        System.out.println(now.getTime());
        now.add(Calendar.DAY_OF_MONTH, 4);

        fine.updateDelayDays(multas);
        int diasEsperados = multas.get(7).getDiasatraso();
        System.out.println(diasEsperados);

//        System.out.println(now.getTime());
//        assertThat(diasEsperados).isGreaterThan(diasActuais);


    }
//
//    @Test
//    @Transactional
//    public void totalDinheiroTest() {
//        List<Multa> multaList;
//        multaList = multaController.getMultas();
//
//        double expected = fine.totalDinheiro(multaList);
//        double actual = 0.0;
//
//        for(Multa m:multaList){
//            actual += fine.getAmountToPay(m.getMultaPK());
//        }
//
//        assertThat(actual).isEqualTo(expected);
//    }
}