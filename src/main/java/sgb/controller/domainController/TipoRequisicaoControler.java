package sgb.controller.domainController;

import sgb.domain.EstadoMulta;
import sgb.domain.TipoRequisicao;
import sgb.service.CRUDService;

import java.util.HashMap;

public class TipoRequisicaoControler
{
    private CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    public final int REQUISICAO_INTERNA;
    public final int REQUISICAO_DOMICILIAR;


    public TipoRequisicaoControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        REQUISICAO_DOMICILIAR = getValue("REQUISICAO_DOMICILIAR");
        REQUISICAO_INTERNA = getValue("REQUISICAO_INTERNA");
    }

    public int getValue(String description)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("description", description);
        query.append("SELECT e FROM TipoRequisicao e WHERE e.descricao = :description");

        return  ((TipoRequisicao) this.CRUDService.findEntByJPQueryT(query.toString(), parameters)).getIdTipoRequisicao();
    }
}