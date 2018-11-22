package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "estadopedido", schema = "public")

public class EstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idestadopedido", nullable = false)
    private int idestadopedido;

    @Basic
    @Column(name = "descricao ", nullable = true, length = 255)
    private String descricao;

    public int getIdestadopedido() {
        return idestadopedido;
    }

    public void setIdestadopedido(int idestadopedido) {
        this.idestadopedido = idestadopedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
