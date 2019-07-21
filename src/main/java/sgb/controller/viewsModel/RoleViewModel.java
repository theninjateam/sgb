package sgb.controller.viewsModel;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.*;
import sgb.domain.*;
import sgb.request.Request;
import sgb.service.CRUDService;
import sgb.domain.Role;
import sgb.domain.Permission;

import java.util.ArrayList;
import java.util.List;

import static org.zkoss.zk.ui.util.Clients.alert;

public class RoleViewModel
{
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Session session = Sessions.getCurrent();

    private RoleController roleController = (RoleController) SpringUtil.getBean("roleController");
    private ConfigControler configControler = (ConfigControler) SpringUtil.getBean("configControler");
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");

    private List<Role> roles = new ArrayList<Role>();
    private List<Permission> permissions = new ArrayList<Permission>();
    private boolean permissionListVisibilty = false;
    private Role role;

    @Init
    public void init() throws Exception
    {
    }

    @Command("updateRole")
    public void updateRole(@BindingParam("permission") Permission permission)
    {
        ItemRolePK itemRolePK = new ItemRolePK();
        ItemRole itemRole = new ItemRole();
        role = crudService.get(Role.class, role.getRoleId());
        setRole(role);

        try
        {

            if (role.containZitem(permission.getZitem()))
            {
                if (permission.isPermission() == false)
                {
                    itemRolePK.setRole(role);
                    itemRolePK.setZitem(permission.getZitem());
                    itemRole.setItemRolePK(itemRolePK);
                    crudService.delete(itemRole);
                }
            }
            else
            {
                if (permission.isPermission() == true)
                {
                    itemRolePK.setRole(role);
                    itemRolePK.setZitem(permission.getZitem());
                    itemRole.setItemRolePK(itemRolePK);
                    crudService.Save(itemRole);
                }
            }
            role = crudService.get(Role.class, role.getRoleId());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Clients.showNotification("Algo Errado Aconteceu",null,null,null,5000);
        }
    }

    @NotifyChange({"permissionListVisibilty"})
    @Command("back")
    public void back()
    {
        permissionListVisibilty = false;
        BindUtils.postNotifyChange(null, null, this, "permissionListVisibilty");
    }

    @NotifyChange({"permissionListVisibilty", "permissions", "role"})
    @Command("viewRolePermissions")
    public void viewRolePermissions(@BindingParam("role") Role role)
    {
        role = crudService.get(Role.class, role.getRoleId());
        setRole(role);

        permissionListVisibilty  = true;
        permissions = new ArrayList<Permission>();

        try
        {
            for (Zitem zitem: crudService.getAll(Zitem.class))
            {
                Permission permission = new Permission();

                permission.setZitem(zitem);

                if (role.containZitem(zitem))
                    permission.setPermission(true);

                permissions.add(permission);
            }

            BindUtils.postNotifyChange(null, null, this, "permissions");
            BindUtils.postNotifyChange(null, null, this, "permissionListVisibilty");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public List<Role> getRoles() {
        return crudService.getAll(Role.class);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public boolean getPermissionListVisibilty() {
        return permissionListVisibilty;
    }

    public void setPermissionListVisibilty(boolean permissionListVisibilty) {
        this.permissionListVisibilty = permissionListVisibilty;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}