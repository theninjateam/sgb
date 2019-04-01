package sgb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;

public class ObraEliminadasPK implements Serializable {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cota", nullable = false)
    private Obra obra;

    @Column(name = "dataregisto")
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

    public void setDataRegisto(Calendar dataRegisto) {
        this.dataRegisto = dataRegisto;
    }
}
