package sgb.domain;

import java.util.ArrayList;

public class Requisicao
{
    private Obra obra;
    private ArrayList<Integer> rangeQtd = new ArrayList<Integer>();

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public void setRangeQtd(int qtd)
    {
        for (int i  = 1; i <= qtd; i++)
            rangeQtd.add(i);

        rangeQtd.remove(0);
    }

    public ArrayList<Integer> getRangeQtd()
    {
        return this.rangeQtd;
    }

}