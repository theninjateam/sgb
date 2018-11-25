package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.domain.Emprestimo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.*;

import java.util.Calendar;

public class ListPedido extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> pedidoListModel;
    private ListModel<EstadoPedido> estadopedidoModel;
    private Boolean isNormalUser = true;
    private EmprestimoControllerSingleton emprestimoControllerSingleton = EmprestimoControllerSingleton.getInstance(crudService);

    @Wire
    private Listbox pedidoListBox;


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

    public void ComposeUserAdmin(){
        pedidoListModel = new ListModelList<Emprestimo>(emprestimoControllerSingleton.getRequisicoes(1));
        pedidoListBox.setModel(pedidoListModel);
    }

    public void ComposeUserNormal() {
        pedidoListModel = new ListModelList<Emprestimo>(emprestimoControllerSingleton.getRequisicoes(this.user, 1));
        pedidoListBox.setModel(pedidoListModel);
    }

    @Listen("onEliminarEmprestimo = #pedidoListBox")
    public void doEliminar(ForwardEvent event)
    {
        if (isNormalUser) {
            Button btn = (Button) event.getOrigin().getTarget();
            Listitem litem = (Listitem) btn.getParent().getParent().getParent();
            Emprestimo emp = (Emprestimo) litem.getValue();
            Messagebox.show("Tem certeza que deseja eliminar esse pedido ?", "delete pedido",
                    Messagebox.YES + Messagebox.NO, Messagebox.QUESTION,
                    new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            if (Messagebox.ON_YES.equals(event.getName())) {
                                pedidoListModel.remove(emp);
                                crudService.delete(emp);
                                Clients.showNotification("Pedido eliminado com sucesso", null, null, null, 5000);
                            }
                        }
                    });
        }else {
            Clients.showNotification("Precisa ser Utente para executar essa acao ", null, null, null, 5000);
        }

    }

    @Listen("onReprovarEmprestimo = #pedidoListBox")
    public void doReprovar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Button btn = (Button) event.getOrigin().getTarget();
            Listitem litem = (Listitem) btn.getParent().getParent().getParent();
            Emprestimo emp = (Emprestimo) litem.getValue();
            EstadoPedido estadoPedido = crudService.get(EstadoPedido.class, 2);
            emp.setEstadoPedido(estadoPedido);
            emp.setDataaprovacao(Calendar.getInstance());
            pedidoListModel.remove(emp);
            crudService.update(emp);
            Clients.showNotification("Pedido reprovado com sucesso ", null, null, null, 5000);
        }
    }

    @Listen("onAprovarEmprestimo = #pedidoListBox")
    public void doAprovar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Button btn = (Button) event.getOrigin().getTarget();
            Listitem litem = (Listitem) btn.getParent().getParent().getParent();
            Emprestimo emp = (Emprestimo) litem.getValue();
            EstadoPedido estadoPedido = crudService.get(EstadoPedido.class, 3);
            emp.setEstadoPedido(estadoPedido);
            emp.setDataaprovacao(Calendar.getInstance());
            emp.setDatadevolucao(Calendar.getInstance());
            pedidoListModel.remove(emp);
            crudService.update(emp);
            Clients.showNotification("Pedido aprovado com sucesso ", null, null, null, 5000);
        }
    }
}