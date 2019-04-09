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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Calendar;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class AddObraController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<TipoObra> tipoObraModel;
    private ListModelList<AreaCientifica> areaCientificaModel;
    private ListModelList<Idioma> idiomaModel;
    private ListModelList<FormaAquisicao> formaAquisicaoModel;

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
    private Textbox cota;

    @Wire
    private Intbox registo;

    @Wire
    private Textbox titulo;

    @Wire
    private Textbox autor;

    @Wire
    private Textbox localPublicacao;

    @Wire
    private Intbox anoPublicacao;

    @Wire
    private Intbox quatddObra;
    @Wire
    private Textbox isbn;
    @Wire
    private Textbox editora;
    @Wire
    private Textbox codigobarra;
    @Wire
    private Intbox edicao;
    @Wire
    private Intbox volume;
    @Wire
    private Textbox observacao;




    @Wire
    private Listbox formaaquisicaoBox;
    @Wire
    private Listbox areaCientificaListBox;
    @Wire
    private Listbox idiomaListBox;
    @Wire
    private Listbox tipoObraListBox;
    @Wire
    private Label escolha;





    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();

        setAttributeSession(session);

        authorListModel = new ListModelList<Autor>();
        authorListBox.setModel(authorListModel);

        tipoObraModel = new ListModelList<TipoObra>(getTipoObraModel());
        tipoObraListBox.setModel(tipoObraModel);

        areaCientificaModel = new ListModelList<AreaCientifica>(getAreaCientificaModel());
        areaCientificaListBox.setModel(areaCientificaModel);

        idiomaModel = new ListModelList<Idioma>(getIdiomaModel());
        idiomaListBox.setModel(idiomaModel);

        formaAquisicaoModel = new ListModelList<FormaAquisicao>(getFormaAquisicaoModel());
        formaaquisicaoBox.setModel(formaAquisicaoModel);


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

    public ListModelList<FormaAquisicao> getFormaAquisicaoModel () {
        List<FormaAquisicao> formaaquisicao = crudService.getAll(FormaAquisicao.class);
        return new ListModelList<FormaAquisicao>(formaaquisicao);
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

        RegistroObraPK registroObraPK = new RegistroObraPK();

        registroObraPK.setObra(obra);
        registroObraPK.setDataRegisto(Calendar.getInstance());

        registroObra.setRegistroObraPK(registroObraPK);
        registroObra.setFormaAquisicao(formaaquisicaoBox.getSelectedItem().getValue());
        registroObra.setObra(obra);
        registroObra.setUser(user);
        registroObra.setQuantidade(quatddObra.getValue());
        registroObra.setObservacao(observacao.getValue());

        if (tipoObra.getDescricao().toLowerCase().equals("livro")) {

            livro.setCota(obra.getCota());
            livro.setIsbn(isbn.getValue());
            livro.setCodigobarra(codigobarra.getValue());
            livro.setEdicao(edicao.getValue());
            livro.setEditora(editora.getValue());
            livro.setVolume(volume.getValue());
            livro.setObra(obra);
            obra.setLivro(livro);

        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {
            cd.setIdcd(obra.getCota());
            cd.setDescricao(titulo.getValue());
            cd.setObra(obra);
            obra.setCd(cd);
        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {

            revista.setCota(obra.getCota());
            revista.setInstituicao(editora.getValue());
            revista.setObra(obra);
            obra.setRevista(revista);
        }

        if(escolha.getValue().equals("Sim")){
               cd.setIdcd(obra.getCota());
               cd.setDescricao(titulo.getValue());
               cd.setObra(obra);
               obra.setCd(cd);
        }

        try
        {
            for(Autor autor: authorListModel) // esta linha devera sair
                autores.add(autor);

            Set<RegistroObra> registroObras = new HashSet<>();
            registroObras.add(registroObra);

            obra.setAutores(autores);
            obra.setRegistroObras(registroObras);

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
                    Clients.showNotification("Ops: Parece que ja existe uma ObraConcurrenceControl com numero de cota '"+cota.getValue()+"'",null,null,null,5000);
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
            Clients.showNotification("Ficheiro Invalido, carrega um ficheiro pdf",null,null,null,5000);
        }
        else
        {
            relativePathPDF = "digitalLibrary/pdf/".concat(mediaPDF.getName());
            fullPathPDF = Executions.getCurrent().getDesktop().getWebApp().getRealPath(relativePathPDF);
            addedPDF.setValue(mediaPDF.getName());

            this.upLoadPDF.setLabel("Eliminar PDF adicionado");
            this.upLoadPDF.setUpload("false");
            this.upLoadPDF.setSclass("w3-btn ww3-light-grey");
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
            this.upLoadPDF.setLabel("Adicionar PDF");
            this.upLoadPDF.setSclass("w3-btn ww3-light-grey");
            this.upLoadPDF.setMold("os");

        }
    }

    @Listen("onUpLoadCAPA = #upLoadCAPA")
    public void loadCAPA(ForwardEvent event)
    {
        UploadEvent uploadEvent = (UploadEvent) event.getOrigin();
        mediaCover = uploadEvent.getMedia();

        if (!mediaCover.getContentType().contains("image"))
        {
            Clients.showNotification("Ficheiro Invalido, carrega uma imagem",null,null,null,5000);
        }
        else
        {
            relativePathCover = "digitalLibrary/cover/".concat(mediaCover.getName());
            fullPathCover = Executions.getCurrent().getDesktop().getWebApp().getRealPath(relativePathCover);

            this.capa.setContent((org.zkoss.image.Image) mediaCover);
            this.upLoadCAPA.setLabel("Eliminar CAPA adicionada");
            this.upLoadCAPA.setUpload("false");
            this.upLoadCAPA.setSclass("w3-btn ww3-light-grey");
        }
    }

    @Listen("onDeleteUpLoadedCAPA = #upLoadCAPA")
    public void deletUpLoadCAPA(ForwardEvent event) throws IOException {
        if(relativePathCover != null || fullPathCover != null)
        {

            //Clients.showNotification(Executions.getCurrent().getDesktop().getWebApp().getRealPath("digitalLibrary/cover/default.jpg"));
            String fullPathDefaultCover = Executions.getCurrent().getDesktop().getWebApp().getRealPath("img/capa.jpg");

            Media mediaDefaultCover = new AImage(fullPathDefaultCover);

            relativePathCover = null;
            fullPathCover = null;
            mediaCover = null;

            this.capa.setContent((org.zkoss.image.Image) mediaDefaultCover);

            this.upLoadCAPA.setUpload("true");
            this.upLoadCAPA.setLabel("Adicionar CAPA");
            this.upLoadCAPA.setSclass("w3-btn ww3-light-grey");
            this.upLoadCAPA.setMold("os");

        }
    }

    @Listen("onClick = #addAuthor")
    public void addNewAuthor() throws NoSuchAlgorithmException {

        if (Strings.isBlank(autor.getValue()))
        {
            Clients.showNotification("Informe O Nome e o Apelido do Autor",null,null,null,5000);
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
