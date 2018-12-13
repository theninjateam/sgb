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
import sgb.service.CRUDService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class ConfigTest
{
    @Autowired
    private ApplicationContext context;
    private CRUDService crudService;

    @Before
    public void before() throws Exception
    {
        this.crudService = (CRUDService) context.getBean("CRUDService");
    }

    @Test
    @Transactional
    public void getQuantidadeMinimaDeExemplares() throws Exception
    {
        Config config = this.crudService.get(Config.class, "QMDE");
        assertThat(Integer.parseInt(config.getValor()) ).isEqualTo(2);
    }

    @Test
    @Transactional
    public void getTempoMaximo() throws Exception
    {
        Config config = this.crudService.get(Config.class, "TM");
        assertThat(Integer.parseInt(config.getValor())).isEqualTo(60);
    }
}
