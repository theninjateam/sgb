package sgb.login;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.domain.Users;

public class Login {

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private sgb.controller.domainController.UserController userController = (sgb.controller.domainController.UserController) SpringUtil.getBean("userController");


    public boolean isBlocked(){

        Users user1 = userController.getUser(this.user.getId());

        return user1.getActive()==0;



    }

}
