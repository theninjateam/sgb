package sgb.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="obra")
public class Obra {
    private String cota;
    private Integer registro;
    private String titulo;
    private String localpublicacao;
    private Integer quantidade;
    private String pathpdf;
    private String pathcapa;
    private Integer anoPublicacao;
    private TipoObra tipoobra;
    private Set<Autor> autores = new HashSet<>();;
    private Idioma idioma;
    private Livro livro;
    private Cd cd;
    private Revista revista;
    private LivroCd livroCd;
    private RegistroObra registroObra;
    private AreaCientifica areacientifica;

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
    @Column(name = "quantidade")
    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    @Basic
    @Column(name = "pathpdf")
    public String getPathpdf() {
        return pathpdf;
    }


    public void setPathpdf(String pathpdf) {
        this.pathpdf = pathpdf;
    }

    @Basic
    @Column(name = "pathcapa")
    public String getPathcapa() {
        return pathcapa;
    }

    public void setPathcapa(String pathcapa) {
        this.pathcapa = pathcapa;
    }

    @Basic
    @Column(name = "anopublicacao")
    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obra obra = (Obra) o;
        return Objects.equals(cota, obra.cota) &&
                Objects.equals(registro, obra.registro) &&
                Objects.equals(titulo, obra.titulo) &&
                Objects.equals(localpublicacao, obra.localpublicacao) &&
                Objects.equals(quantidade, obra.quantidade) &&
                Objects.equals(pathpdf, obra.pathpdf) &&
                Objects.equals(pathcapa, obra.pathcapa) &&
                Objects.equals(anoPublicacao, obra.anoPublicacao) &&
                Objects.equals(autores, obra.autores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, registro, titulo, localpublicacao, quantidade, pathpdf, pathcapa, anoPublicacao);
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="obra_autor",  joinColumns = @JoinColumn(name ="cota", nullable = false),
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

    public LivroCd getLivroCd() {
        return livroCd;
    }

    public void setLivroCd(LivroCd livroCd) {
        this.livroCd = livroCd;
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
