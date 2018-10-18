package sgb.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Autor {
    private int idautor;
    private String nome;
    private String apelido;

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
