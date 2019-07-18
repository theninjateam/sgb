package sgb.domain;

public class Permission
{
    private Zitem zitem;
    private boolean permission = false;

    public Zitem getZitem() {
        return zitem;
    }

    public void setZitem(Zitem zitem) {
        this.zitem = zitem;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }
}
