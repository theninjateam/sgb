package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.domain.Emprestimo;
import sgb.domain.Obra;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Calendar;
import java.util.List;

public class ListRenovacao extends SelectorComposer<Component> {
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> renovacaoListModel;
    private Boolean isNormalUser = true;
    private EstadoRenovacao estadoRenovacao;

    @Wire
    private Listbox renovacaoListBox;

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
        renovacaoListModel = new ListModelList<Emprestimo>(getAllEmprestimoListModel());
        renovacaoListBox.setModel(renovacaoListModel);
    }
    public void ComposeUserNormal() {
        renovacaoListModel = new ListModelList<Emprestimo>(getUserEmprestimoListModel());
        renovacaoListBox.setModel(renovacaoListModel);
    }
    public ListModelList<Emprestimo> getUserEmprestimoListModel() {
        List<Emprestimo> lista = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.estadoRenovacao.idestadorenovacao=2 and e.estadoPedido.idestadopedido=3 and e.emprestimoPK.utente.id = " +
                user.getId(),null);
        return new ListModelList<Emprestimo>(lista);
    }
    public ListModelList<Emprestimo> getAllEmprestimoListModel () {
        List<Emprestimo> lista = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.estadoRenovacao.idestadorenovacao=2 and e.estadoPedido.idestadopedido=3",null);
        return new ListModelList<Emprestimo>(lista);
    }

    @Listen("onEliminarRenovacao = #renovacaoListBox")
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
                                estadoRenovacao = crudService.get(EstadoRenovacao.class, 1);
                                emp.setEstadoRenovacao(estadoRenovacao);
                                renovacaoListModel.remove(emp);
                                crudService.update(emp);
                                Clients.showNotification("Pedido eliminado com sucesso", null, null, null, 5000);
                            }
                        }
                    });
        }else {
            Clients.showNotification("Precisa ser Utente para executar essa acao ", null, null, null, 5000);
        }

    }
    @Listen("onReprovarRenovacao = #renovacaoListBox")
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
            estadoRenovacao = crudService.get(EstadoRenovacao.class, 4);
            emp.setEstadoRenovacao(estadoRenovacao);
            renovacaoListModel.remove(emp);
            crudService.update(emp);
            Clients.showNotification("Pedido reprovado com sucesso ", null, null, null, 5000);
        }

    }

    @Listen("onAprovarRenovacao = #renovacaoListBox")
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
            estadoRenovacao = crudService.get(EstadoRenovacao.class, 3);
            emp.setEstadoRenovacao(estadoRenovacao);
            renovacaoListModel.remove(emp);
            crudService.update(emp);
            Clients.showNotification("Pedido aprovado com sucesso ", null, null, null, 5000);
        }

    }


}
