package sgb.controller.domainController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.domain.Config;
import sgb.service.CRUDService;

/**
 * @author Fonseca
 *
 *@Description: We use Singleton because there are several classes that use methods present in
 * this class to verify business rule, so we decide that, the instace of that class can be shared.
 */

public class EmprestimoRuleSingleton
{
    private CRUDService CRUDService;

    public final int MINIMUM_NUMBER_OF_COPIES;

    public EmprestimoRuleSingleton(CRUDService crudService)
    {
        this.CRUDService = crudService;

        MINIMUM_NUMBER_OF_COPIES = Integer.parseInt(getConfigValue("MINIMUM_NUMBER_OF_COPIES")) ;
    }

    public String getConfigValue(String id)
    {
        return this.CRUDService.get(Config.class, id).getValor();
    }

}
