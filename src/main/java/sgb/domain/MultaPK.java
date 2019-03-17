package sgb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import  java.util.Calendar;

@Embeddable
public class MultaPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cota")
    private Obra obra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="utente")
    private Users user;

    private Calendar dataentradapedido;

    @Basic
    @Column(name = "dataentradapedido")

    public Calendar getDataentradapedido() {
        return dataentradapedido;
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


    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

}
