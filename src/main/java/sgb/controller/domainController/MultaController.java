package sgb.controller.domainController;

import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;

public class MultaController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public MultaController(CRUDService crudService)
    {
        this.crudService = crudService;
    }

    public Multa getFine(EmprestimoPK emprestimoPK)
    {
        parameters = new HashMap<String, Object>(3);
        query = new StringBuilder();

        parameters.put("user_id", emprestimoPK.getUtente().getId());
        parameters.put("cota", emprestimoPK.getObra().getCota());
        parameters.put("dataentrada", emprestimoPK.getDataentradapedido());


        query.append("SELECT m FROM Multa m WHERE m.emprestimoPK.utente.id = :user_id and ");
        query.append("m.emprestimoPK.obra.cota = :cota and m.emprestimoPK.dataentradapedido = :dataentrada");

        return this.crudService.findEntByJPQueryT(query.toString(), parameters);
    }
}
