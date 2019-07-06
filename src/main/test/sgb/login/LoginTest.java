package sgb.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.UserController;
import sgb.domain.Users;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

/**
 * @author Meneses
 */



public class LoginTest {
    @Autowired
    private ApplicationContext context;

    private Login login;
    private Users user;
    private sgb.controller.domainController.UserController userController ;


    @Before
    @Transactional
    public void before() throws Exception {
        this.userController = (UserController) context.getBean("userController");

        this.login = (Login) context.getBean("login") ;
    }

    @Test
    @Transactional
    public void blockTest() throws Exception {
        boolean isBlocked;
        this.user = new Users();

        login.unblockUser(this.user);

        isBlocked = login.isBlocked(this.user);
        assertThat(isBlocked).isFalse();

        login.blockUser(user);
        isBlocked = login.isBlocked(this.user);
        assertThat(isBlocked).isTrue();

    }

    @Test
    @Transactional
    public void getSateTest() throws Exception {

        int state = login.getState("Blocked");

        assertThat(state).isEqualTo(0);

        state = login.getState("whatever");
        assertThat(state).isEqualTo(1);

    }


}