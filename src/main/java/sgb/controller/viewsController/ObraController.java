/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgb.controller.viewsController;


import org.springframework.dao.*;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class ObraController extends SelectorComposer<Component> {

    private CRUDService crudService;
    private List<TipoObra> tipoObraModel;
    private List<AreaCientifica> areaCientificaModel;
    private List<Idioma> idiomaModel;
    private Set<Autor> auts = new HashSet<Autor>();
    private ListModelList<Autor> authorListModel;
    Autor oAutor = new Autor();

    @Wire
    private Listbox authorListBox;

    @Wire
    private Label cota_duplicated_key;
    @Wire
    private Listbox tipoObraListBox;

    @Wire("#grpData")
    Div grpData;

    @Wire
    Include idInclData;

    @Wire
    private Groupbox grplivrodata;

    @Wire
    private Textbox cota;

    @Wire
    private Textbox registo;

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
    private Datebox dataPublicacao;

    @Wire
    private Textbox quatddObra;

//    livro

    @Wire
    private Textbox isbn;

    @Wire
    private Textbox codigobarra;

    @Wire
    private Textbox editora;

    @Wire
    private Textbox edicao;

//cd

    @Wire
    private Textbox topico;

    @Wire
    private Listbox topicoBox;

    public ObraController() {
    }

//    revista


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
//
//        Autor autor = new Autor();
//
//        autor.setNome("Fonseca");
//        autor.setApelido("Fonseca");
//
//        List<Autor> auts = new ArrayList<Autor>();
//
//        auts.add(autor);
        authorListModel = new ListModelList<Autor>();

        authorListBox.setModel(authorListModel);

    }

    public ListModelList<TipoObra> getTipoObraModel() {
        this.crudService = (CRUDService) SpringUtil.getBean("CRUDService");
        List<TipoObra> tipoObras = crudService.getAllQuery("SELECT tipoobra FROM TipoObra tipoobra");
        return new ListModelList<TipoObra>(tipoObras);
    }

    public ListModelList<Idioma> getIdiomaModel() {
        this.crudService = (CRUDService) SpringUtil.getBean("CRUDService");
        List<Idioma> idiomas = crudService.getAllQuery("SELECT idioma FROM Idioma idioma");
        return new ListModelList<Idioma>(idiomas);
    }

    public ListModelList<AreaCientifica> getAreaCientificaModel() {
        this.crudService = (CRUDService) SpringUtil.getBean("CRUDService");
        List<AreaCientifica> areascientificas = crudService.getAllQuery("SELECT areacientifica FROM AreaCientifica areacientifica");
        return new ListModelList<AreaCientifica>(areascientificas);
    }


    @Listen("onSelect = #tipoObraListBox")
    public void change() {
        //CRUDService csimp = (CRUDService) SpringUtil.getBean("CRUDService");

//        Messagebox.show("entrou");

        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();

        //Messagebox.show("selected: "+tipoObra.getDescricao().toLowerCase());

        if (tipoObra.getDescricao().toLowerCase().equals("livro")) {
            grplivrodata.setVisible(true);
            //idInclData.setSrc("views/livro.zul");
        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {
            grpData.setVisible(true);
            idInclData.setSrc("views/cd.zul");
        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {
            grpData.setVisible(true);
            idInclData.setSrc("views/revista.zul");
        }
    }

    @Listen("onClick = #savebtn")
    public void saveData() {

        Obra obra = new Obra();
        Autor aut = new Autor();
        Livro livro = new Livro();
        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();
        CRUDService csimp = (CRUDService) SpringUtil.getBean("CRUDService");
        String [] nomeA = autor.getValue().split(" ");

        aut.setNome(nomeA[0]);
        aut.setApelido(nomeA[1]);
        auts.add(aut);

        obra.setCota(cota.getValue());
        obra.setRegistro(Integer.parseInt(registo.getValue()));
        obra.setTipoobra(tipoObra);
        obra.setAutores(auts);

        obra.setTitulo(titulo.getValue());
        obra.setAreacientifica(areaCientificaListBox.getSelectedItem().getValue());
        obra.setDatapublicacao(new Date(dataPublicacao.getValue().getDay(),
                dataPublicacao.getValue().getMonth(), dataPublicacao.getValue().getYear()));
        obra.setIdioma(idiomaListBox.getSelectedItem().getValue());

        obra.setLocalpublicacao(localPublicacao.getValue());
        obra.setQuantidade(Integer.parseInt(quatddObra.getValue()));



        if (tipoObra.getDescricao().toLowerCase().equals("livro")) {

            livro.setCota(obra.getCota());
            livro.setIsbn(isbn.getValue());
            livro.setCodigobarra(codigobarra.getValue());
            livro.setEdicao(edicao.getValue());
            livro.setEditora(editora.getValue());
            livro.setObra(obra);

        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {

            Messagebox.show("Not implement yet");

        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {

            Messagebox.show("Not implement yet");


        }

        try {

            if (tipoObra.getDescricao().toLowerCase().equals("livro")) {


                crudService.Save(aut);

                crudService.Save(obra);
                crudService.Save(livro);

            }else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {

                Messagebox.show("Not implemented yet");

            } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {

                Messagebox.show("Not implemented yet");

            }
        }
        catch (Exception e) {

            if(e instanceof DataIntegrityViolationException){

                DataIntegrityViolationException dive = (DataIntegrityViolationException) e;

                if(dive.getMostSpecificCause().toString().contains("duplicate key value")){
                    cota.setStyle("color: black; border-color: red;");

                    cota_duplicated_key.setVisible(true);
                    cota_duplicated_key.setStyle("color: red;");
                    cota_duplicated_key.setValue("OPS:Ja existe uma obra com este numero de cota");
                }

            }
        }
    }


    public void rescueAuthor(){

        Autor aut = new Autor();
        String [] nomeA = autor.getValue().split(" ");

        aut.setNome(nomeA[0]);
        aut.setApelido(nomeA[1]);
        auts.add(aut);
    }

    @Listen("onClick = #addAuthor")
    public void addNewAuthor(){

        if (Strings.isBlank(autor.getValue())) {
            Clients.showNotification("Subject is blank, nothing to do ?");
        } else {
            String[] nomeA = autor.getValue().split(" ");
            //save data
            oAutor.setNome(nomeA[0]);
            oAutor.setApelido(nomeA[1]);

            //update the model, by using ListModelList, you don't need to notify todoListModel change
            //it is efficient that only update one item of the listbox
            authorListModel.add(oAutor);
            authorListModel.addToSelection(oAutor);

            //reset value for fast typing.
            autor.setValue(null);
        }

    }


    @Listen("onAuthorDelete = #authorListBox")
    public void doAuthorDelete(ForwardEvent evt){

        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent();
        Autor autor = (Autor) litem.getValue();
        authorListModel.remove(autor);
    }


    @Listen("onAuthorEdite = #authorListBox")
    public void doAuthorEdit(ForwardEvent evt){

        Button btn = (Button)evt.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent();
        Autor autor = (Autor) litem.getValue();
        authorListModel.remove(autor);
        this.autor.setValue(autor.getNome().concat(" "+autor.getApelido()));
    }
}
