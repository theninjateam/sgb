package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class TipoObra {
    private int idtipo;
    private String descricao;

    @Id
    @Column(name = "idtipo")
    public int getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(int idtipo) {
        this.idtipo = idtipo;
    }

    @Basic
    @Column(name = "descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) { this.descricao = descricao; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoObra tipoobra = (TipoObra) o;
        return idtipo == tipoobra.idtipo &&
                Objects.equals(descricao, tipoobra.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtipo, descricao);
    }
}
