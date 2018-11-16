package sgb.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "emprestimo", schema = "public")
public class Emprestimo {

    @EmbeddedId
    private EmprestimoPK emprestimoPK;
    private Calendar dataentrada;
    private Calendar dataaprovacao;
    private Calendar datadevolucao;
    private int quantidade;
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "estadopedido",
            referencedColumnName = "idestadopedido", nullable = false)
    private EstadoPedido estadoPedido;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "estadodevolucao",
            referencedColumnName = "idestadodevolucao", nullable = false)
    private EstadoDevolucao estadoDevolucao;


    public EmprestimoPK getEmprestimoPK() {
        return this.emprestimoPK;
    }

    public void setEmprestimoPK(EmprestimoPK emprestimoPK) {
        this.emprestimoPK = emprestimoPK;
    }

    @Basic
    @Column(name = "dataentrada", nullable = true)
    public Calendar getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(Calendar dataentrada) {
        this.dataentrada = dataentrada;
    }

    @Basic
    @Column(name = "dataaprovacao", nullable = true)
    public Calendar getDataaprovacao() {
        return dataaprovacao;
    }

    public void setDataaprovacao(Calendar dataaprovacao) {
        this.dataaprovacao = dataaprovacao;
    }

    @Basic
    @Column(name = "datadevolucao", nullable = true)
    public Calendar getDatadevolucao() {
        return datadevolucao;
    }

    public void setDatadevolucao(Calendar datadevolucao) {
        this.datadevolucao = datadevolucao;
    }

    @Basic
    @Column(name = "quantidade", nullable = true, length = 255)
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
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


    public EstadoPedido getEstadoPedido() {
        return this.estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }


    public EstadoDevolucao getEstadoDevolucao() { return this.estadoDevolucao; }

    public void setEstadoDevolucao(EstadoDevolucao estadoDevolucao)
    {
        this.estadoDevolucao = estadoDevolucao;
    }
}
