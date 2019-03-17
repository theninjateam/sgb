package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "estadomulta")
public class EstadoMulta {

    private int idestadomulta;
    private String descricao;

    @Id
    @Column(name = "idestadomulta")
    public int getIdestadomulta() {
        return idestadomulta;
    }

    public void setIdestadomulta(int idestadomulta) {
        this.idestadomulta = idestadomulta;
    }

    @Basic
    @Column(name = "descricao ")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
