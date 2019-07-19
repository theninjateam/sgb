/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
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
    private  List<FormaAquisicao> formaAquisicaoModel;
    private Obra obra;

    private int quantidade;
    private String descricao;
    private FormaAquisicao formaAquisicao;


    private Session session =Sessions.getCurrent();

    @Init
    public void init () throws Exception {
        this.obra = (Obra) this.session.getAttribute ("obraToEdite");
        this.formaAquisicaoModel =  getFormaAquisicaoModel();

    }


    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public List<FormaAquisicao> getFormaAquisicaoModel () {
        List<FormaAquisicao> formaaquisicao = crudService.getAll(FormaAquisicao.class);
        return formaaquisicao;
    }

    @Command
    public void updateObra (@BindingParam("modal") Window modal)throws NoSuchAlgorithmException {

        RegistroObra registroObra = new RegistroObra();
        Set<RegistroObra> registroObras = new HashSet<>();


        registroObras = obra.getRegistroObras();

        RegistroObraPK registroObraPK = new RegistroObraPK();
        registroObraPK.setObra(obra);
        registroObraPK.setDataRegisto(Calendar.getInstance());

        registroObra.setRegistroObraPK(registroObraPK);
        registroObra.setFormaAquisicao(this.formaAquisicao);
        registroObra.setObra(obra);
        registroObra.setUser(this.user);
        registroObra.setObservacao(this.descricao);
        registroObra.setQuantidade(this.quantidade);

        registroObras.add(registroObra);

        obra.setQuantidade(obra.getQuantidade()+quantidade);
        obra.setRegistroObras(registroObras);


        crudService.update(obra);
        modal.detach(); // close modal

        Clients.showNotification("Dados da obra atualizados ",null,null,null,5000);
    }


    @Command
    public void exit (@BindingParam("modal") Window modal)
    {
        modal.detach();
    }

    public void setFormaAquisicaoModel(List<FormaAquisicao> formaAquisicaoModel) {
        this.formaAquisicaoModel = formaAquisicaoModel;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public FormaAquisicao getFormaAquisicao() {
        return formaAquisicao;
    }

    public void setFormaAquisicao(FormaAquisicao formaAquisicao) {
        this.formaAquisicao = formaAquisicao;
    }
}
