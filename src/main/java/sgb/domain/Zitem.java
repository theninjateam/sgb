package sgb.domain;

import javax.persistence.*;

@Entity
@Table(name = "item", schema = "public")

public class Zitem
{
    @Id
    @Column(name = "item_id")
    private int roleId;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "item")
    private String item;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
