package sgb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;
import sgb.service.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class ObraTest
{
    private String queue[] = new String[4];

    @Autowired
    private ApplicationContext context;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");

        CRUDService crudService = (CRUDService) context.getBean("CRUDService");
        List<Emprestimo> listem = crudService.getAll(Emprestimo.class);

        for (Emprestimo e: listem)
            crudService.delete(e);

        EmprestimoPK emprestimoPK = new EmprestimoPK();
        Emprestimo emprestimo = new Emprestimo();
        Calendar ca = Calendar.getInstance();
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 21);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(crudService.get(Users.class, 3));
        emprestimoPK.setObra(crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[1] = emprestimoPK.toString();
        crudService.Save(emprestimo);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 22);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(crudService.get(Users.class, 2));
        emprestimoPK.setObra(crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[2] = emprestimoPK.toString();
        crudService.Save(emprestimo);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 19);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(crudService.get(Users.class, 4));
        emprestimoPK.setObra(crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[0] = emprestimoPK.toString();
        crudService.Save(emprestimo);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 25);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(crudService.get(Users.class, 1));
        emprestimoPK.setObra(crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[3] = emprestimoPK.toString();
        crudService.Save(emprestimo);
    }

    @Test
    @Transactional
    public void generateDomiciliarQueue() throws Exception
    {
        CRUDService crudService = (CRUDService) context.getBean("CRUDService");

        StringBuilder actualBuilder = new StringBuilder();
        StringBuilder espectedBuilder = new StringBuilder();
        Obra o = crudService.get(Obra.class, "eee2");
        EmprestimoControllerSingleton eCSingleton = EmprestimoControllerSingleton
                .getInstance(crudService);
        PriorityQueue<Emprestimo> pq = new PriorityQueue<>(o.generateDomiciliarQueue(eCSingleton));

        while (!pq.isEmpty())
        {
            espectedBuilder.append(pq.remove().getEmprestimoPK().toString()+"\n");
        }

        actualBuilder.append(queue[0]+"\n");
        actualBuilder.append(queue[1]+"\n");
        actualBuilder.append(queue[2]+"\n");
        actualBuilder.append(queue[3]+"\n");

       assertThat(actualBuilder.toString()).isEqualTo(espectedBuilder.toString());
       System.out.println(actualBuilder.toString());
    }
}
