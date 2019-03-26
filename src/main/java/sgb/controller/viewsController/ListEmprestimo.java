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
import sgb.domain.*;
import sgb.request.Request;
import sgb.service.CRUDService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import javax.swing.plaf.PanelUI;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
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
    private ConfigControler configControler =(ConfigControler) SpringUtil.getBean("configControler");
    private EstadoMultaControler eMController = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");


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
        emprestimoListModel = new ListModelList<Emprestimo>(eController.getRequest(ePController.ACCEPTED));
        emprestimoListBox.setModel(emprestimoListModel);
    }

    public void ComposeUserNormal() {
        emprestimoListModel = new ListModelList<Emprestimo>(eController.getRequest(this.user, ePController.ACCEPTED));
        emprestimoListBox.setModel(emprestimoListModel);
    }


    @Listen("onNotificarUtente = #emprestimoListBox")
    public void doNotificarUtente(ForwardEvent event)
    {
         Clients.showNotification(" Enviar email a notificar utente da devolucao do livo",null,null,null,5000);
    }
    @Listen("onDevolver = #emprestimoListBox")
    public void doDevolver(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Button btn = (Button) event.getOrigin().getTarget();
            Listitem litem = (Listitem) btn.getParent().getParent().getParent();
            Emprestimo emp = (Emprestimo) litem.getValue();



            if (emp.getEstadoDevolucao().getDescricao().equals("NOT_RETURNED")) {

                multa = crudService.get(Multa.class,emp.getEmprestimoPK());

                session.setAttribute("Multa", multa);
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

    @Listen("onRenovarEmprestimo = #emprestimoListBox")
    public void doRenovar(ForwardEvent event)
    {
        if(isNormalUser) {
            /*
            * Metodo a ser descutido
            * Ideia 1 : O usuario submete o pedido de renovacao e o sistema aprova;
            * Ideia 2 : O usuario vai ter com o bibliotecario e esse acede o pedido e renova;
            * Necessidade de um Metodo que verifica se existe alguem a espera daquele livro para desabilitar o
            * Botao
            */
            Button btn = (Button) event.getOrigin().getTarget();
            Listitem litem = (Listitem) btn.getParent().getParent().getParent();
            Emprestimo emp = (Emprestimo) litem.getValue();
            estadoRenovacao = crudService.get(EstadoRenovacao.class, 2);
            emp.setEstadoRenovacao(estadoRenovacao);
            crudService.update(emp);
            Clients.showNotification("Renovacao do Emprestimo", null, null, null, 5000);
        } else {
            Clients.showNotification("Precisa ser Utente para executar essa acao ", null, null, null, 5000);
        }
    }
    public boolean isNormalUser () {
        Boolean a = true;

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRole().equals("ADMIN"))
                a = false;
        }
        return a;
    }


}
