package sgb.domain;

import java.util.ArrayList;

public class Item
{
    private Obra obra;
    private int quantidade;
    private boolean isLineUp;
    private boolean isHomeRequisition;
    private boolean canBeRequested = true;

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean getIsLineUp() {
        return isLineUp;
    }

    public void setLineUp(boolean lineUp) {
        isLineUp = lineUp;
    }

    public boolean getIsHomeRequisition() {
        return isHomeRequisition;
    }

    public void setHomeRequisition(boolean homeRequisition) {
        isHomeRequisition = homeRequisition;
    }

    public boolean getCanBeRequested() {
        return canBeRequested;
    }

    public void setCanBeRequested(boolean canBeRequested) {
        this.canBeRequested = canBeRequested;
    }
}
