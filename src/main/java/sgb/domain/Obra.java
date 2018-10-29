package sgb.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "obra", schema = "public")
public class Obra {
    private String cota;
    private Integer registro;
    private String titulo;
    private String localpublicacao;
    private Date datapublicacao;
    private Integer quantidade;
    private AreaCientifica areacientifica;
    private TipoObra tipoobra;
    private Set<Autor> autores = new HashSet<>();;
    private Idioma idioma;
    private Livro livro;
    private Cd cd;
    private Revista revista;
    private RegistroObra registroObra;

    @Id
    @Column(name = "cota")
    public String getCota() {
        return cota;
    }
    public void setCota(String cota) {
        this.cota = cota;
    }


    @Basic
    @Column(name = "registro")
    public Integer getRegistro() {
        return registro;
    }
    public void setRegistro(Integer registro) {
        this.registro = registro;
    }

    @Basic
    @Column(name = "titulo")
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    @Basic
    @Column(name = "localpublicacao")
    public String getLocalpublicacao() {
        return localpublicacao;
    }
    public void setLocalpublicacao(String localpublicacao) {
        this.localpublicacao = localpublicacao;
    }

    @Basic
    @Column(name = "datapublicacao")
    public Date getDatapublicacao() {
        return datapublicacao;
    }
    public void setDatapublicacao(Date datapublicacao) {
        this.datapublicacao = datapublicacao;
    }

    @Basic
    @Column(name = "quantidade")
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Obra obra = (Obra) o;
//        return Objects.equals(cota, obra.cota) &&
//                Objects.equals(registro, obra.registro) &&
//                Objects.equals(titulo, obra.titulo) &&
//                Objects.equals(idarea, obra.idarea) &&
//                Objects.equals(localpublicacao, obra.localpublicacao) &&
//                Objects.equals(datapublicacao, obra.datapublicacao) &&
////                Objects.equals(ididioma, obra.ididioma) &&
//                Objects.equals(quantidade, obra.quantidade) &&
//                Objects.equals(idtipo, obra.idtipo);
//    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, registro, titulo, localpublicacao, datapublicacao,  quantidade);
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ididioma", nullable = false)
    public Idioma getIdioma() {
        return idioma;
    }
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idtipo", nullable = false)
    public TipoObra getTipoobra() {
        return tipoobra;
    }
    public void setTipoobra(TipoObra tipoobra) {
        this.tipoobra = tipoobra;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idarea", nullable = false)
    public AreaCientifica getAreacientifica() {
        return areacientifica;
    }

    public void setAreacientifica(AreaCientifica areacientifica) {
        this.areacientifica = areacientifica;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
            )
    @JoinTable(name="obra_autor", joinColumns = @JoinColumn(name ="cota", nullable = false),
            inverseJoinColumns = @JoinColumn(name="hashcode", nullable = false))
    public Set<Autor> getAutores(){
        return this.autores;
    }
    public void setAutores(Set<Autor> autores) {
        this.autores=autores;
    }


    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "obra")

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "obra")

    public Cd getCd() {
        return cd;
    }

    public void setCd(Cd cd) {
        this.cd = cd;
    }

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "obra")

    public Revista getRevista(){
        return revista;
    }

    public void setRevista(Revista revista) {
        this.revista = revista;
    }

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "obra")

    public RegistroObra getRegistroObra() {
        return registroObra;
    }

    public void setRegistroObra(RegistroObra registroObra) {
        this.registroObra = registroObra;
    }

}