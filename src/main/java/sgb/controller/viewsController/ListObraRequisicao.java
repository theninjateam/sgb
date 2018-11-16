package sgb.controller.viewsController;


import org.zkoss.zul.ListModelList;
import sgb.domain.Emprestimo;
import sgb.domain.Users;

/**
 * @author Fonseca
 */

public interface ListObraRequisicao
{
    public ListModelList<Emprestimo> getRequisicoesPendentes(Users user);
    public ListModelList<Emprestimo> getObrasNaoDevolvidas(Users user);

    /**
     * Requisicoes pendentes eh equivalente a pedidos de emprestimo
     * */
    public ListModelList<Emprestimo> getTodasRequisicoesPendentes();

    /**
     * Devolve a quantidade de obras requisitadas por um usuario,
     *  cujo estadoPedido eh pendente
     */
    public int getQtdObrasReqPendentes(Users user);
}
