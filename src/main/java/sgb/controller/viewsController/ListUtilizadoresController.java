package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.UserController;
import sgb.domain.Role;
import sgb.domain.Users;
import sgb.service.CRUDService;

import java.util.List;
import java.util.Set;

public class ListUtilizadoresController extends SelectorComposer<Component> {

    private UserController userController = (UserController) SpringUtil.getBean("userController");
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");


@Wire
   private Listbox listUtilizadores;

@Wire
private Button buttonBlock;

    @Wire
    private Button buttonPesquisar;

    @Wire
    private Textbox textboxPesquisar;

    @Wire
    private Listbox estadoListBox;

    @Wire
    private Vlayout listUsers;

    private ListModelList<Users> usersListModelList;
    private ListModelList<String> estado;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);

        usersListModelList = new ListModelList<Users>(this.getNormalUsers(userController.getUsers()));

        estado = new ListModelList<String>();
        estado.add("All");
        estado.add("Blocked");
        estado.add("Unblocked");

        estadoListBox.setModel(estado);
        listUtilizadores.setModel(usersListModelList);

    }

    @Listen("onSelect = #estadoListBox")

    public void doChanges(){

        pesquisar(textboxPesquisar.getValue());
    }

    @Listen("onBlock = #listUsers")
    public void block(ForwardEvent event) {

        int id = (int)event.getData();

        Users user = userController.getUser(id);

        if (user.getActive() == 1){
            user.setActive(0);
        }else {
            user.setActive(1);
        }
        this.crudService.update(user);

        pesquisar(textboxPesquisar.getValue());
    }

    @Listen("onPesquisar = #textboxPesquisar")
    public void doAutoPesquisar(ForwardEvent event){

        pesquisar(textboxPesquisar.getValue());
    }


    @Listen("onPesquisar = #buttonPesquisar")
    public void doPesquisar(ForwardEvent event)
    {

        pesquisar(textboxPesquisar.getValue());
    }

    public void pesquisar(String keys){

        usersListModelList.removeAll(usersListModelList);

        List <Users> users= this.getNormalUsers(this.getUsers()) ;
        for (Users user: users)
            {
                for ( String key: keys.split(" "))
                {
                    if( user.getName().toLowerCase().contains(key.toLowerCase()) || user.getLastName().toLowerCase().contains(key.toLowerCase()))
                    {
                        usersListModelList.add(user);
                        break;
                    }
                }
            }
            listUtilizadores.setModel(usersListModelList);
    }

    public List<Users> getUsers(){

        String estado="";
        try{
            estado = estadoListBox.getSelectedItem().getValue();
        }catch (Exception ex){}

        if(estado.equals("All") || estado.equals(""))
            return userController.getUsers();
        else {
            String estate = estadoListBox.getSelectedItem().getValue();
            return userController.getUsersByState(getState(estate));
        }
    }

    public List<Users> getNormalUsers(List<Users> users){


        Set<Role> userRoles;
        List<Users> result = users;
        try {
            for (Users user : result) {

                userRoles = user.getRoles();

                for (Role role : userRoles) {

                    if (role.getRole().equals("ADMIN")) {
                        result.remove(user);
                    }

                }
//

            }
        }catch (Exception e){}
        return result;
    }


    public int getState(String state){

        if (state.equals("Blocked"))
            return 0;

        return 1;

    }


}
