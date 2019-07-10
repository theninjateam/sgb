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

public class UpdateObraController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private  ListModelList<FormaAquisicao> formaAquisicaoModel;


    @Wire
    private Intbox quantidade;

    @Wire
    private Textbox descricao;

    @Wire
    private Listbox formaaquisicaoBox;

    @Wire
    private Window modalUpdate;

    private Session session;

    private Obra obra;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();

        formaAquisicaoModel = new ListModelList<FormaAquisicao>(getFormaAquisicaoModel());
        formaaquisicaoBox.setModel(formaAquisicaoModel);

        obra = (Obra) session.getAttribute ("obraToEdite");
    }



    public ListModelList<FormaAquisicao> getFormaAquisicaoModel () {
        List<FormaAquisicao> formaaquisicao = crudService.getAll(FormaAquisicao.class);
        return new ListModelList<FormaAquisicao>(formaaquisicao);
    }

    @Listen("onClick= #updateObra")
    public void updateObra () throws NoSuchAlgorithmException {

        RegistroObra registroObra = new RegistroObra();

        Set<RegistroObra> registroObras = new HashSet<>();
        Set<RegistroObra> novoRegistros = new HashSet<>();

        registroObras = obra.getRegistroObras();

        FormaAquisicao formaaquisicao = formaaquisicaoBox.getSelectedItem().getValue();
        RegistroObraPK registroObraPK = new RegistroObraPK();


        registroObraPK.setObra(obra);
        registroObraPK.setDataRegisto(Calendar.getInstance());

        registroObra.setRegistroObraPK(registroObraPK);
        registroObra.setFormaAquisicao(formaaquisicao);
        registroObra.setObra(obra);
        registroObra.setUser(user);
        registroObra.setObservacao(descricao.getValue());
        registroObra.setQuantidade(quantidade.getValue());

        novoRegistros.add(registroObra);

        obra.setQuantidade(obra.getQuantidade()+quantidade.getValue());
        obra.setRegistroObras(novoRegistros);


        crudService.update(obra);
        modalUpdate.detach(); // close modal
        session.removeAttribute ("obraToEdite");
        Clients.showNotification("Dados da obra atualizados ",null,null,null,5000);
    }


    @Listen("onClick= #exit")
    public void exit ()
    {
        session.removeAttribute ("obraToEdite");
        modalUpdate.detach();
    }

}
