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
import sgb.controller.domainController.*;
import sgb.domain.Multa;
import sgb.domain.Role;
import sgb.domain.Users;
import sgb.request.Request;
import sgb.service.CRUDService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;

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
    private MultaController mController = (MultaController) SpringUtil.getBean("multaController");
    private EstadoMultaControler eMController = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private RoleController rController = (RoleController) SpringUtil.getBean("roleController");

    private Boolean isNormalUser = true;
    private StringBuilder query;
    private HashMap<String, Object> parameters;


    @Wire
    private Listbox multaListBox;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRoleId() == rController.ADMIN)
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
        multaListModel = new ListModelList<Multa>(mController.getFine(eMController.NOT_PAID));
        multaListBox.setModel(multaListModel);
    }

    public void ComposeUserNormal() {
        multaListModel = new ListModelList<Multa>(mController.getFine(user, eMController.NOT_PAID));
        multaListBox.setModel(multaListModel);
    }

    @Listen("onDetalhesMulta = #multaListBox")
    public void doDelahes(ForwardEvent event)
    {

            Multa multa = (Multa) event.getData();
            Boolean isForDetails = false;
            session.setAttribute("ForDetais", isForDetails);
            session.setAttribute("Multa", multa);
            session.setAttribute("MultaListModel", multaListModel);
            Window window =(Window) Executions.createComponents("/views/multamodal.zul", null, null);
            window.setClosable(true);
            window.doModal();

    }

    public String dataConvert (Calendar dataa) {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("'('HH:mm:s')'");
        DateFormat dateFormatter = new SimpleDateFormat();
        Locale MOZAMBIQUE = new Locale("pt","MZ");
        StringBuilder builder = new StringBuilder();


        dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, MOZAMBIQUE);

        builder.append(dateFormatter.format(dataa.getTime()));
        builder.append("\n");
        builder.append(timeFormatter.format(dataa.getTime()));

        String dataEntrada = builder.toString();

        return dataEntrada;
    }



}
