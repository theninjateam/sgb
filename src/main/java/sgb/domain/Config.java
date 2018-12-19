package sgb.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "config", schema = "public")

public class Config
{
    @Id
    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private String valor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
