package sgb.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Autor {
    private String hashcode;
    private String nome;
    private Set<Obra> obras;

    @Id
    @Column(name = "hashcode")
    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    @Basic
    @Column(name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(hashcode, autor.hashcode) &&
                Objects.equals(nome, autor.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hashcode, nome);
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

}
