package sgb.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "estadomulta")
public class EstadoMulta {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idestadomulta")
    private int idestadomulta;

    public int getIdestadomulta() {
        return idestadomulta;
    }

    public void setIdestadomulta(int idestadomulta) {
        this.idestadomulta = idestadomulta;
    }

    @Basic
    @Column(name = "descricao")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


}
