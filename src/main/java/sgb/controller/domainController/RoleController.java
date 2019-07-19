package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.HashMap;

public class RoleController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public final int STUDENT;
    public final int TEACHER;
    public final int ADMIN;
    public final int GUEST;
    public final int EMPLOYEE;
    public final int LIBRARIAN;


    public RoleController(CRUDService crudService)
    {
        this.crudService = crudService;

        STUDENT = getRoleId("ESTUDANTE");
        TEACHER = getRoleId("PROFESSOR");
        ADMIN = getRoleId("ADMIN");
        GUEST = getRoleId("VISITANTE");
        LIBRARIAN = getRoleId("BIBLIOTECARIO");
        EMPLOYEE = getRoleId("FUNCIONARIO");
    }

    public int getRoleId(String roleName)
    {
        Role role;
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("role", roleName);
        query.append("SELECT r FROM Role r WHERE r.role = :role");

        role =  ((Role) this.crudService.findEntByJPQueryT(query.toString(), parameters));

        if (role == null)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.append("There is no role called: \""+ roleName+"\"");
                builder.append(" - verifiy if you are calling atributes that exists on database\n");

                throw new Exception(builder.toString());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                System.exit(-1);
            }
        }

        return role.getRoleId();
    }
}
