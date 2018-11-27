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
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class ObraTest
{
    private String queue[] = new String[4];

    @Autowired
    private ApplicationContext context;

    private EmprestimoControllerSingleton eCSingleton;
    private CRUDService crudService;

    @Before
    @Transactional
    public void before() throws Exception
    {
        System.out.println("Setting it up!");

        this.crudService = (CRUDService) context.getBean("CRUDService");
        this.eCSingleton = EmprestimoControllerSingleton.getInstance(crudService);
        List<Emprestimo> listem = crudService.getAll(Emprestimo.class);

        for (Emprestimo e: listem)
        {
            this.crudService.delete(e);
        }

        EmprestimoPK emprestimoPK = new EmprestimoPK();
        Emprestimo emprestimo = new Emprestimo();
        Calendar ca = Calendar.getInstance();
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 21);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(this.crudService.get(Users.class, 3));
        emprestimoPK.setObra(this.crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[2] = emprestimoPK.toString();
        this.crudService.Save(emprestimo);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 19);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(this.crudService.get(Users.class, 2));
        emprestimoPK.setObra(this.crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[0] = emprestimoPK.toString();
        this.crudService.Save(emprestimo);

        Thread.sleep(4 * 1000);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 19);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(this.crudService.get(Users.class, 4));
        emprestimoPK.setObra(this.crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[1] = emprestimoPK.toString();
        this.crudService.Save(emprestimo);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 25);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(this.crudService.get(Users.class, 1));
        emprestimoPK.setObra(this.crudService.get(Obra.class, "eee2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        queue[3] = emprestimoPK.toString();
        this.crudService.Save(emprestimo);

        emprestimoPK = new EmprestimoPK();
        emprestimo = new Emprestimo();
        ca = Calendar.getInstance();
        estadoPedido = new EstadoPedido();
        estadoPedido.setIdestadopedido(4);
        ca.set(Calendar.DATE, 25);
        emprestimoPK.setDataentrada(ca);
        emprestimoPK.setUser(this.crudService.get(Users.class, 1));
        emprestimoPK.setObra(this.crudService.get(Obra.class, "WW2"));
        emprestimo.setEmprestimoPK(emprestimoPK);
        emprestimo.setEstadoPedido(estadoPedido);
        this.crudService.Save(emprestimo);
    }

    @Test
    @Transactional
    public void generateDomiciliarQueue() throws Exception
    {
        StringBuilder actualBuilder = new StringBuilder();
        StringBuilder espectedBuilder = new StringBuilder();
        Obra o = this.crudService.get(Obra.class, "eee2");
        PriorityQueue<Emprestimo> pq = new PriorityQueue<>(o.generateDomiciliarQueue(this.eCSingleton));

        while (!pq.isEmpty())
        {
            Emprestimo e = pq.remove();
            espectedBuilder.append(e.getEmprestimoPK().toString()+"\n");
            this.crudService.delete(e);
        }

        actualBuilder.append(queue[0]+"\n");
        actualBuilder.append(queue[1]+"\n");
        actualBuilder.append(queue[2]+"\n");
        actualBuilder.append(queue[3]+"\n");

       assertThat(actualBuilder.toString()).isEqualTo(espectedBuilder.toString());
       assertThat(o.generateDomiciliarQueue(this.eCSingleton).isEmpty()).isEqualTo(true);
       System.out.println(espectedBuilder.toString());
    }
}
