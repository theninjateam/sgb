package sgb.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "item_role")

public class ItemRole
{
    @EmbeddedId
    private ItemRolePK itemRolePK;

    public ItemRolePK getItemRolePK() {
        return itemRolePK;
    }

    public void setItemRolePK(ItemRolePK itemRolePK) {
        this.itemRolePK = itemRolePK;
    }
}
