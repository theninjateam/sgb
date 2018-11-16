/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.zkoss.zk.ui.util.ForEach;

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
import java.util.*;
import java.util.Calendar;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class ObraController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;
    private  ListModelList<TipoObra> tipoObraModel;
    private  ListModelList<AreaCientifica> areaCientificaModel;
    private  ListModelList<Idioma> idiomaModel;
    private Set<Autor> autores = new HashSet<Autor>();
    private ListModelList<Autor> authorListModel;
    Autor oAutor = new Autor();
    private RegistroObra registroObra = new RegistroObra();
    private String fullPathPDF = null;
    private String relativePathPDF = null;
    private String fullPathCover = null;
    private String relativePathCover = null;
    private Media mediaCover;
    private Media mediaPDF;
    private Session session;

    @Wire
    private Image capa;
    @Wire
    private Button upLoadPDF;

    @Wire
    private Button upLoadCAPA;

    @Wire
    private Label addedPDF;

    @Wire
    private Label addedCapa; // eliminar esta label

    @Wire
    private Window addObra;

    @Wire
    private Listbox authorListBox;

    @Wire
    private Label cota_duplicated_key;

    @Wire
    private Listbox tipoObraListBox;

    @Wire("#grpOtherData")
    Div grpOtherData;

    @Wire("#grpData")
    Div grpData;

    @Wire
    Include idInclOtherData;

    @Wire
    Include idInclData;

    @Wire
    private Groupbox grplivrodata;

    @Wire
    private Groupbox grpcddata;

    @Wire
    private Groupbox grprevistadata;

    @Wire
    private Textbox cota;

    @Wire
    private Intbox registo;

    @Wire
    private Textbox titulo;

    @Wire
    private Textbox autor;

    @Wire
    private Listbox areaCientificaListBox;

    @Wire
    private Listbox idiomaListBox;

    @Wire
    private Textbox localPublicacao;

    @Wire
    private Intbox anoPublicacao;

    @Wire
    private Intbox quatddObra;




    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();

        setAttributeSession(session);

        authorListModel = new ListModelList<Autor>();
        authorListBox.setModel(authorListModel);

        authorListModel = new ListModelList<Autor>();
        authorListBox.setModel(authorListModel);

        tipoObraModel = new ListModelList<TipoObra>(getTipoObraModel());
        tipoObraListBox.setModel(tipoObraModel);

        areaCientificaModel = new ListModelList<AreaCientifica>(getAreaCientificaModel());
        areaCientificaListBox.setModel(areaCientificaModel);

        idiomaModel = new ListModelList<Idioma>(getIdiomaModel());
        idiomaListBox.setModel(idiomaModel);

    }

    public ListModelList<TipoObra> getTipoObraModel() {

        List<TipoObra> tipoObras = crudService.getAll(TipoObra.class);
        return new ListModelList<TipoObra>(tipoObras);
    }

    public ListModelList<Idioma> getIdiomaModel() {
        List<Idioma> idiomas = crudService.getAll(Idioma.class);
        return new ListModelList<Idioma>(idiomas);
    }

    public ListModelList<AreaCientifica> getAreaCientificaModel() {
        List<AreaCientifica> areascientificas = crudService.getAll(AreaCientifica.class);
        return new ListModelList<AreaCientifica>(areascientificas);
    }


        @Listen("onSelect = #tipoObraListBox")
    public void change() {

        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();
        grpData.setVisible(true);
        if (tipoObra.getDescricao().toLowerCase().equals("livro")) {
            idInclData.setSrc("views/livro.zul");
        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {
            idInclData.setSrc("views/cd.zul");
        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {
            idInclData.setSrc("views/revista.zul");

        } else if (tipoObra.getDescricao().toLowerCase().equals("livro com cd")) {
            idInclData.setSrc("views/livro.zul");
            grpOtherData.setVisible(true);
            idInclOtherData.setSrc("views/cd.zul");
        }
    }

    @Listen("onClick = #savebtn")
    public void saveData() throws ParseException {
        check(addObra);

        Obra obra = new Obra();
        Livro livro = new Livro();
        Cd cd = new Cd();
        LivroCd livroCd = new LivroCd();
        Revista revista = new Revista();
        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();
        user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        obra.setCota(cota.getValue());
        obra.setRegistro(registo.getValue());
        obra.setTipoobra(tipoObra);
        obra.setTitulo(titulo.getValue());
        obra.setAreacientifica(areaCientificaListBox.getSelectedItem().getValue());
        obra.setAnoPublicacao(anoPublicacao.getValue());
        obra.setLocalpublicacao(localPublicacao.getValue());
        obra.setQuantidade(quatddObra.getValue());
        obra.setPathcapa(relativePathCover);
        obra.setPathpdf(relativePathPDF);
        obra.setIdioma(idiomaListBox.getSelectedItem().getValue());

        registroObra.setIduser(user.getId());
        registroObra.setCota(obra.getCota());
        registroObra.setDataRegisto(Calendar.getInstance());
        registroObra.setObra(obra);

        if (tipoObra.getDescricao().toLowerCase().equals("livro")) {

            livro.setCota(obra.getCota());
            livro.setIsbn(((Textbox)idInclData.getFellow("isbn")).getValue());
            livro.setCodigobarra(((Textbox)idInclData.getFellow("codigobarra")).getValue());
            livro.setEdicao(((Textbox)idInclData.getFellow("edicao")).getValue());
            livro.setEditora(((Textbox)idInclData.getFellow("editora")).getValue());
            livro.setObra(obra);
            obra.setLivro(livro);
           String aa = ((Label)idInclData.getFellow("escolha")).getValue();
           if(aa.equals("Sim")){
               cd.setIdcd(obra.getCota());
               cd.setDescricao(titulo.getValue());
               cd.setObra(obra);
               obra.setCd(cd);
//               Clients.showNotification("Ola sou sim");
           }

>>>>>>> e6bd73573db96a2c4f1d8de86b2ebe8864978134
        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {
            cd.setIdcd(obra.getCota());
            cd.setDescricao(((Textbox)idInclData.getFellow("descricaoCd")).getValue());
            cd.setObra(obra);
            obra.setCd(cd);
        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {

            revista.setCota(obra.getCota());
            revista.setInstituicao(((Textbox)idInclData.getFellow("instituicaoRevista")).getValue());
            revista.setObra(obra);
            obra.setRevista(revista);
        } else if (tipoObra.getDescricao().toLowerCase().equals("livro com cd")) {

            livroCd.setCota(obra.getCota());
            livroCd.setIsbn(((Textbox) idInclData.getFellow("isbn")).getValue());
            livroCd.setCodigobarra(((Textbox) idInclData.getFellow("codigobarra")).getValue());
            livroCd.setEdicao(((Textbox) idInclData.getFellow("edicao")).getValue());
            livroCd.setEditora(((Textbox) idInclData.getFellow("editora")).getValue());
            livroCd.setVolume(Integer.parseInt(((Textbox) idInclData.getFellow("volume")).getValue()));
            livroCd.setDescricaocd(((Textbox) idInclOtherData.getFellow("descricaoCd")).getValue());
            livroCd.setObra(obra);
            obra.setLivroCd(livroCd);
        }

        try
        {
            for(Autor autor: authorListModel) // esta linha devera sair
                autores.add(autor);


            obra.setRegistroObra(registroObra);
            obra.setAutores(autores);

          //   deve existir transacoes
            crudService.Save(obra);

            if(fullPathPDF != null)
                Files.copy(new File(fullPathPDF), mediaPDF.getStreamData());

            if(fullPathCover != null)
                Files.copy(new File(fullPathCover), mediaCover.getStreamData());


            Clients.showNotification("Os dados foram guardados com sucesso!",null,null,null,5000);
        }
        catch (Exception e)
        {
            if(e instanceof DataIntegrityViolationException)
            {
                DataIntegrityViolationException dive = (DataIntegrityViolationException) e;

                if(dive.getMostSpecificCause().toString().contains("duplicate key value"))
                {
                    Clients.showNotification("Ops: Parece que ja existe uma Obra com numero de cota '"+cota.getValue()+"'");
                }
            }
            e.printStackTrace();
        }
    }

    @Listen("onUpLoadPDF = #upLoadPDF")
    public void loadPDF(ForwardEvent event)
    {
        UploadEvent uploadEvent = (UploadEvent) event.getOrigin();
        mediaPDF = uploadEvent.getMedia();

        if (!mediaPDF.getFormat().equals("pdf"))
        {
            Clients.showNotification("Ficheiro Invalido, carrega um ficheiro pdf");
        }
        else
        {
            relativePathPDF = "digitalLibrary/pdf/".concat(mediaPDF.getName());
            fullPathPDF = Executions.getCurrent().getDesktop().getWebApp().getRealPath(relativePathPDF);
            addedPDF.setValue(mediaPDF.getName());

            this.upLoadPDF.setLabel("eliminar PDF adicionado");
            this.upLoadPDF.setUpload("false");
        }
    }

    @Listen("onDeleteUpLoadedPDF = #upLoadPDF")
    public void deletUpLoadPDF(ForwardEvent event)
    {
        if(relativePathPDF != null || fullPathPDF != null)
        {
            relativePathPDF = null;
            fullPathPDF = null;
            mediaPDF = null;

            addedPDF.setValue(null);
            this.upLoadPDF.setUpload("true");
            this.upLoadPDF.setLabel("adicionar PDF");
        }
    }

    @Listen("onUpLoadCAPA = #upLoadCAPA")
    public void loadCAPA(ForwardEvent event)
    {
        UploadEvent uploadEvent = (UploadEvent) event.getOrigin();
        mediaCover = uploadEvent.getMedia();

        if (!mediaCover.getContentType().contains("image"))
        {
            Clients.showNotification("Ficheiro Invalido, carrega uma imagem");
        }
        else
        {
            relativePathCover = "digitalLibrary/cover/".concat(mediaCover.getName());
            fullPathCover = Executions.getCurrent().getDesktop().getWebApp().getRealPath(relativePathCover);

            this.capa.setContent((org.zkoss.image.Image) mediaCover);
            this.upLoadCAPA.setLabel("eliminar CAPA adicionada");
            this.upLoadCAPA.setUpload("false");
        }
    }

    @Listen("onDeleteUpLoadedCAPA = #upLoadCAPA")
    public void deletUpLoadCAPA(ForwardEvent event) throws IOException {
        if(relativePathCover != null || fullPathCover != null)
        {

            //Clients.showNotification(Executions.getCurrent().getDesktop().getWebApp().getRealPath("digitalLibrary/cover/default.jpg"));
            String fullPathDefaultCover = Executions.getCurrent().getDesktop().getWebApp().getRealPath("digitalLibrary/cover/default.jpg");

            Media mediaDefaultCover = new AImage(fullPathDefaultCover);

            relativePathCover = null;
            fullPathCover = null;
            mediaCover = null;

            this.capa.setContent((org.zkoss.image.Image) mediaDefaultCover);

            this.upLoadCAPA.setUpload("true");
            this.upLoadCAPA.setLabel("adicionar CAPA");
        }
    }

    @Listen("onClick = #addAuthor")
    public void addNewAuthor() throws NoSuchAlgorithmException {

        if (Strings.isBlank(autor.getValue()))
        {
            Clients.showNotification("Informe O Nome e o Apelido do Autor");
        }
        else
        {
            Autor outroAutor = new Autor();
            String nomeC = autor.getValue().trim();

            outroAutor.setHashcode(DigestUtils.md5Hex(nomeC.toLowerCase()));
            outroAutor.setNome(nomeC);

            authorListModel.add(outroAutor);
            authorListModel.addToSelection(outroAutor);
            autor.setValue(null);
        }
    }

    @Listen("onAuthorDelete = #authorListBox")
    public void doAuthorDelete(ForwardEvent evt)
    {
        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent();
        Autor autor = (Autor) litem.getValue();
        authorListModel.remove(autor);
    }

    @Listen("onAuthorEdite = #authorListBox")
    public void doAuthorEdit(ForwardEvent evt)
    {
        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent();
        Autor autor = (Autor) litem.getValue();
        authorListModel.remove(autor);
        this.autor.setValue(autor.getNome());
    }

    private void check(Component component) {
        checkIsValid(component);

        if (component.isVisible() || component instanceof Tabpanel) {
            List<Component> children = component.getChildren();
            for (Component each : children) {
                check(each);
            }
        }
    }

    private void limpar(Component component) {
        limparComp(component);

        //if (component.isVisible()) {
        List<Component> children = component.getChildren();
        for (Component each : children) {
            limpar(each);
        }
        // }
    }

    public void limparComp(Component component) {
        Constraint co = null;
        if (component instanceof InputElement) {
            Constraint c = ((InputElement) component).getConstraint();
            if (!(component instanceof Combobox)) {
                if (c != null) {
                    SimpleConstraint sc = (SimpleConstraint) c;
                    String s = sc.getClientConstraint();
                    ((InputElement) component).setConstraint(co);
                    ((InputElement) component).setText("");
                    ((InputElement) component).setConstraint(c);
                } else {
                    ((InputElement) component).setText("");
                }
            }
        }
    }
    private void checkIsValid(Component component) {
        if (component instanceof InputElement) {
            if ((!((InputElement) component).isValid())) {
                Clients.scrollIntoView(component);
                ((InputElement) component).getText();
            }
            if (((component instanceof Combobox) && ((Combobox) component).getSelectedItem() == null)) {
                Clients.scrollIntoView(component);
                ((Combobox) component).setText("");
                ((Combobox) component).getValue();
            }
        }
    }

    private void setAttributeSession(Session session)
    {
        if (session.getAttribute("addObraCota") == null)
            session.setAttribute("addObraCota", cota);
        else
            cota.setValue(((Textbox) session.getAttribute("addObraCota")).getValue());

        if (session.getAttribute("addObraTitulo") == null)
            session.setAttribute("addObraTitulo", titulo);
        else
            titulo.setValue(((Textbox) session.getAttribute("addObraTitulo")).getValue());
    }

    private void removeAttributeSession(Session session)
    {
        if (session.getAttribute ("addObraCota") != null)
            session.removeAttribute("addObraCota");

        if (session.getAttribute("addObraTitulo") != null)
            session.removeAttribute("addObraTitulo");
    }

}
