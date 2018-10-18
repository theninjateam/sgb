package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Cd {
    private String idcd;
    private String descricao;
    private Obra obra;


    @Id
    @Column(name = "cota")
    public String getIdcd() {
        return idcd;
    }

    public void setIdcd(String idcd) {
        this.idcd = idcd;
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
        Cd cd = (Cd) o;
        return Objects.equals(idcd, cd.idcd) &&
                Objects.equals(descricao, cd.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcd, descricao);
    }

    @OneToOne
    @JoinColumn(name = "cota")

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

}
