package sgb.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Estadopedido {
    private int idestadopedido;
    private String descricao;

    @Id
    @Column(name = "idestadopedido", nullable = false)
    public int getIdestadopedido() {
        return idestadopedido;
    }

    public void setIdestadopedido(int idestadopedido) {
        this.idestadopedido = idestadopedido;
    }

    @Basic
    @Column(name = "descricao ", nullable = true, length = 255)
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estadopedido that = (Estadopedido) o;
        return idestadopedido == that.idestadopedido &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idestadopedido, descricao);
    }
}
