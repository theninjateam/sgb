/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.lang.Strings;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import sgb.controller.domainController.RoleController;
import sgb.controller.domainController.UserController;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.*;
import java.util.Calendar;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class AddUserController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Role> roleModel;
    private UserController userController = (UserController) SpringUtil.getBean("userController");
    private RoleController rController = (RoleController) SpringUtil.getBean("roleController");


    private Session session;



    @Wire
    private Textbox Nome;

    @Wire
    private Textbox Apelido;

    @Wire
    private Textbox email;

    @Wire
    private Textbox autor;
    @Wire
    private Intbox userid;




    @Wire
    private Listbox roleListBox;





    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();


        roleModel = new ListModelList<Role>(getRoleModel());
        roleListBox.setModel(roleModel);


    }


    public ListModelList<Role> getRoleModel() {

        List<Role> roles = new ArrayList<Role>();


        for(Role role : crudService.getAll(Role.class)){
            if(isAdminUser(user)) {
                if(role.getRoleId() != rController.ADMIN ) {
                    roles.add(role);
                }
            }
            if(isGestorUser(user)){
                if(role.getRoleId() != rController.GESTOR && role.getRoleId() != rController.ADMIN ) {
                    roles.add(role);
                }
            }
        }
        return new ListModelList<Role>(roles);
    }

    public boolean isAdminUser (Users user) {

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRoleId() == rController.ADMIN)
                return true;
        }
        return false;
    }

    public boolean isGestorUser (Users user) {

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRoleId() == rController.GESTOR)
                return true;
        }
        return false;
    }




    @Listen("onClick = #savebtn")
    public void saveData() throws ParseException {

        Users user = new Users();
        user.setActive(1);
        Set<Role> roles = new HashSet<>();
        Role role = roleListBox.getSelectedItem().getValue();
        roles.add(role);

        String pass = userController.encrypt("0123456789");
        user.setPassword(pass);
        user.setName(Nome.getValue());
        user.setLastName(Apelido.getValue());
        user.setEmail(email.getValue());
        user.setRoles(roles);
        user.setId(userid.getValue());

        crudService.Save(user);
        Clients.showNotification("Os dados foram guardados com sucesso!",null,null,null,5000);

    }




}
