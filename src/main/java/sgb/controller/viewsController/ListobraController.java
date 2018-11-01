package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.domain.Obra;
import sgb.domain.Users;
import sgb.service.CRUDService;

import java.util.List;

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

    public ListModelList<Obra> getObraListModel () {
        List<Obra> listaobra = crudService.getAll(Obra.class);
        alert(listaobra.get(0).getIdioma().getDescricao());
        return new ListModelList<Obra>(listaobra);

    }

}
