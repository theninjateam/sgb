package sgb.controller.viewsController;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.List;

public class ListobraController extends SelectorComposer<Component> {
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;
    private Session session;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;

    private ListModelList<Obra> obraListModel;

    private ListModelList<Requisicao> obraRequisitarListModel = new ListModelList<Requisicao>();

    @Wire
    private Window listObra;

    @Wire
    private Listbox obraListBox;

    @Wire
    private Listbox obraRequisitarListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        obraListModel = getObraListModel();
        obraListBox.setModel(obraListModel);
        obraRequisitarListBox.setModel(obraRequisitarListModel);
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
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();

        Obra obra = (Obra) litem.getValue();
        insertOnObraRequisitarListModel(obra);


//        emprestimo = new Emprestimo();
//        emprestimoPK = new EmprestimoPK();
//        estadoDevolucao = crudService.get(EstadoDevolucao.class, 2);
//        estadoPedido= crudService.get(EstadoPedido.class, 1);

//        user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        emprestimoPK.setObra(obra);
//        emprestimoPK.setUser(user);
//
//        emprestimo.setEstadoDevolucao(estadoDevolucao);
//        emprestimo.setEstadoPedido(estadoPedido);
//        emprestimo.setEmprestimoPK(emprestimoPK);
//        emprestimo.setComentario("perdeu o livro");
//        emprestimo.setDataaprovacao(null);
//        emprestimo.setDataentrada(null);
//        emprestimo.setQuantidade(null);
//        crudService.Save(emprestimo);
//
//
//        Clients.showNotification("Ola: "+obra.getTitulo());

    }


    @Listen("onEliminarobraRequisitar = #obraRequisitarListBox")
    public void doEliminarobraRequisitar(ForwardEvent event)
    {
        Clients.showNotification("Hi There");

    }

    public void insertOnObraRequisitarListModel(Obra obra)
    {
        boolean obraExists = false;

        for(int i = 0; i <  obraRequisitarListModel.size(); i++)
        {
            if(obra.getCota() == obraRequisitarListModel.get(i).getObra().getCota())
            {
                int qtd =  obraRequisitarListModel.getElementAt(i).getQuantidade() + 1;
                Requisicao requisicao = obraRequisitarListModel.getElementAt(i);

                requisicao.setQuantidade(qtd);
                obraRequisitarListModel.set(i, requisicao);
                obraExists = true;
                break;
            }
        }

        if (!obraExists)
        {
            Requisicao requisicao = new Requisicao();

            requisicao.setObra(obra);
            requisicao.setQuantidade(1);

            obraRequisitarListModel.add(requisicao);
        }


        for(int i = 0; i <  obraListModel.size(); i++)
        {
            if(obra.getCota() == obraListModel.get(i).getCota())
            {
                int qtd =  obraListModel.getElementAt(i).getQuantidade() - 1;
                Obra ob  = obraListModel.getElementAt(i);

                ob.setQuantidade(qtd);
                obraListModel.set(i, ob);

                if(qtd == 0)
                    obraListModel.remove(i);

                break;
            }
        }
    }
}