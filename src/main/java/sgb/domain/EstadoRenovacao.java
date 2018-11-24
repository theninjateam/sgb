package sgb.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class EstadoRenovacao {
    private int idestadorenovacao;
    private String descricao;

    @Id
    @Column(name = "idestadorenovacao")
    public int getIdestadorenovacao() {
        return idestadorenovacao;
    }

    public void setIdestadorenovacao(int idestadorenovacao) {
        this.idestadorenovacao = idestadorenovacao;
    }

    @Basic
    @Column(name = "descricao")
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
        EstadoRenovacao that = (EstadoRenovacao) o;
        return idestadorenovacao == that.idestadorenovacao &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idestadorenovacao, descricao);
    }
}
