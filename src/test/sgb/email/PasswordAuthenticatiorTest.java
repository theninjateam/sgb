package sgb.email;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.service.CRUDService;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Meneses
 */



public class PasswordAuthenticatiorTest
{
    @Autowired
    private ApplicationContext context;
    private CRUDService crudService;
    private sgb.email.PasswordAuthenticatior passwordAuthenticatior;


    @Before
    @Transactional
    public void before() throws Exception {
        System.out.println("Setting it up!");

        this.passwordAuthenticatior = (PasswordAuthenticatior)context.getBean("passwordAuthenticator");


    }

    @Test
    @Transactional
    public void getPasswordAuthenticationTest() throws Exception
    {

        assertNotNull( passwordAuthenticatior.getPasswordAuthentication());



    }
}