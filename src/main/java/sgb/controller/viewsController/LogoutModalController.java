package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;
import sgb.domain.Users;

public class LogoutModalController extends SelectorComposer<Component> {
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    @Wire
    Window logoutModal;

    @Wire
    private Label currentUser;
    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        try{
            super.doAfterCompose(comp);

            currentUser.setValue(user.getName() +" "+ user.getLastName());
        }catch (Exception e){}


    }

    @Listen("onClick = #preLogout")
    public void showModal(Event e) {
        //create a window programmatically and use it as a modal dialog.
        Window window = (Window) Executions.createComponents(
                "/views/logoutmodal.zul", null, null);
        window.doModal();
    }


    @Listen("onClick = #alterarSenha")
    public void redirectToChangePassword(){

        closeModal();
        Window window = (Window)Executions.createComponents(
                "/views/alterarSenha.zul", null, null);
        window.doModal();

        //Executions.sendRedirect("/views/alterarSenha.zul");

    }

    @Listen("onClick = #fechar")
    public void closeModal(){

        logoutModal.detach();

    }
}
