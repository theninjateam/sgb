package sgb.email;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.MultaController;
import sgb.domain.Multa;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Meneses
 */



public class SendEmailControllerTest
{
    @Autowired
    private ApplicationContext context;
    private CRUDService crudService;
    private List<Multa>  multas;


    private MultaController multaController;
    private SendEmail sendEmail;
    private sgb.email.SendEmailController sendEmailController;



    @Before
    @Transactional
    public void before() throws Exception {
        System.out.println("Setting it up!");

        this.multaController = (MultaController) context.getBean("multaController");
        this.sendEmail = (SendEmail) context.getBean("sendEmail");
        this.sendEmailController = (SendEmailController) context.getBean("sendEmailController");


        multas = new ArrayList<Multa>();

    }

    @Test
    @Transactional
    public void processTest() throws Exception
    {

        boolean notify;


        notify = this.sendEmailController.process(null);
        assertThat(notify).isFalse();


        notify = this.sendEmailController.process(multas);
        assertThat(notify).isTrue();

    }
}