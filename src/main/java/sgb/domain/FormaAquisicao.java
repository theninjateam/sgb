package sgb.domain;

import javax.persistence.*;

@Entity
@Table(name = "formaaquisicao", schema = "public")
public class FormaAquisicao
{
    @Id
    @Column(name = "formaaquisicao")
    private int formaaquisicao;

    @Basic
    @Column(name = "descricao")
    private String descricao;

    public int getFormaaquisicao() { return formaaquisicao; }

    public void setFormaaquisicao(int formaaquisicao) { this.formaaquisicao = formaaquisicao;}

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
}
