package sgb.controller.viewsModel;

import java.util.ArrayList;
import java.util.List;
import sgb.domain.Item;

public class CestaViewModel
{
    private List<Item> requisicoes = new ArrayList<Item>();

    public List<Item> getRequisicoes() {
        return this.requisicoes;
    }

    public void setRequisicoes(List<Item> requisicoes) {
        this.requisicoes = requisicoes;
    }
}
