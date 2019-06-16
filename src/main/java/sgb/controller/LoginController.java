/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sgb.controller;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.service.CRUDService;

;import javax.servlet.http.HttpSession;

/**
 *
 * @author user
 */
public class LoginController extends SelectorComposer<Component> {

    private final CRUDService csimp = (CRUDService) SpringUtil.getBean("CRUDService");


    @Listen("onClick=#logout")
    public void doLogout() {
        Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
        Sessions.getCurrent().invalidate();
        Executions.sendRedirect("/");
    }




}
