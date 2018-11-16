/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.Obra;
import sgb.domain.Requisicao;
import sgb.service.CRUDService;

import java.util.List;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class listRequestsController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
//    private Users user;
    private Session session;

    private ListModelList<Requisicao> requestListModel;

    @Wire
    private Listbox requestListBox;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        requestListModel = getrequestListModel();
        requestListBox.setModel(requestListModel);

    }

    public ListModelList<Requisicao> getrequestListModel() {
        List<Requisicao> listarequisicao = crudService.getAll(Requisicao.class);
        listarequisicao.get(0);
        return new ListModelList<Requisicao>(listarequisicao);
    }

    @Listen("onSelect = #orequestListBox")
    public void doEliminar(ForwardEvent event)
    {
        Clients.showNotification("Eliminar");

    }

    //private static final long serialVersionUID = 1L;


//
//    @Listen("onClick = #orderBtn")
//    public void showModal(Event e) {
//        //create a window programmatically and use it as a modal dialog.
//        Window window = (Window) Executions.createComponents(
//                "/widgets/window/modal_dialog/employee_dialog.zul", null, null);
//        window.doModal();
//    }
}
