package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "livrocd", schema = "public")
public class LivroCd {
    private String cota;
    private String isbn;
    private String editora;
    private String edicao;
    private int volume;
    private String codigobarra;
    private String descricaocd;
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
    @Column(name = "volume")
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
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
    @Column(name = "descricaocd")
    public String getDescricaocd() { return descricaocd; }

    public void setDescricaocd(String descricaocd) {
        this.descricaocd = descricaocd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LivroCd livro = (LivroCd) o;
        return Objects.equals(cota, livro.cota) &&
                Objects.equals(isbn, livro.isbn) &&
                Objects.equals(editora, livro.editora) &&
                Objects.equals(edicao, livro.edicao) &&
                Objects.equals(volume, livro.volume) &&
                Objects.equals(codigobarra, livro.codigobarra) &&
                Objects.equals(descricaocd, livro.descricaocd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cota, isbn, editora, edicao,volume, codigobarra,descricaocd);
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
