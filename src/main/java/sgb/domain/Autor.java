package sgb.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "autor", schema = "public")
public class Autor {
    private int idautor;
    private String nome;
    private String apelido;
    private Set<Obra> obras = new HashSet<Obra>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idautor")
    public int getIdautor() {
        return idautor;
    }

    public void setIdautor(int idautor) {
        this.idautor = idautor;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    @Column(name = "apelido")
    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "autores")

    public Set<Obra> getObras() {
        return obras;
    }

    public void setObras(Set<Obra> obras) {
        this.obras = obras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return idautor == autor.idautor &&
                Objects.equals(nome, autor.nome) &&
                Objects.equals(apelido, autor.apelido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idautor, nome, apelido);
    }
}
