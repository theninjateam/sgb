package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;

import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.Obra;
import sgb.domain.Users;
import sgb.service.CRUDService;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

public class ListobraController extends SelectorComposer<Component> {
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;

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

    public List<String> getAutores() {

        List<String> list = new ArrayList<>();


        list.add("a");
        list.add("b");

        return list;
    }

    @Listen("onClick = #listObra")
    public void doRequisitar(ForwardEvent event)
    {
        String op = (String) event.getData();

        if(op.trim().contains("requisitar"))
        {
            Button btn = (Button)event.getOrigin().getTarget();
            Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();

            Obra obra = (Obra) litem.getValue();
            Clients.showNotification("Ola: "+obra.getTitulo());
        }
    }
}