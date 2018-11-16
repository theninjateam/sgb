package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "livro", schema = "public")
public class Livro {
    private String cota;
    private String isbn;
    private String editora;
    private String edicao;
    private String codigobarra;
    private Integer volume;
    private Obra obra;

    @Id
    @Column(name = "cota")
    public String getCota() {
        return cota;
    }

    public void setCota(String cota) {
        this.cota = cota;
    }

    @Basic
    @Column(name = "isbn")
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "editora")
    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    @Basic
    @Column(name = "edicao")
    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    @Basic
    @Column(name = "codigobarra")
    public String getCodigobarra() {
        return codigobarra;
    }

    public void setCodigobarra(String codigobarra) {
        this.codigobarra = codigobarra;
    }

    @Basic
    @Column(name = "volume")
    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(cota, livro.cota) &&
                Objects.equals(isbn, livro.isbn) &&
                Objects.equals(editora, livro.editora) &&
                Objects.equals(edicao, livro.edicao) &&
                Objects.equals(codigobarra, livro.codigobarra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, isbn, editora, edicao, codigobarra);
    }

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cota", nullable = false)

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }
}
