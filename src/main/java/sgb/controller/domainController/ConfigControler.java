package sgb.controller.domainController;
import sgb.domain.Config;
import sgb.service.CRUDService;

import javax.swing.plaf.PanelUI;

/**
 *@author Fonseca, bfonseca@unilurio.ac.mz
 *@Description este singleton recupera dados da entidade Config
 **/
public class ConfigControler
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
    public final int DEADLINE_STUDENT_BORROWED_BOOKS;
    public final int DEADLINE_TEACHER_BORROWED_BOOKS;
    public final int DAILY_RATE_FINE;
    public final int MAXIMUM_NUMBER_OF_COPIES;
    public final int MAXIMUM_COPIES_PER_BOOK;
    public final int DAY_OF_RENEWAL;

    public ConfigControler(CRUDService crudService)
    {
        this.CRUDService = crudService;

        MINIMUM_NUMBER_OF_COPIES = Integer.parseInt(getConfigValue("MINIMUM_NUMBER_OF_COPIES"));
        DEADLINE_REQUESTED_BOOKS = Integer.parseInt(getConfigValue("DEADLINE_REQUESTED_BOOKS"));
        ENTRY_TIME_ON_SATURDAY = Integer.parseInt(getConfigValue("ENTRY_TIME_ON_SATURDAY"));
        EXIT_TIME_ON_SATURDAY = Integer.parseInt(getConfigValue("EXIT_TIME_ON_SATURDAY"));
        ENTRY_TIME_ON_WEEKDAYS = Integer.parseInt(getConfigValue("ENTRY_TIME_ON_WEEKDAYS"));
        EXIT_TIME_ON_WEEKDAYS = Integer.parseInt(getConfigValue("EXIT_TIME_ON_WEEKDAYS"));
        DEADLINE_RESERVED_BOOKS = Integer.parseInt(getConfigValue("DEADLINE_RESERVED_BOOKS"));
        DEADLINE_STUDENT_BORROWED_BOOKS = Integer.parseInt(getConfigValue("DEADLINE_STUDENT_BORROWED_BOOKS"));
        DEADLINE_TEACHER_BORROWED_BOOKS = Integer.parseInt(getConfigValue("DEADLINE_TEACHER_BORROWED_BOOKS"));
        DAILY_RATE_FINE = Integer.parseInt(getConfigValue("DAILY_RATE_FINE"));
        MAXIMUM_NUMBER_OF_COPIES = Integer.parseInt(getConfigValue("MAXIMUM_NUMBER_OF_COPIES"));
        MAXIMUM_COPIES_PER_BOOK = Integer.parseInt(getConfigValue("MAXIMUM_COPIES_PER_BOOK"));
        DAY_OF_RENEWAL = Integer.parseInt(getConfigValue("DAY_OF_RENEWAL"));
    }

    public String getConfigValue(String id)
    {
        return this.CRUDService.get(Config.class, id).getValor();
    }
}
