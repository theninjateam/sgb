package sgb.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Calendar;

@Entity
@Table(name = "multa", schema = "public")
public class Multa {
    @EmbeddedId
    private EmprestimoPK multaPK;
    private Integer diasatraso;
    private Float valorpago;
    private Calendar dataemprestimo;
    private Calendar dataemissao;
    private Float taxadiaria;
    private boolean notificacao;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idestadomulta", nullable = false)
    private EstadoMulta estadoMulta;
    public EstadoMulta getEstadoMulta() { return estadoMulta; }
    public void setEstadoMulta(EstadoMulta estadoMulta) {this.estadoMulta = estadoMulta; }

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.PERSIST)
    @JoinColumn(name="bibliotecario", nullable = false)
    private Users bibliotecario;

    public Users getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Users bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public EmprestimoPK getMultaPK() {
        return multaPK;
    }

    public void setMultaPK(EmprestimoPK multaPK) {
        this.multaPK = multaPK;
    }


    @Basic
    @Column(name = "diasatraso")
    public Integer getDiasatraso() {
        return diasatraso;
    }

    public void setDiasatraso(Integer diasatraso) {
        this.diasatraso = diasatraso;
    }

    @Basic
    @Column(name = "valorpago")
    public Float getValorpago() {
        return valorpago;
    }

    public void setValorpago(Float valorpago) {
        this.valorpago = valorpago;
    }

    @Basic
    @Column(name = "dataemprestimo")
    public Calendar getDataemprestimo() {
        return dataemprestimo;
    }

    public void setDataemprestimo(Calendar dataemprestimo) {
        this.dataemprestimo = dataemprestimo;
    }

    @Column(name = "dataemissao")
    public Calendar getDataemissao() {
        return dataemissao;
    }

    public void setDataemissao(Calendar dataemissao) {
        this.dataemissao = dataemissao;
    }

    @Basic
    @Column(name = "taxadiaria")

    public Float getTaxadiaria() {
        return taxadiaria;
    }

    public void setTaxadiaria(Float taxadiaria) {
        this.taxadiaria = taxadiaria;
    }


    @Basic
    @Column (name = "notificacao")
    public boolean getNotificacao(){ return notificacao; }

    public void setNotificacao(boolean notificacao) { this.notificacao = notificacao; }

}
