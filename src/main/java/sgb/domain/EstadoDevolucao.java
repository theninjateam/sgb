package sgb.domain;

import javax.persistence.*;

@Entity
@Table(name = "estadodevolucao", schema = "public")
public class EstadoDevolucao
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idestadodevolucao", nullable = false)
    private int idestadodevolucao;

    @Basic
    @Column(name = "descricao", nullable = true)
    private String descricao;


    public int getIdestadodevolucao()
    {
        return this.idestadodevolucao;
    }

    public void setIdestadodevolucao(int idestadodevolucao)
    {
        this.idestadodevolucao = idestadodevolucao;
    }

    public String getDescricao()
    {
        return this.descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
}
