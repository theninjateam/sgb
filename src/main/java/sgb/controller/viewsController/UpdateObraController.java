/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.lang.Strings;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import org.zkoss.zul.impl.InputElement;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class UpdateObraController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;
    private  ListModelList<FormaAquisicao> formaAquisicaoModel;
    private RegistroObra registroObra = new RegistroObra();

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
        alert( obra.getTitulo());
    }


    public ListModelList<FormaAquisicao> getFormaAquisicaoModel () {
        List<FormaAquisicao> formaaquisicao = crudService.getAll(FormaAquisicao.class);
        return new ListModelList<FormaAquisicao>(formaaquisicao);
    }
    @Listen("onClick= #updateObra")
    public void updateObra () throws NoSuchAlgorithmException {
        Clients.showNotification("Modal Update",null,null,null,5000);
        modalUpdate.detach(); // close modal
    }



}
