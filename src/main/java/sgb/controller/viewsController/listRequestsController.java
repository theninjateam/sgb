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
import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.HashMap;
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
        HashMap<Users,List<Emprestimo>> hashMap = new HashMap<>();
        List<Emprestimo> listammprestimos = crudService.getAll(Emprestimo.class);
        List<Requisicao> listarequisicao = new ArrayList<>();

        for(Emprestimo e: listammprestimos){
                Users u = e.getEmprestimoPK().getUser();

                if(hashMap.containsKey(u)){

                    hashMap.get(u).add(e);
                }else{
                    ArrayList<Emprestimo> list = new ArrayList<>();
                    list.add(e);
                    hashMap.put(u,list);
                }
        }

        for(Users u : hashMap.keySet()) {
            Requisicao r = new Requisicao(u,hashMap.get(u));
            listarequisicao.add(r);
        }

        return new ListModelList<Requisicao>(listarequisicao);
    }

    @Listen("onSelect = #requestListBox")
    public void doVerificarRequisicao(Event e)
    {

        Requisicao r = (Requisicao) requestListBox.getSelectedItem().getValue();


        session.setAttribute("requisicoes", r );


        //create a window programmatically and use it as a modal dialog.
        Window window = (Window)Executions.createComponents(
                "views/viewRequestModal.zul", null, null);
        window.doModal();

    }

}
