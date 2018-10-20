package sgb.domain;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "revista", schema = "public")
public class Revista {
    private String cota;
    private String instituicao;
    private Obra obra;


    @Id
    @Column(name = "cota")
    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    @Basic
    @Column(name = "instituicao")
    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revista revista = (Revista) o;
        return Objects.equals(cota, revista.cota) &&
                Objects.equals(instituicao, revista.instituicao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, instituicao);
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
