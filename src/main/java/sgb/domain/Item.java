package sgb.domain;

public class Item
{
    private Obra obra;
    private int quantidade;
    private boolean lineUp = false;
    private boolean homeRequisition;
    private boolean canDoInternalRequisition;
    private boolean canDoHomeRequisition;
    private boolean internalRequisition;
    public boolean delete = false;


    private boolean canBeViewed = true;
    private EstadoPedido estadoPedido;

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

    public boolean isLineUp()
    {
        return lineUp;
    }

    public void setLineUp(boolean lineUp)
    {
        this.lineUp = lineUp;
    }

    public boolean isHomeRequisition()
    {
        return homeRequisition;
    }

    public void setHomeRequisition(boolean homeRequisition)
    {
        this.homeRequisition = homeRequisition;
    }

    public boolean isInternalRequisition()
    {
        return internalRequisition;
    }

    public void setInternalRequisition(boolean internalRequisition)
    {
        this.internalRequisition = internalRequisition;
    }

    public EstadoPedido getEstadoPedido()
    {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido)
    {
        this.estadoPedido = estadoPedido;
    }

    public boolean isCanBeViewed() {
        return canBeViewed;
    }

    public void setCanBeViewed(boolean canBeViewed) {
        this.canBeViewed = canBeViewed;
    }

    public boolean isCanDoInternalRequisition() {
        return canDoInternalRequisition;
    }

    public void setCanDoInternalRequisition(boolean canDoInternalRequisition) {
        this.canDoInternalRequisition = canDoInternalRequisition;
    }

    public boolean isCanDoHomeRequisition() {
        return canDoHomeRequisition;
    }

    public void setCanDoHomeRequisition(boolean canDoHomeRequisition) {
        this.canDoHomeRequisition = canDoHomeRequisition;
    }
}
