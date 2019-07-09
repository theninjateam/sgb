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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.Set;

public class AlterarSenhaController extends SelectorComposer<Component> {

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

    @Wire
    private Label currentUserRole;

    @Wire
    private Label escolha;

    @Wire
    private Radiogroup opcao;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);

        currentUser.setValue(user.getName() +" "+ user.getLastName());
        currentUserRole.setValue("("+getRole()+")");

    }

    public Boolean isPasswordEqual(String actualPassword,String newPass,String confNewPass)
    {
        return (user.getPassword().equals(userController.encrypt(actualPassword)) && newPass.equals(confNewPass));

    }

    public String getRole () {
        String string= null;

        Set<Role> userrole =user.getRoles();

        for(Role rol : userrole) {
            string = rol.getRole();
        }

        return string;
    }



    @Listen("onClick = #savebtn")
    public void updatePassword()
    {


        if (isPasswordEqual(senhaActual.getValue(),novaSenha.getValue(),confirmarSenha.getValue()))
        {
            if(escolha.getValue().equals("Sim")) {
                userController.changeUserPassword(user,userController.encrypt(novaSenha.getValue()));
                Clients.showNotification("Senha alterada com sucesso",null,null,null,5000);
                closeModal();
                return;
            } else {
                Clients.showNotification("Seleciona Sim",null,null,null,5000);

            }


        } else {
            Clients.showNotification("Dados incorrectos","error",null,null,5000);

        }



    }

    @Listen("onClick = #cancelbtn")
    public void clearPassword()
    {
        senhaActual.setValue("");
        novaSenha.setValue("");
        confirmarSenha.setValue("");
        escolha.setValue("");
        opcao.setSelectedItem(null);

    }
    @Listen("onClick = #fechar")
    public void closeModal(){

        alterarSenha.detach();
    }


}
