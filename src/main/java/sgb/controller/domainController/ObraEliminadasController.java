package sgb.controller.domainController;

import sgb.domain.AreaCientifica;
import sgb.domain.ObraEliminadas;
import sgb.service.CRUDService;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ObraEliminadasController
{
    private StringBuilder query;
    private HashMap<String, Object> parameters;
    private CRUDService crudService;

    public ObraEliminadasController(CRUDService crudService)
    {
        this.crudService = crudService;
    }

    public List<ObraEliminadas> getObrasEliminadas()
    {
        query = new StringBuilder();

        query.append("SELECT oe FROM ObraEliminadas oe");

        return this.crudService.findByJPQuery(query.toString(),null);
    }

    public List<ObraEliminadas> getObrasEliminadasByDate(Calendar dataI, Calendar dataF, AreaCientifica areaCientifica){

        if(areaCientifica != null)
            parameters = new HashMap<String, Object>(3);
        else
            parameters = new HashMap<String, Object>(2);

        query = new StringBuilder();

        parameters.put("datai", dataI);
        parameters.put("dataf", dataF);

        query.append("SELECT oe FROM ObraEliminadas oe WHERE oe.obraEliminadasPK.dataRegisto >= :datai ");
        query.append("and oe.obraEliminadasPK.dataRegisto <= :dataf");
        if(areaCientifica != null) {
            parameters.put("idarea", areaCientifica.getIdarea());
            query.append(" and oe.obraEliminadasPK.obra.areacientifica.idarea = :idarea");
        }

        return this.crudService.findByJPQuery(query.toString(),parameters);
    }
}
