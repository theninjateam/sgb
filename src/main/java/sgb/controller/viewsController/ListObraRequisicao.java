package sgb.controller.viewsController;


import org.zkoss.zul.ListModelList;
import sgb.domain.Emprestimo;
import sgb.domain.Users;

public interface ListObraRequisicao
{
    public ListModelList<Emprestimo> getRequisicoesPendentes(Users user);
    public ListModelList<Emprestimo> getRequisicoesRejeitadas(Users user);
    public ListModelList<Emprestimo> getRequisicoesAceites(Users user);
    public ListModelList<Emprestimo> getTodasRequisicoes(Users user);
    public ListModelList<Emprestimo> getObrasNaoDevolvidas(Users user);
    public ListModelList<Emprestimo> getObrasDevolvidas(Users user);

    /* devolve quantida de bras requisitadas pendentes*/
    public int getQtdObrasRequiPendentes(Users user);
}
