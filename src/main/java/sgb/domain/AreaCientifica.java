package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "areacientifica", schema = "public")
public class AreaCientifica {
    private int idarea;
    private String descricao;

    @Id
    @Column(name = "idarea")
    public int getIdarea() {
        return idarea;
    }

    public void setIdarea(int idarea) {
        this.idarea = idarea;
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
        AreaCientifica that = (AreaCientifica) o;
        return idarea == that.idarea &&
                Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idarea, descricao);
    }

}
