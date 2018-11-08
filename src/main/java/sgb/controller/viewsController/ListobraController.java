package sgb.controller.viewsController;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
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
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.image.RenderedImage;
import java.io.File;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Executions;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ListobraController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;

    private ListModelList<Obra> obraListModel;

    @Wire
    private Listbox obraList;

    @Wire
    private Window listObra;

    @Override
    public void doAfterCompose(Component comp) throws Exception {

        super.doAfterCompose(comp);
        obraListModel = getObraListModel();
        obraList.setModel(obraListModel);
    }

    public ListModelList<Obra> getObraListModel() {
        List<Obra> listaobra = crudService.getAll(Obra.class);
        return new ListModelList<Obra>(listaobra);
    }

    @Listen("onClick = .requisitar")
    public void doRequisitar() {
        Clients.showNotification("Os dados foram guardados com sucesso!", null, null, null, 5000);
    }

    @Listen("ondoRequisitar = #obraList")
    public void doRequisitar(ForwardEvent evt) {
        Clients.showNotification("Os dados foram guardados com sucesso!", null, null, null, 5000);
    }

}