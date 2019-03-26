package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.domain.*;
import sgb.request.Request;
import sgb.service.CRUDService;

import java.util.Set;

//import sgb.controller.domainController.EmprestimoControllerSingleton;

public class ListMulta extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Multa> multaListModel;
    private Session session;
    private Request request = (Request) SpringUtil.getBean("request");
    private EstadoPedidoControler ePController = (EstadoPedidoControler) SpringUtil.getBean("estadoPedidoControler");
    private EmprestimoController eController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler eDController = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");

    private Boolean isNormalUser = true;



    @Wire
    private Listbox multaListBox;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRole().equals("ADMIN"))
                isNormalUser = false;
        }
        if (isNormalUser) {
            ComposeUserNormal();
        }
        else {
            ComposeUserAdmin();
        }

    }

    public void ComposeUserAdmin(){
        multaListModel = new ListModelList<Multa>(crudService.getAll(Multa.class));
        multaListBox.setModel(multaListModel);
    }

    public void ComposeUserNormal() {
        multaListModel = new ListModelList<Multa>(crudService.getAll(Multa.class));
        multaListBox.setModel(multaListModel);
    }

    @Listen("onDetalhesMulta = #multaListBox")
    public void doRenovar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Button btn = (Button) event.getOrigin().getTarget();
            Listitem litem = (Listitem) btn.getParent().getParent().getParent();
            Multa multa = (Multa) litem.getValue();
            Boolean isForDetails = false;

            session.setAttribute("ForDetais", isForDetails);
            session.setAttribute("multa", multa);
            Window window =(Window) Executions.createComponents("/views/multamodal.zul", null, null);
            window.setClosable(true);
            window.doModal();
        }
    }

}
