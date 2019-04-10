package sgb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Embeddable
public class RegistroObraPK implements Serializable
{
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cota", nullable = false)
    private Obra obra;

    @Column(name = "dataregisto")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataRegisto;

    public Obra getObra() {
        return obra;
    }
    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Calendar getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(Calendar dataregisto) {
        this.dataRegisto = dataregisto;
    }
}
