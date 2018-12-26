package sgb.domain;


import javax.persistence.*;
@Entity
@Table(name = "tiporequisicao", schema = "public")

public class TipoRequisicao
{
    private int idTipoRequisicao;
    private String descricao;


    @Id
    @Column(name = "idtiporequisicao")
    public int getIdTipoRequisicao()
    {
        return idTipoRequisicao;
    }

    public void setIdTipoRequisicao(int idTipoRequisicao)
    {
        this.idTipoRequisicao = idTipoRequisicao;
    }

    @Basic
    @Column(name = "descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
}
