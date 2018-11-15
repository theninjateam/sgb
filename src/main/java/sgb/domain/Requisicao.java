package sgb.domain;

import java.util.ArrayList;

public class Requisicao
{
    private Obra obra;
    private ArrayList<Range> rangeQtd = new ArrayList<Range>();
    private int currentQtd;

    public Obra getObra() {
        return this.obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }

    public int getCurrentQtd() {  return this.currentQtd; }

    public void setCurrentQtd(int currentQtd) { this.currentQtd = currentQtd; }

    public void setRangeQtd(int qtd)
    {
        for (int i  = 1; i <= qtd; i++)
        {
            Range range = new Range();
            range.setNum(i);
            range.setCurrentQtd(currentQtd);
            rangeQtd.add(range);
        }
    }

    public ArrayList<Range> getRangeQtd()
    {
        return this.rangeQtd;
    }
}
