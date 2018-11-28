package sgb.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "registroobra", schema = "public")
public class RegistroObra
{
    @EmbeddedId
    private RegistroObraPK registroObraPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formaaquisicao", nullable = false)
    private FormaAquisicao formaAquisicao;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Basic
    @Column( name = "observacao")
    private String observacao;


    @ManyToOne
    @JoinColumn (name ="cota", insertable = false, updatable = false)
    private Obra obra;

    public RegistroObraPK getRegistroObraPK()
    {
        return registroObraPK;
    }

    public void setRegistroObraPK(RegistroObraPK registroObraPK)
    {
        this.registroObraPK = registroObraPK;
    }

    public FormaAquisicao getFormaAquisicao()
    {
        return formaAquisicao;
    }

    public void setFormaAquisicao(FormaAquisicao formaAquisicao)
    {
        this.formaAquisicao = formaAquisicao;
    }

    public String getObservacao()
    {
        return observacao;
    }

    public void setObservacao(String observacao)
    {
        this.observacao = observacao;
    }

    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }

    public Obra getObra() {
        return obra;
    }
    public void setObra(Obra obra) {
        this.obra = obra;
    }
}
