package sgb.controller.domainController;

import sgb.domain.Role;
import sgb.domain.Users;
import sgb.service.CRUDService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserController {

    private CRUDService crudService;
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private static MessageDigest m;
    private RoleController rController;

    public UserController(CRUDService crudService,RoleController rController)
    {
        this.crudService = crudService;
        this.rController = rController;
    }

    public void changeUserPassword(Users user, String password){

        Users user1= getUser(user.getId());
        user1.setPassword(password);

        this.crudService.update(user1);

    }

    public Users getUser(int id){

        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("id", id);

        query.append("SELECT u FROM Users u WHERE u.id = :id");

        return (Users) this.crudService.findByJPQuery(query.toString(),parameters).get(0);

    }

    public List<Users> getUsers(){

        query = new StringBuilder();

        query.append("SELECT u FROM Users u order by u.name asc");

        return this.crudService.findByJPQuery(query.toString(),null);
    }

    public List<Users> getUsersByState(int active){

        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("active", active);

        query.append("SELECT u FROM Users u where u.active = :active order by u.name asc");

        return this.crudService.findByJPQuery(query.toString(),parameters);
    }

    public void updateUser(Users user){

        this.crudService.update(user);
    }


    public String encrypt(String password)
    {

        try
        {
            m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(), 0, password.length());
            BigInteger login1 = new BigInteger(1, m.digest());

            password = String.format("%1$032X", login1).toLowerCase();


            return password;
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
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

            }
        }catch (Exception e){}
        return result;
    }

    public boolean isNormalUser (Users user) {
        /*
        *All user wich are not have admin role nether bibliotecario role
        */

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRoleId() == rController.ADMIN || role.getRoleId() ==rController.LIBRARIAN
                    || role.getRoleId() ==rController.GESTOR)
                return false;
        }
        return true;
    }



}
