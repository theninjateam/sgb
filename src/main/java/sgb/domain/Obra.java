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
@Table(name = "obra")
public class Obra {
    private String cota;
    private Integer registro;
    private String titulo;
    private String localpublicacao;
    private Date datapublicacao;
    private Integer quantidade;
    private AreaCientifica areacientifica;
    private TipoObra tipoobra;
    private Set<Autor> autores ;
    private Idioma idioma;
    private Livro livro;




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


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ididioma", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idtipo", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public TipoObra getTipoobra() {
        return tipoobra;
    }

    public void setTipoobra(TipoObra tipoobra) {
        this.tipoobra = tipoobra;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idarea", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public AreaCientifica getAreacientifica() {
        return areacientifica;
    }

    public void setAreacientifica(AreaCientifica areacientifica) {
        this.areacientifica = areacientifica;
    }

    @OneToMany
    @JoinTable(name="obra_autor", joinColumns = @JoinColumn(name ="cota"),
            inverseJoinColumns = @JoinColumn(name="idautor"))
    public Set<Autor> getAutores(){
        return this.autores;
    }
    public void setAutores(Set<Autor> autores) {
        this.autores=autores;
    }



    @OneToOne(mappedBy = "obra")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }


}