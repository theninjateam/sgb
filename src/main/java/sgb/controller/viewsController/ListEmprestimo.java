package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
//import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.controller.domainController.*;
import sgb.deadline.BorrowedBooksDeadline;
import sgb.deadline.Deadline;
import sgb.domain.*;
import sgb.fine.Fine;
import sgb.request.Queue;
import sgb.request.Request;
import sgb.service.CRUDService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import javax.swing.plaf.PanelUI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class ListEmprestimo extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> emprestimoListModel;
    private Session session;
    private Multa multa;
    private Request request = (Request) SpringUtil.getBean("request");
    private EstadoPedidoControler ePController = (EstadoPedidoControler) SpringUtil.getBean("estadoPedidoControler");
    private EmprestimoController eController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler eDController = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private EstadoRenovacaoControler eRController = (EstadoRenovacaoControler) SpringUtil.getBean("estadoRenovacaoControler");
    private RoleController rController = (RoleController) SpringUtil.getBean("roleController");
    private ConfigControler configControler =(ConfigControler) SpringUtil.getBean("configControler");
    private EstadoMultaControler eMController = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private Fine fine = (Fine) SpringUtil.getBean("fine");
    private Deadline deadline = (Deadline) SpringUtil.getBean("deadline");
    private MultaController mController = (MultaController) SpringUtil.getBean("multaController");
    private BorrowedBooksDeadline bBDeadline = (BorrowedBooksDeadline) SpringUtil.getBean("borrowedBooksDeadline");
    private Queue queue = (Queue) SpringUtil.getBean("queue");


    private Boolean isNormalUser = true;
    private EstadoRenovacao estadoRenovacao;


    @Wire
    private Listbox emprestimoListBox;

    @Wire
    private Listbox estadopedidoListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        isNormalUser = isNormalUser();

        if (isNormalUser) {
            ComposeUserNormal();
        }
        else {
            ComposeUserAdmin();
        }

    }

    public void ComposeUserAdmin(){
        emprestimoListModel = new ListModelList<Emprestimo>(eController.getRequest(ePController.ACCEPTED,eDController.NOT_RETURNED));
        emprestimoListBox.setModel(emprestimoListModel);
    }

    public void ComposeUserNormal() {
        emprestimoListModel = new ListModelList<Emprestimo>(eController.getRequest(this.user, ePController.ACCEPTED,eDController.NOT_RETURNED));
        emprestimoListBox.setModel(emprestimoListModel);
    }


    @Listen("onNotificarUtente = #emprestimoListBox")
    public void doNotificarUtente(ForwardEvent event)
    {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent().getParent();
        Emprestimo emp = (Emprestimo) litem.getValue();

        session.setAttribute("EmprestimoMultado", emp);
        Boolean isForDetails = true;
        session.setAttribute("ForDetais", isForDetails);
        Window window =(Window) Executions.createComponents("/views/multamodal.zul", null, null);
        window.setClosable(true);
        window.doModal();

    }
    @Listen("onDevolver = #emprestimoListBox")
    public void doDevolver(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Emprestimo emp = (Emprestimo) event.getData();

            if (fine.wasFinedBefore(emp.getEmprestimoPK())) {

                multa = mController.getFine(emp.getEmprestimoPK());

//                fine.fine(emp,Calendar.getInstance());

                session.setAttribute("Multa", multa);
                Boolean isForDetails = false;
                session.setAttribute("ForDetais", isForDetails);

                Window window = (Window) Executions.createComponents("/views/multamodal.zul", null, null);
                window.setClosable(true);
                window.doModal();
            } else {
                EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);
                emp.setEstadoDevolucao(estadoDevolucao);
                emp.setComentario("");

                emprestimoListModel.remove(emp);
                crudService.update(emp);

                Clients.showNotification("Obra devolvida a tempo", null, null, null, 5000);

            }
    }

    }

    @Listen("onDetalheEmprestimo = #emprestimoListBox")
    public void doDetalhes(ForwardEvent event)
    {
        Emprestimo emp = (Emprestimo) event.getData();

        session.setAttribute("EmprestimoMultado", emp);
        Boolean isForDetails = true;
        session.setAttribute("ForDetais", isForDetails);
        Window window =(Window) Executions.createComponents("/views/multamodal.zul", null, null);
        window.setClosable(true);
        window.doModal();

    }

    @Listen("onRenovarEmprestimo = #emprestimoListBox")
    public void doRenovar(ForwardEvent event)
    {
        Emprestimo emp = (Emprestimo) event.getData();
        if(isNormalUser) {




            if(CanDoRenewal(emp)) {
                Clients.showNotification("Renovacao", null, null, null, 5000);

                 estadoRenovacao = crudService.get(EstadoRenovacao.class,eRController.RENOVADO);
                 emp.setEstadoRenovacao(estadoRenovacao);
                 emprestimoListModel.remove(emp);
                 emp.setDatadevolucao(bBDeadline.getDeadline(emp));
                 emprestimoListModel.add(emp);
                 crudService.update(emp);

            }else {
                if(!queue.getOnWaitingQueueRequest(emp.getEmprestimoPK().getObra()).isEmpty())
                    Clients.showNotification("Existe um usuario na Lista de espera dessa obra, Por favor devolva a obra", null, null, null, 5000);
                else
                    Clients.showNotification("Renovacao so pode ser feita no dia de devolucao ou um dia antes", null, null, null, 5000);
            }

        } else {
            Clients.showNotification("Precisa ser Utente para executar essa acao ", null, null, null, 5000);
        }
    }

    public Boolean CanDoRenewal (Emprestimo emp )
    {
        Calendar today = Calendar.getInstance();

        if(!iSRenovated(emp) && queue.getOnWaitingQueueRequest(emp.getEmprestimoPK().getObra()).isEmpty()) {

//            if((emp.getDatadevolucao().get(Calendar.ERA) == today.get(Calendar.ERA)) &&
//                    (emp.getDatadevolucao().get(Calendar.YEAR) == today.get(Calendar.YEAR)) &&
//                    ((emp.getDatadevolucao().get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) ||
//                            ((emp.getDatadevolucao().get(Calendar.DAY_OF_YEAR) - 1) == today.get(Calendar.DAY_OF_YEAR)))) {
//                return true;
//            } else {
//                return true;
//            }
            return true;

        }else
            return false;

    }

    public boolean isNormalUser () {
        Boolean a = true;

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRoleId() == rController.ADMIN)
                a = false;
        }
        return a;
    }


    @Listen("onShowOperacoes = #emprestimoListBox")
    public void doShowOperacoes(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Div div = (Div) btn.getNextSibling();

        if (div.isVisible())
        {
            btn.setLabel("Outras operações");
            div.setVisible(false);
        }
        else
        {
            btn.setLabel("Ocultar operações");
            div.setVisible(true);
        }
    }

    public Boolean Expired(Emprestimo emp) {

        Calendar DataLimite = emp.getDatadevolucao();

        if(deadline.exceededDeadline(DataLimite,Calendar.getInstance())){
            return true;
        }

        return false;
    }
    public Boolean iSRenovated (Emprestimo emp) {

        if(emp.getEstadoRenovacao().getIdestadorenovacao() == eRController.RENOVADO)
            return true;
        else
            return false;
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
