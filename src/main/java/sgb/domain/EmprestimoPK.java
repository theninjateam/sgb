package sgb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Embeddable
public class EmprestimoPK implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cota")
    private Obra obra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="utente")
    private Users utente;

    @Column(name = "dataentradapedido", nullable = true)
    private Calendar dataentradapedido;

    public Calendar getDataentradapedido() {
        return this.dataentradapedido;
    }

    public void setDataentradapedido(Calendar dataentradapedido) {
        this.dataentradapedido = dataentradapedido;
    }

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public Users getUtente() {
        return this.utente;
    }

    public void setUtente(Users utente) {
        this.utente = utente;
    }
}
