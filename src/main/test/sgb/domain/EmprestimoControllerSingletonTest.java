package sgb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class EmprestimoControllerSingletonTest
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
        this.eCSingleton = (EmprestimoControllerSingleton) context.getBean("emprestimoControllerSingleton");

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
    public void generateDomiciliarQueueFor() throws Exception
    {
        StringBuilder actualBuilder = new StringBuilder();
        StringBuilder espectedBuilder = new StringBuilder();
        Obra o = this.crudService.get(Obra.class, "eee2");
        PriorityQueue<Emprestimo> pq = this.eCSingleton.generateDomiciliarQueueFor(o);

        while (!pq.isEmpty())
        {
            Emprestimo e = pq.remove();
            espectedBuilder.append(e.getEmprestimoPK().toString()+"\n");
        }

        actualBuilder.append(queue[0]+"\n");
        actualBuilder.append(queue[1]+"\n");
        actualBuilder.append(queue[2]+"\n");
        actualBuilder.append(queue[3]+"\n");

       assertThat(actualBuilder.toString()).isEqualTo(espectedBuilder.toString());
       System.out.println(espectedBuilder.toString());
    }

    //@Test
    @Transactional
    public void requisitar() throws Exception
    {
        Item item = new Item();
        Users user1 = this.crudService.get(Users.class, 2);
        Users user2 = this.crudService.get(Users.class, 3);

        Obra obra = this.crudService.get(Obra.class, "eee2");
        obra.setQuantidade(this.eCSingleton.eRSingleton.MINIMUM_NUMBER_OF_COPIES + 1);
        this.crudService.update(obra);

        item.setObra(obra);

        this.eCSingleton.requisitar(item, user1);
        this.eCSingleton.requisitar(item, user2);


        obra = this.crudService.get(Obra.class, "eee2");

        System.out.println("qtd: "+obra.getQuantidade());
        assertThat(obra.getQuantidade()).isEqualTo(this.eCSingleton.eRSingleton.MINIMUM_NUMBER_OF_COPIES);
        assertThat(this.eCSingleton.getRequisicoes(user1, 1).size()).isEqualTo(1);
        assertThat(this.eCSingleton.getRequisicoes(user2, 4).size()).isEqualTo(1);

    }

    @Test
    @Transactional
    public void enterAndLeaveCriticalRegion() throws Exception
    {
        Item item1 = new Item();
        Item item2 = new Item();

        item1.setObra(this.crudService.get(Obra.class, "eee2"));
        item2.setObra(this.crudService.get(Obra.class, "WW2"));

        Request requesta = new Request(item1, 5, "requestA", this.eCSingleton);
        requesta.start();

        Request requestb = new Request(item1, 0, "requestB", this.eCSingleton);
        requestb.start();

        Request requestc = new Request(item2, 5, "requestC", this.eCSingleton);
        requestc.start();

        Request requestd = new Request(item2, 0, "requestD", this.eCSingleton);
        requestd.start();

        Thread.sleep(10 *1000);

        assertThat(this.eCSingleton.resources.get(item1.getObra().getCota()).availablePermits()).isEqualTo(1);
        assertThat(this.eCSingleton.resources.get(item2.getObra().getCota()).availablePermits()).isEqualTo(1);

    }
}

class Request extends Thread
{
    private Item item;
    private int secs;
    private String name;
    private EmprestimoControllerSingleton eCSingleton;

    public Request(Item item, int secs, String name, EmprestimoControllerSingleton eCSingleton)
    {
        this.item = item;
        this.secs = secs;
        this.name = name;
        this.eCSingleton = eCSingleton;
    }

    public void run()
    {
        try
        {
            this.eCSingleton.enterInCriticalRegion(this.item);

            Thread.sleep(this.secs *1000);

            System.out.println("["+this.name+"]: semaphore status after enter: "+
                    this.eCSingleton.resources.get(this.item.getObra().getCota()).availablePermits());

            this.eCSingleton.leaveInCriticalRegion(this.item);

            System.out.println("["+this.name+"]: semaphore status after leave: "+
                    this.eCSingleton.resources.get(this.item.getObra().getCota()).availablePermits());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
