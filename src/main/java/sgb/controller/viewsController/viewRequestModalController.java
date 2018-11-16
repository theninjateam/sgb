/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;

import org.zkoss.zul.*;


/**
 * @author Fonseca, Emerson, Matimbe
 */

public class viewRequestModalController extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;

    @Wire
    Window modalDialog;

    @Listen("onClick = #closeBtn")
    public void showModal(Event e) {
        modalDialog.detach();
    }
}
