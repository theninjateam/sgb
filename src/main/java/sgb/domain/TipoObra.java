package sgb.domain;

import javax.persistence.*;

@Entity
@Table(name = "tipoobra", schema = "public")

public class TipoObra
{
    @Id
    @Column(name = "idTipo")
    private short idTipo;
    @Column(name = "descricao")
    private String descricao;

    public TipoObra() {}

    public short getIdTipo() {
        return idTipo;
    }
    public void setIdTipo(short idTipo) {  this.idTipo = idTipo; }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
