package sgb.controller.viewsModel;

import java.util.ArrayList;
import java.util.List;
import sgb.domain.Requisicao;

public class CestaViewModel
{
    private List<Requisicao> requisicoes = new ArrayList<Requisicao>();

    public List<Requisicao> getRequisicoes() {
        return this.requisicoes;
    }

    public void setRequisicoes(List<Requisicao> requisicoes) {
        this.requisicoes = requisicoes;
    }
}
