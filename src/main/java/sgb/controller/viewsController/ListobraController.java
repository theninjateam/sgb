package sgb.controller.viewsController;

import com.sun.rowset.internal.Row;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.domain.Obra;
import sgb.domain.Users;
import sgb.service.CRUDService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListobraController extends SelectorComposer<Component> {
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user;

    private ListModelList<Obra> obraListModel;

    @Wire
    private Listbox obraListBox;


    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        obraListModel = new ListModelList<Obra>(getObraListModel());
        obraListBox.setModel(obraListModel);

    }

    public ListModelList<Obra> getObraListModel() {
        List<Obra> lll = crudService.getAll(Obra.class);
        List<Obra> llb= new ArrayList<>();

        for (Obra o :lll) {
            Obra ob = new Obra();
            ob.setPathpdf(o.getPathpdf());
            ob.setPathcapa(o.getPathcapa());
            ob.setAutores(o.getAutores());
            ob.setAnoPublicacao(o.getAnoPublicacao());
            ob.setRevista(o.getRevista());
            ob.setCd(o.getCd());
            ob.setTipoobra(o.getTipoobra());
            ob.setIdioma(o.getIdioma());
            ob.setAreacientifica(o.getAreacientifica());
            ob.setCota(o.getCota());
            ob.setLocalpublicacao(o.getLocalpublicacao());
            ob.setQuantidade(o.getQuantidade());
            ob.setLivro(o.getLivro());
            ob.setRegistro(o.getRegistro());
            ob.setTitulo(o.getTitulo());
            llb.add(ob);
        }
        return new ListModelList<Obra> (llb);
    }

}
