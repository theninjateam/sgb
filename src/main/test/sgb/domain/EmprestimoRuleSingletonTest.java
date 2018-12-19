package sgb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.EmprestimoRuleSingleton;
import sgb.service.CRUDService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class EmprestimoRuleSingletonTest
{
    @Autowired
    private ApplicationContext context;
    private CRUDService crudService;
    private EmprestimoRuleSingleton eRSingleton;

    @Before
    public void before() throws Exception
    {
        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.eRSingleton = (EmprestimoRuleSingleton) context.getBean("emprestimoRuleSingleton");
    }

    @Test
    @Transactional
    public void getQuantidadeMinimaDeExemplares() throws Exception
    {
        assertThat(eRSingleton.MINIMUM_NUMBER_OF_COPIES).isEqualTo(2);
    }

    @Test
    @Transactional
    public void getTempoMaximo() throws Exception
    {
      //  assertThat(Integer.parseInt(config.getValor())).isEqualTo(60);
    }
}
