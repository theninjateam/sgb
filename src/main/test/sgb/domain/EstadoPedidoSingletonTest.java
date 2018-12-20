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
import sgb.controller.domainController.EstadoPedidoSingleton;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class EstadoPedidoSingletonTest
{
    @Autowired
    private ApplicationContext context;
    private EstadoPedidoSingleton ePSingleton;

    @Before
    public void before() throws Exception
    {
        this.ePSingleton = (EstadoPedidoSingleton) this.context.getBean("estadoPedidoSingleton");
    }

    @Test
    @Transactional
    public void getValueTest()
    {
        assertThat(1).isEqualTo(this.ePSingleton.PENDING);
        assertThat(2).isEqualTo(this.ePSingleton.REJECTED);
        assertThat(3).isEqualTo(this.ePSingleton.ACCEPTED);
        assertThat(4).isEqualTo(this.ePSingleton.IN_QUEUE);
        assertThat(5).isEqualTo(this.ePSingleton.CANCELED);
    }


}
