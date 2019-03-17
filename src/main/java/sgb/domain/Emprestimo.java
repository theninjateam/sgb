package sgb.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "emprestimo", schema = "public")
public class Emprestimo  implements Comparable<Emprestimo>{

    @EmbeddedId
    private EmprestimoPK emprestimoPK;
    private Calendar dataaprovacao;
    private Calendar datadevolucao;
    private int quantidade;
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idtipoRequisicao", nullable = false)
    private TipoRequisicao tipoRequisicao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estadopedido", nullable = false)
    private EstadoPedido estadoPedido;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "estadodevolucao", nullable = false)
    private EstadoDevolucao estadoDevolucao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="estadorenovacao", nullable = false)
    private EstadoRenovacao estadoRenovacao;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bibliotecario", nullable = false)
    private Users bibliotecario;


    public EmprestimoPK getEmprestimoPK() {
        return this.emprestimoPK;
    }

    public void setEmprestimoPK(EmprestimoPK emprestimoPK) {
        this.emprestimoPK = emprestimoPK;
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

    public EstadoRenovacao getEstadoRenovacao() {
        return estadoRenovacao;
    }

    public void setEstadoRenovacao(EstadoRenovacao estadoRenovacao) {
        this.estadoRenovacao = estadoRenovacao;
    }


    public TipoRequisicao getTipoRequisicao() { return tipoRequisicao; }

    public void setTipoRequisicao(TipoRequisicao tipoRequisicao) { this.tipoRequisicao = tipoRequisicao; }

    public Users getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Users bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    @Override
    public int compareTo(Emprestimo emprestimo)
    {
        return this.getEmprestimoPK().getDataentradapedido().
                compareTo(emprestimo.getEmprestimoPK().getDataentradapedido());
    }

}
