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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import java.util.List;

public class ListEmprestimo extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> emprestimoListModel;
    private ListModel<EstadoPedido> estadopedidoModel;
    @Wire
    private Listbox emprestimoListBox;

    @Wire
    private Listbox estadopedidoListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        emprestimoListModel = new ListModelList<Emprestimo>(getEmprestimoListModel());
        emprestimoListBox.setModel(emprestimoListModel);

        estadopedidoModel = getEstadopedidoListModel();
//        estadopedidoListBox.setModel(estadopedidoModel);
    }

    public ListModelList<Emprestimo> getEmprestimoListModel() {
        List<Emprestimo> lista = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido=1 and e.emprestimoPK.user.id = " +
                user.getId(),null);
        return new ListModelList<Emprestimo>(lista);
    }
    public ListModelList<EstadoPedido> getEstadopedidoListModel () {
        List<EstadoPedido> lista = crudService.getAll(EstadoPedido.class);
        return new ListModelList<EstadoPedido>(lista);
    }

    @Listen("onEliminarEmprestimo = #emprestimoListBox")
    public void doEliminar(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent();
        Emprestimo emp = (Emprestimo) litem.getValue();
        Messagebox.show("Tem certeza que deseja eliminar esse pedido ?", null,
                Messagebox.YES + Messagebox.NO, Messagebox.QUESTION,
                new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.ON_YES.equals(event.getName())) {
                            emprestimoListModel.remove(emp);
                            crudService.delete(emp);
                            Clients.showNotification("Pedido eliminado com sucesso");                        }
                    }
                });


    }
    @Listen("onDetalheEmprestimo = #emprestimoListBox")
    public void doDetalhe(ForwardEvent event)
    {
        Clients.showNotification("Detalhes Obra");
    }

    @Listen("onEditarEmprestimo = #emprestimoListBox")
    public void doEditar(ForwardEvent event)
    {
        Clients.showNotification("Editar Obra");
    }


}
