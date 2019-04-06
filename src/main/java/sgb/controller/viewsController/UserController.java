package sgb.controller.viewsController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sgb.domain.*;
import sgb.service.CRUDService;

public class UserController extends SelectorComposer<Component> {

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private sgb.controller.domainController.UserController userController = (sgb.controller.domainController.UserController) SpringUtil.getBean("userController");



    @Wire
    private Textbox senhaActual;

    @Wire
    private Textbox novaSenha;

    @Wire
    private Textbox confirmarSenha;

    @Wire
    private Window alterarSenha;

    @Wire
    private Label currentUser;
    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);

        currentUser.setValue(user.getName() +" "+ user.getLastName());

    }

    public Boolean isPasswordEqual(String actualPassword,String newPass,String confNewPass)
    {
        return (user.getPassword().equals(userController.encrypt(actualPassword)) && newPass.equals(confNewPass));

    }


    @Listen("onClick = #savebtn")
    public void updatePassword()
    {


        if (isPasswordEqual(senhaActual.getValue(),novaSenha.getValue(),confirmarSenha.getValue()))
        {


            userController.changeUserPassword(user,userController.encrypt(novaSenha.getValue()));
            Clients.showNotification("Feito");
            closeModal();
            return;

        }

        Clients.showNotification("Dados incorrectos");





    }

    @Listen("onClick = #cancelbtn")
    public void clearPassword()
    {
        senhaActual.setValue("");
        novaSenha.setValue("");
        confirmarSenha.setValue("");


    }
    @Listen("onClick = #fechar")
    public void closeModal(){

        alterarSenha.detach();
    }


}
