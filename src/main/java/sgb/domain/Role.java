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
    private int qtdmaxobras;

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

    public int getQtdmaxobras() {  return this.qtdmaxobras; }

    public void setQtdmaxobras(int qtdmaxobras) { this.qtdmaxobras = qtdmaxobras; }
}
