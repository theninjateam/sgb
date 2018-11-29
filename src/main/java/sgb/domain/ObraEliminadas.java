package sgb.domain;

import javax.persistence.*;

@Entity
@Table(name = "obraeliminadas")
public class ObraEliminadas {

    @EmbeddedId
    private ObraEliminadasPK obraEliminadasPK;

    private String descricao;
    private Integer quantidade;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;


    @ManyToOne
    @JoinColumn (name ="cota", insertable = false, updatable = false)
    private Obra obra;

    @Basic
    @Column(name = "descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Basic
    @Column(name = "quantidade")
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ObraEliminadasPK getObraEliminadasPK() {
        return obraEliminadasPK;
    }

    public void setObraEliminadasPK(ObraEliminadasPK obraEliminadasPK) {
        this.obraEliminadasPK = obraEliminadasPK;
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
