package sgb.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role", schema = "public")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role")

    private String role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "item_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Zitem> zitems;

    @Column(name = "qtdmaxobras")
    private int qtdMaxObras;

    @Column(name = "iconpath")
    private String iconPath = null;

    public Role() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getQtdMaxObras() { return this.qtdMaxObras; }

    public void setQtdMaxObras(int qtdMaxObras) {
        this.qtdMaxObras = qtdMaxObras;
    }

    public Set<Zitem> getZitems() {
        return zitems;
    }

    public void setZitems(Set<Zitem> zitems) {
        this.zitems = zitems;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public boolean containZitem(Zitem zitem)
    {
        for (Zitem zi : zitems)
        {
            if (zi.getItem().equals(zitem.getItem()))
                return true;
        }
        return false;
    }
}
