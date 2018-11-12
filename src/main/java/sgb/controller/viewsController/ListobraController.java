package sgb.controller.viewsController;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class ListobraController extends SelectorComposer<Component> {
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;

    private ListModelList<Obra> obraListModel;

    @Wire
    private Window listObra;

    @Wire
    private Listbox obraListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obraListModel = getObraListModel();
        obraListBox.setModel(obraListModel);
    }

    public ListModelList<Obra> getObraListModel() {
        List<Obra> listaobra = crudService.getAll(Obra.class);
        return new ListModelList<Obra>(listaobra);
    }

    @Listen("onEliminarObra = #obraListBox")
    public void doEliminar(ForwardEvent event)
    {
        Clients.showNotification("Eliminar");

    }

    @Listen("onDetalheObra = #obraListBox")
    public void doDetalhe(ForwardEvent event)
    {
            Clients.showNotification("Detalhes Obra");
    }

    @Listen("onEditarObra = #obraListBox")
    public void doEditar(ForwardEvent event)
    {
        Clients.showNotification("Editar Obra");
    }

    @Listen("onRequisitarObra = #obraListBox")
    public void doRequisitar(ForwardEvent event)
    {
        emprestimo = new Emprestimo();
        emprestimoPK = new EmprestimoPK();
        estadoDevolucao = crudService.get(EstadoDevolucao.class, 2);
        estadoPedido= crudService.get(EstadoPedido.class, 1);

        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();

        Obra obra = (Obra) litem.getValue();
        user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        emprestimoPK.setObra(obra);
        emprestimoPK.setUser(user);

        emprestimo.setEstadoDevolucao(estadoDevolucao);
        emprestimo.setEstadoPedido(estadoPedido);
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setComentario("perdeu o livro");
        emprestimo.setDataaprovacao(null);
        emprestimo.setDataentrada(null);
        emprestimo.setQuantidade(null);
        crudService.Save(emprestimo);




        Clients.showNotification("Ola: "+obra.getTitulo());

    }
}