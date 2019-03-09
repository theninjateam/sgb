package sgb.controller.domainController;

import sgb.domain.Config;
import sgb.service.CRUDService;

/**
 *@author Fonseca, bfonseca@unilurio.ac.mz
 *@Description este singleton recupera dados da entidade Config
 **/

public class ConfigSingleton
{
    private CRUDService CRUDService;

    /**
     * @Description
     *
     * Para cada tupla da tabela config deve existir uma contante inteira, tal que:
     * 1. o nome da constante de ser igual ao valor do atributo chave da tupla.
     * 2. o valor atribuido a constante deve ser igual ao valor do atributo "valor" da tupla.
     *
     *Se o programador quiser aceder o valor do atributo "valor" de uma das tuplas,
     *recomenda-se que faca o uso das constantes.
     * */

    public final int MINIMUM_NUMBER_OF_COPIES;

    public final int DEADLINE_REQUESTED_BOOKS;

    public final int ENTRY_TIME_ON_SATURDAY;

    public final int EXIT_TIME_ON_SATURDAY;

    public final int ENTRY_TIME_ON_WEEKDAYS;

    public final int EXIT_TIME_ON_WEEKDAYS;

    public final int DEADLINE_RESERVED_BOOKS;

    public ConfigSingleton(CRUDService crudService)
    {
        this.CRUDService = crudService;

        MINIMUM_NUMBER_OF_COPIES = Integer.parseInt(getConfigValue("MINIMUM_NUMBER_OF_COPIES"));

        DEADLINE_REQUESTED_BOOKS = Integer.parseInt(getConfigValue("DEADLINE_REQUESTED_BOOKS"));

        ENTRY_TIME_ON_SATURDAY = Integer.parseInt(getConfigValue("ENTRY_TIME_ON_SATURDAY"));

        EXIT_TIME_ON_SATURDAY = Integer.parseInt(getConfigValue("EXIT_TIME_ON_SATURDAY"));

        ENTRY_TIME_ON_WEEKDAYS = Integer.parseInt(getConfigValue("ENTRY_TIME_ON_WEEKDAYS"));

        EXIT_TIME_ON_WEEKDAYS = Integer.parseInt(getConfigValue("EXIT_TIME_ON_WEEKDAYS"));

        DEADLINE_RESERVED_BOOKS = Integer.parseInt(getConfigValue("DEADLINE_RESERVED_BOOKS"));
    }

    public String getConfigValue(String id)
    {
        return this.CRUDService.get(Config.class, id).getValor();
    }
}
