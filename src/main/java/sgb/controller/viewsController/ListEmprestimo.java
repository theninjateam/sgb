package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Set;

public class ListEmprestimo extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> emprestimoListModel;
    private ListModel<EstadoPedido> estadopedidoModel;
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

    public void ComposeUserAdmin () {
        emprestimoListModel = new ListModelList<Emprestimo>(getAllEmprestimoListModel());
        emprestimoListBox.setModel(emprestimoListModel);

    }

    public void ComposeUserNormal () {
        emprestimoListModel = new ListModelList<Emprestimo>(getUserEmprestimoListModel());
        emprestimoListBox.setModel(emprestimoListModel);
    }

    public ListModelList<Emprestimo> getAllEmprestimoListModel() {
        List<Emprestimo> lista = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido=3 " ,null);
        return new ListModelList<Emprestimo>(lista);
    }

    public ListModelList<Emprestimo> getUserEmprestimoListModel() {
        List<Emprestimo> lista = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido=3 and e.emprestimoPK.user.id = " +
                                user.getId()  ,null);
        return new ListModelList<Emprestimo>(lista);
    }

    @Listen("onNotificarUtente = #emprestimoListBox")
    public void doNotificarUtente(ForwardEvent event)
    {
         Clients.showNotification(" Enviar email a notificar utente da devolucao do livo",null,null,null,5000);
    }
    @Listen("onDevolver = #emprestimoListBox")
    public void doDevolver(ForwardEvent event)
    {
        Clients.showNotification("Devolver ObraConcurrenceControl",null,null,null,5000);
    }

    @Listen("onDetalheEmprestimo = #emprestimoListBox")
    public void doDetalhes(ForwardEvent event)
    {
        Clients.showNotification("Detalhes do Emprestimo",null,null,null,5000);
    }

    @Listen("onRenovarEmprestimo = #emprestimoListBox")
    public void doRenovar(ForwardEvent event)
    {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent().getParent();
        Emprestimo emp = (Emprestimo) litem.getValue();
        estadoRenovacao = crudService.get(EstadoRenovacao.class,2);
        emp.setEstadoRenovacao(estadoRenovacao);
        crudService.update(emp);
        Clients.showNotification("Renovacao do Emprestimo",null,null,null,5000);
    }



}
