package sgb.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Idioma {
    private int ididioma;
    private String descricao;

    @Id
    @Column(name = "ididioma")
    public int getIdidioma() {
        return ididioma;
    }

    public void setIdidioma(int ididioma) {
        this.ididioma = ididioma;
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
        Idioma idioma = (Idioma) o;
        return ididioma == idioma.ididioma &&
                Objects.equals(descricao, idioma.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ididioma, descricao);
    }



}
