/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgb.controller.viewsController;


import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;
import sun.rmi.runtime.NewThreadAction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Fonseca
 */

public class ObraController extends SelectorComposer<Component> {
    private CRUDService crudService;
    private List<TipoObra> tipoObraModel;
    private List<AreaCientifica> areaCientificaModel;
    private List<Idioma> idiomaModel;


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

        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();

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
        CRUDService csimp = (CRUDService) SpringUtil.getBean("CRUDService");

        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();
        Obra obra = new Obra();

        obra.setCota(cota.getValue());
        obra.setRegistro(Integer.parseInt(registo.getValue()));

       String [] nomeA = autor.getValue().split(" ");

        Autor aut = new Autor();
        aut.setNome(nomeA[0]);
        aut.setApelido(nomeA[1]);

        Set<Autor> auts = new HashSet<Autor>();

        auts.add(aut);

        obra.setAutores(auts);


        obra.setTitulo(titulo.getValue());

            obra.setAreacientifica(areaCientificaListBox.getSelectedItem().getValue());

//        for (AreaCientifica a : getAreaCientificaModel())
//            if (a.getDescricao().equals(areaCientificaListBox.getSelectedItem().getValue())) {
//                obra.setAreacientifica(a);
//                break;
//            }
//
        obra.setDatapublicacao(new Date(dataPublicacao.getValue().getDay(),
                dataPublicacao.getValue().getMonth(), dataPublicacao.getValue().getYear()));

        obra.setIdioma(idiomaListBox.getSelectedItem().getValue());

//        for (Idioma idioma : getIdiomaModel())
//            if (idioma.getDescricao().equals(idiomaListBox.getSelectedItem().getValue())) {
//                obra.setIdioma(idioma);
//                break;
//            }

        obra.setLocalpublicacao(localPublicacao.getValue());

        obra.setQuantidade(Integer.parseInt(quatddObra.getValue()));

        if (tipoObra.getDescricao().toLowerCase().equals("livro")) {

//            Messagebox.show("isbn"+isbn.getValue());

            Livro livro = new Livro();

            livro.setCota(obra.getCota());
            livro.setIsbn(isbn.getValue());
            livro.setCodigobarra(codigobarra.getValue());
            livro.setEdicao(edicao.getValue());
            livro.setEditora(editora.getValue());
            livro.setObra(obra);


            crudService.Save(obra);
            crudService.Save(livro);

        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {

            Messagebox.show("Not implement yet");

        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {

            Messagebox.show("Not implement yet");


        }

//        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();
//
//        //Messagebox.show("selected: "+tipoObra.getDescricao().toLowerCase());
//
//        if(tipoObra.getDescricao().toLowerCase().equals("livro")) {
//            grpData.setVisible(true);
//            idInclData.setSrc("views/livro.zul");
//        }else if(tipoObra.getDescricao().toLowerCase().equals("cd")){
//            grpData.setVisible(true);
//            idInclData.setSrc("views/cd.zul");
//        }else if(tipoObra.getDescricao().toLowerCase().equals("revista")) {
//            grpData.setVisible(true);
//            idInclData.setSrc("views/revista.zul");
    }

}
