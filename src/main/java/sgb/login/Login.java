package sgb.login;

import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.UserController;
import sgb.domain.Users;

public class Login {

    private sgb.controller.domainController.UserController userController;

//    public Login() {
//
//        this.userController = (sgb.controller.domainController.UserController) SpringUtil.getBean("userController");
//
//    }


    public Login(UserController userController) {
        this.userController = userController;
    }

    public boolean isBlocked(Users user){

        return user.getActive()==0;

    }

    public int getState(String state){

        if (state.equals("Bloqueados"))
            return 0;

        return 1;

    }

    public void blockUser(Users user){

        user.setActive(0);
        this.userController.updateUser(user);
    }

    public void unblockUser(Users user){

        user.setActive(1);
        this.userController.updateUser(user);
    }

}
