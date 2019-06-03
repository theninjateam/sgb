package sgb.controller.domainController;

import sgb.domain.EstadoDevolucao;
import sgb.domain.EstadoPedido;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;

public class EstadoDevolucaoControler
{
    private CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;
    public final int INDETERMINADO;
    public final int DEVOLVIDO;
    public final int NAO_DEVOLVIDO;


    public EstadoDevolucaoControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        INDETERMINADO = getValue("INDETERMINADO");
        DEVOLVIDO = getValue("DEVOLVIDO");
        NAO_DEVOLVIDO = getValue("NAO_DEVOLVIDO");
    }

    public int getValue(String description)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("description", description);
        query.append("SELECT e FROM EstadoDevolucao e WHERE e.descricao = :description");

        return  ((EstadoDevolucao) this.CRUDService.findEntByJPQueryT(query.toString(), parameters)).getIdestadodevolucao();
    }

    public List<EstadoDevolucao> getEstadoDevolucao(){
        query = new StringBuilder();

        query.append("SELECT ed FROM EstadoDevolucao ed");

        return this.CRUDService.findByJPQuery(query.toString(), null);
    }
}