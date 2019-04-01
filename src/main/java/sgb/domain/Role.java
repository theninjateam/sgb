package sgb.domain;

import javax.persistence.*;

@Entity
@Table(name = "role", schema = "public")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role")

    private String role;


    @Column(name = "qtdmaxobras")
    private int qtdMaxObras;

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
}
