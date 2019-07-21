package sgb.domain;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ItemRolePK implements Serializable
{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Zitem zitem;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Zitem getZitem() {
        return zitem;
    }

    public void setZitem(Zitem zitem) {
        this.zitem = zitem;
    }
}