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


        query.append("SELECT m FROM Multa m WHERE m.multaPK.utente.id = :user_id and ");
        query.append("m.multaPK.obra.cota = :cota and m.multaPK.dataentradapedido = :dataentrada");

        return this.crudService.findEntByJPQueryT(query.toString(), parameters);
    }


    public List<Multa> getFine(Users user , int idEstadoMulta)
    {
        parameters = new HashMap<String, Object>(2);
        query = new StringBuilder();

        parameters.put("user_id", user.getId());
        parameters.put("idEstadoMulta", idEstadoMulta);


        query.append("SELECT m FROM Multa m WHERE m.multaPK.utente.id = :user_id and ");
        query.append("m.estadoMulta.idestadomulta = :idEstadoMulta");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public List<Multa> getFine(int idEstadoMulta)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("idEstadoMulta", idEstadoMulta);

        query.append("SELECT m FROM Multa m WHERE m.estadoMulta.idestadomulta = :idEstadoMulta");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

}
