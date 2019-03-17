
package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
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

import java.util.*;

import java.util.Calendar;
import java.util.List;

public class viewRequestModalController extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;


    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> emprestimoListModel = new ListModelList<Emprestimo>();
    private Session session;
    private Requisicao r;
    private List<EstadoPedido> estadoPedidos;

    @Wire
    private Listbox emprestimoListBox;
    @Wire
    private Tab tab;
    @Wire
    private Window modalDialog, parent;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {



        parent = (Window) comp.getParent();

        session = Sessions.getCurrent();
        super.doAfterCompose(comp);
        emprestimoListModel = getEmprestimoListModel();
        emprestimoListBox.setModel(emprestimoListModel);
        tab.setLabel("Obras Requisitadas por: "+r.getUser().getName()+" "+r.getUser().getLastName());

        estadoPedidos = crudService.getAll(EstadoPedido.class);
    }

    public ListModelList<Emprestimo> getEmprestimoListModel() {

       r = (Requisicao) session.getAttribute("requisicoes");
        List<Emprestimo> lista = new ArrayList<>();

        for(Emprestimo e : r.getPedidos()) {
            List<Emprestimo> le = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.utente.id = " +
                    e.getEmprestimoPK().getUtente().getId() +" and e.emprestimoPK.obra.cota = '"+e.getEmprestimoPK().getObra().getCota()+"'", null);

            for (Emprestimo emp : le)
                lista.add(emp);
        }

        return new ListModelList<Emprestimo>(lista);
    }

    @Listen("onEliminarObra = #emprestimoListBox")
    public void doEliminar(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent();
        Emprestimo emprestimo = (Emprestimo) litem.getValue();

        emprestimoListModel.remove(emprestimo);
    }

    @Listen("onReduzirQtd = #emprestimoListBox")
    public void doReduzirQtd(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent();
        Emprestimo emprestimo = (Emprestimo) litem.getValue();

        if(emprestimo.getQuantidade() == 1){
            Clients.showNotification("nao pode mais dinimuir");
        }else {
            emprestimoListModel.remove(emprestimo);
            emprestimo.setQuantidade(emprestimo.getQuantidade() - 1);
            emprestimoListModel.add(emprestimo);
        }
    }

    @Listen("onAumentarQtd = #emprestimoListBox")
    public void doAumentarQtd(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent();
        Emprestimo emprestimo = (Emprestimo) litem.getValue();
        int total=0;

        for(Emprestimo e : emprestimoListModel)
            total = total + e.getQuantidade();

        if(total == 6){
            Clients.showNotification("Limite de obras a requisitar atingido");
        }else {
            emprestimoListModel.remove(emprestimo);
            emprestimo.setQuantidade(emprestimo.getQuantidade() + 1);
            emprestimoListModel.add(emprestimo);
        }

    }

    @Listen("onClick = #acceptBtn")
    public void accept() {
        EstadoPedido ep = null;

        for(EstadoPedido epa : estadoPedidos){
            if(epa.getIdestadopedido() == 3)
                ep = epa;
        }

        for (Emprestimo e : emprestimoListModel){
            e.setDataaprovacao(Calendar.getInstance());
            e.setEstadoPedido(ep);

            crudService.update(e);
        }
        modalDialog.detach();
    }

    @Listen("onClick = #rejectBtn")
    public void reject() {

        alert(parent.getId());

        EstadoPedido ep = null;

        for(EstadoPedido epa : estadoPedidos){
            if(epa.getIdestadopedido() == 2)
                ep = epa;
        }

        for (Emprestimo e : emprestimoListModel){
            e.setDataaprovacao(Calendar.getInstance());
            e.setEstadoPedido(ep);

            crudService.update(e);
        }

//        ((Listbox)parent.getFellow("")).setModel();

        modalDialog.detach();

    }

}
