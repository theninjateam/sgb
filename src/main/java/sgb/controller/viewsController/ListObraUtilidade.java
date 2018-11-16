package sgb.controller.viewsController;

import org.zkoss.zul.ListModelList;
import sgb.domain.Obra;

import java.util.ArrayList;


/**
 * @author Fonseca
 */

public interface ListObraUtilidade
{
    ListModelList<Obra>  getObrasPorNome(ArrayList<String> autores);
    ListModelList<Obra>  getObrasPorCategoria(ArrayList<String> categoria);
    ListModelList<Obra>  getObrasPorTitulo(String titulo);
}
