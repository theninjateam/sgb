/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgb.controller.viewsController;


import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import sgb.domain.TipoObra;
import sgb.service.CRUDService;

import java.util.List;

/**
 *
 * @author Fonseca
 */

public class ObraController extends SelectorComposer<Component>
{
    private CRUDService crudService;
    private List<TipoObra> tipoObraModel;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
    }

    public ListModelList<TipoObra> getTipoObraModel()
    {
        this.crudService = (CRUDService) SpringUtil.getBean("CRUDService");
        List<TipoObra> tipoObras =  crudService.getAllQuery("SELECT tipoobra FROM TipoObra tipoobra");
        return new ListModelList<TipoObra>(tipoObras);
    }
}
