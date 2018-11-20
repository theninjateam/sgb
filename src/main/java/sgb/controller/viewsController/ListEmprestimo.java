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
import sgb.domain.Emprestimo;
import sgb.domain.EstadoPedido;
import sgb.domain.Users;
import sgb.service.CRUDService;

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

    }

    public ListModelList<Emprestimo> getEmprestimoListModel() {
        List<Emprestimo> lista = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.estadoPedido.idestadopedido=3 and e.estadoDevolucao.idestadodevolucao =2 " ,null);
        return new ListModelList<Emprestimo>(lista);
    }


    @Listen("onNotificarUtente = #emprestimoListBox")
    public void doEliminar(ForwardEvent event)
    {
         Clients.showNotification(" Enviar email a notificar utente da devolucao do livo",null,null,null,5000);
    }
    @Listen("onDevolver = #emprestimoListBox")
    public void doDetalhe(ForwardEvent event)
    {
        Clients.showNotification("Devolver Obra",null,null,null,5000);
    }

    @Listen("onDetalheEmprestimo = #emprestimoListBox")
    public void doEditar(ForwardEvent event)
    {
        Clients.showNotification("Detalhes do Emprestimo",null,null,null,5000);
    }


}
