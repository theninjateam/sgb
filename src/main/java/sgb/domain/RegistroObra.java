package sgb.domain;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "registroobra", schema = "public")
public class RegistroObra {
    private String cota;
    private String bibliotecario;
    private Calendar dataRegistro;
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
    @Column(name = "bibliotecario")
    public String getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(String bibliotecario)
    {
        this.bibliotecario = bibliotecario;
    }

    @Basic
    @Column(name = "dataregisto")
    public Calendar getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Calendar dataRegistro)
    {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, bibliotecario, dataRegistro);
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cota", nullable = false)

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }
}
