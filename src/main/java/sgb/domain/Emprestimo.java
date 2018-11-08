package sgb.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@IdClass(EmprestimoPK.class)
public class Emprestimo {
    private Obra obra;
    private Users user;
    private Date dataentrada;
    private Date dataaprovacao;
    private Date datadevolucao;
    private String quantidade;
    private String comentario;


    @Id
    @Column(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Id
    @Column(name = "cota", nullable = false, length = 255)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cota", nullable = false)
    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    @Basic
    @Column(name = "dataentrada", nullable = true)
    public Date getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(Date dataentrada) {
        this.dataentrada = dataentrada;
    }

    @Basic
    @Column(name = "dataaprovacao", nullable = true)
    public Date getDataaprovacao() {
        return dataaprovacao;
    }

    public void setDataaprovacao(Date dataaprovacao) {
        this.dataaprovacao = dataaprovacao;
    }

    @Basic
    @Column(name = "datadevolucao", nullable = true)
    public Date getDatadevolucao() {
        return datadevolucao;
    }

    public void setDatadevolucao(Date datadevolucao) {
        this.datadevolucao = datadevolucao;
    }

    @Basic
    @Column(name = "quantidade", nullable = true, length = 255)
    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Basic
    @Column(name = "comentario", nullable = true, length = 5000)
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }



}
