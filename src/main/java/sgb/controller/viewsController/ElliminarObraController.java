/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

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

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class ElliminarObraController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
//    private  ListModelList<FormaAquisicao> formaAquisicaoModel;
    private  ListModelList<Item> eliminarListModel;




    @Wire
    private Window modalEliminar;

    @Wire
    private Listbox eliminarListBox;

    private Session session;

    private Obra obra;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();

        obra = (Obra) session.getAttribute ("obraToEdite");
//        alert( obra.getTitulo());
        Item item = new Item();
        item.setObra(obra);
        item.setQuantidade(obra.getQuantidade());


        eliminarListModel = new ListModelList<Item>();
        eliminarListModel.add(item);
        eliminarListBox.setModel(eliminarListModel);

    }

    @Listen("onReduzirQtd = #eliminarListBox")
    public void doReduzirQtd(ForwardEvent event) {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem litem = (Listitem) getListitem(btn);
        Item item = (Item) litem.getValue();
        item.setQuantidade(item.getQuantidade() - 1);
        Clients.showNotification("successful",null,null,null,5000);

    }
    @Listen("onAumentarQtd = #eliminarListBox")
    public void doAumentarQtd(ForwardEvent event) {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem litem = (Listitem) getListitem(btn);
        Item item = (Item) litem.getValue();
        item.setQuantidade(item.getQuantidade() + 1);
        Clients.showNotification("successful",null,null,null,5000);

    }

    public Component getListitem (Button btn)
    {
        Component component = btn.getParent();

        while(true)
        {
            if ( component instanceof  Listitem)
            {
                return  component;
            }

            if(component == null)
                return null;

            component = component.getParent();
        }
    }

}
