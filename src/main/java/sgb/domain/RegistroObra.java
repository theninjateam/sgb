package sgb.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class RegistroObra {
    private String cota;
    private int iduser;
    private Calendar dataRegisto;
    private Obra obra;

    @Id
    @Column(name = "cota")
    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    @Column(name = "iduser")
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "dataregisto")
    public Calendar getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Calendar dataregisto) {
        this.dataRegisto = dataregisto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroObra that = (RegistroObra) o;
        return iduser == that.iduser &&
                Objects.equals(cota, that.cota) &&
                Objects.equals(dataRegisto, that.dataRegisto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, iduser, dataRegisto);
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cota")

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }
}
