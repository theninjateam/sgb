package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;

public class RoleController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public final int STUDENT;
    public final int TEACHER;
    public final int ADMIN;

    public RoleController(CRUDService crudService)
    {
        this.crudService = crudService;
        STUDENT = getRoleId("STUDENT");
        TEACHER = getRoleId("STUDENT");
        ADMIN = getRoleId("ADMIN");

    }

    public int getRoleId(String role)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("role", role);
        query.append("SELECT r FROM Role r WHERE r.role = :role");

        return ((Role) this.crudService.findEntByJPQueryT(query.toString(), parameters)).getRoleId();
    }
}
