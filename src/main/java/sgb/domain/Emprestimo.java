package sgb.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "emprestimo", schema = "public")
public class Emprestimo {

    @EmbeddedId
    private EmprestimoPK emprestimoPK;

    private Date dataentrada;
    private Date dataaprovacao;
    private Date datadevolucao;
    private String quantidade;
    private String comentario;

    public EmprestimoPK getEmprestimoPK() {
        return this.emprestimoPK;
    }

    public void setEmprestimoPK(EmprestimoPK emprestimoPK) {
        this.emprestimoPK = emprestimoPK;
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
