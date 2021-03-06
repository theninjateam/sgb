package sgb.controller.domainController;

import org.zkoss.zkplus.spring.SpringUtil;
import sgb.domain.*;
import sgb.fine.Fine;
import sgb.service.CRUDService;

import java.util.Calendar;
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

    public List<Multa> getMultasByDate(Calendar dataI, Calendar dataF, EstadoMulta estadoMulta){
        if(estadoMulta!=null){
            parameters = new HashMap<>(3);
        }
        else{
            parameters = new HashMap<>(2);
        }

        parameters.put("datai", dataI);
        parameters.put("dataf", dataF);

        query = new StringBuilder();

        query.append("SELECT m FROM Multa m WHERE m.dataemissao >= :datai ");
        query.append("and m.dataemissao <= :dataf");
        if(estadoMulta!=null){
            parameters.put("idEstado",estadoMulta.getIdestadomulta());
            query.append(" and m.estadoMulta.idestadomulta = :idEstado");
        }

        return this.crudService.findByJPQuery(query.toString(),parameters);
    }

    public List<Multa> getByNotification(boolean notification)
    {
        parameters = new HashMap<String, Object>(1);
        query = new StringBuilder();

        parameters.put("notification", notification);

        query.append("SELECT m FROM Multa m WHERE m.notificacao = :notification");

        return this.crudService.findByJPQuery(query.toString(), parameters);
    }

    public void updateNotification (EmprestimoPK emprestimoPK,boolean notification){


        Multa multa = this.getFine(emprestimoPK);

        multa.setNotificacao(notification);

        this.crudService.update(multa);


    }

    public List<Multa> getMultas(){
        query = new StringBuilder();

        query.append("SELECT m FROM Multa m");

        return this.crudService.findByJPQuery(query.toString(),null);
    }
}