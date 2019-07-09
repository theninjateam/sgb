package sgb.controller.domainController;

import sgb.domain.EstadoPedido;
import sgb.domain.EstadoRenovacao;
import sgb.service.CRUDService;

import java.util.HashMap;

public class EstadoRenovacaoControler {

    private sgb.service.CRUDService CRUDService;
    private HashMap<String, Object> parameters;
    private StringBuilder query;

    public final int RENOVADO;
    public final int INDEFERIDO;

    public EstadoRenovacaoControler(CRUDService crudService)
    {
        this.CRUDService = crudService;
        RENOVADO = getValue("RENOVADO");
        INDEFERIDO = getValue("INDEFERIDO");
    }

    public int getValue(String description)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("description", description);
        query.append("SELECT e FROM EstadoRenovacao e WHERE e.descricao = :description");

        return  ((EstadoRenovacao) this.CRUDService.findEntByJPQueryT(query.toString(), parameters)).getIdestadorenovacao();    }

    public EstadoRenovacao getEstadoRenovacao(int idEstadoRenovacao)
    {
        return this.CRUDService.get(EstadoRenovacao.class,idEstadoRenovacao);
    }

}
