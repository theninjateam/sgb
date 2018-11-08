package sgb.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Estadodevolucao {
    private int idestadodevolucao;
    private String descricao;

    @Id
    @Column(name = "idestadodevolucao", nullable = false)
    public int getIdestadodevolucao() {
        return idestadodevolucao;
    }

    public void setIdestadodevolucao(int idestadodevolucao) {
        this.idestadodevolucao = idestadodevolucao;
    }

    @Basic
    @Column(name = "descricao", nullable = true, length = 255)
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
        Estadodevolucao that = (Estadodevolucao) o;
        return idestadodevolucao == that.idestadodevolucao &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idestadodevolucao, descricao);
    }
}
