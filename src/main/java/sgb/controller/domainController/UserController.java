package sgb.controller.domainController;

import sgb.domain.Users;
import sgb.service.CRUDService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class UserController {

    private CRUDService crudService;
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private static MessageDigest m;

    public UserController(CRUDService crudService) {
        this.crudService = crudService;
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

}
