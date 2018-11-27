package sgb.controller.domainController;

import sgb.service.CRUDService;

/**
 * @author Fonseca
 *
 *@Description: We use Singleton because there are several classes that use methods present in
 * this class to verify business rule, so we decide that, the instace of that class can be shared.
 */

public class EmprestimoRuleSingleton
{
    private static EmprestimoRuleSingleton instace = null;
    private CRUDService crudService;

    private EmprestimoRuleSingleton(){}
    private EmprestimoRuleSingleton(CRUDService crudService)
    {
        this.crudService = crudService;
    }


    public EmprestimoRuleSingleton getInstace(CRUDService crudService)
    {
        if (instace == null)
        {
            synchronized (EmprestimoRuleSingleton.class)
            {
                if ( instace == null)
                {
                    instace = new EmprestimoRuleSingleton(crudService);
                }
            }
        }

        return instace;
    }



}
