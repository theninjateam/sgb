package sgb.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Embeddable
public class EmprestimoPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cota")
    private Obra obra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;

    @Column(name = "dataentrada", nullable = true)
    private Calendar dataentrada;

    public Calendar getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(Calendar dataentrada) {
        this.dataentrada = dataentrada;
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

    @Override
    public String toString() {
        return "EmprestimoPK{" +
                "obra=" + obra.getCota() +
                ", user=" + user.getId() +
                ", dataentrada=" + dataentrada.getTime().toString() +
                '}';
    }
}
