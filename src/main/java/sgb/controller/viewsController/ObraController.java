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
    private  ListModelList<TipoObra> tipoObraModel;
    private  ListModelList<AreaCientifica> areaCientificaModel;
    private  ListModelList<Idioma> idiomaModel;
    private Set<Autor> autores = new HashSet<Autor>();
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
    private Groupbox grpcddata;

    @Wire
    private Groupbox grprevistadata;

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
    private Textbox descricaoCd;

    //revista
    @Wire
    private Textbox instituicaoRevista;

    public ObraController() {
    }

//    revista


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

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
            grprevistadata.setVisible(false);
            grpcddata.setVisible(false);
            //idInclData.setSrc("views/livro.zul");
        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {
            grpcddata.setVisible(true);
            grplivrodata.setVisible(false);
            grprevistadata.setVisible(false);
            //idInclData.setSrc("views/cd.zul");
        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {
            grprevistadata.setVisible(true);
            grplivrodata.setVisible(false);
            grpcddata.setVisible(false);
            //idInclData.setSrc("views/revista.zul");
        }
    }

    @Listen("onClick = #savebtn")
    public void saveData() {

        Obra obra = new Obra();
        Livro livro = new Livro();
        Cd cd = new Cd();
        Revista revista = new Revista();
        TipoObra tipoObra = tipoObraListBox.getSelectedItem().getValue();
        CRUDService csimp = (CRUDService) SpringUtil.getBean("CRUDService");

        obra.setCota(cota.getValue());
        obra.setRegistro(Integer.parseInt(registo.getValue()));
        obra.setTipoobra(tipoObra);

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
            obra.setLivro(livro);
        } else if (tipoObra.getDescricao().toLowerCase().equals("cd")) {
            cd.setIdcd(obra.getCota());
            cd.setDescricao(descricaoCd.getValue());
            cd.setObra(obra);
            obra.setCd(cd);
        } else if (tipoObra.getDescricao().toLowerCase().equals("revista")) {
            revista.setCota(obra.getCota());
            revista.setInstituicao(instituicaoRevista.getValue());
            revista.setObra(obra);
            obra.setRevista(revista);
        }

        try
        {
            for(Autor autor: authorListModel) // esta linha devera sair
                autores.add(autor);

            obra.setAutores(autores);
            crudService.Save(obra);
            Clients.showNotification("Os dados foram guadados com sucesso!");
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

    public void removerAu()
    {
    }

    @Listen("onClick = #addAuthor")
    public void addNewAuthor(){

        if (Strings.isBlank(autor.getValue()))
        {
            Clients.showNotification("Informe O Nome e o Apelido do Autor");
        }
        else
        {
            Autor outroAutor = new Autor();
            String nomeC = "";
            String[] nomeA = autor.getValue().split(" ");

            for(int i=0; i<nomeA.length-1; i++){
                nomeC+=nomeA[i]+ " ";
            }
            if(nomeA.length<2) {
                outroAutor.setNome(nomeC);
                outroAutor .setApelido(null);
            } else {
                outroAutor.setNome(nomeC);
                outroAutor.setApelido(nomeA[nomeA.length - 1]);
            }

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
        this.autor.setValue(autor.getNome().concat(" "+autor.getApelido()));
    }
}
