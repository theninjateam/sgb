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

    public final int INTERNAL_REQUISITION;
    public final int HOME_REQUISITION;


    public TipoRequisicaoControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        HOME_REQUISITION = getValue("HOME_REQUISITION");
        INTERNAL_REQUISITION = getValue("INTERNAL_REQUISITION");
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