package sgb.concurrence;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.domain.Obra;
import sgb.service.CRUDService;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ObraConcurrenceControl implements ApplicationListener<ContextRefreshedEvent>
{
    public HashMap<String, Semaphore> obrasSemaphores = new HashMap<String, Semaphore>();
    private CRUDService crudService;

    public ObraConcurrenceControl(CRUDService crudService)
    {
        this.crudService = crudService;
    }

    public synchronized void enterInCriticalRegion(Obra obra)
    {
        try
        {
            String cota = obra.getCota();

            if (this.obrasSemaphores.get(cota) == null)
            {
                this.obrasSemaphores.put(cota, new Semaphore(1));
                this.obrasSemaphores.get(cota).acquire();
            }
            else
            {
                this.obrasSemaphores.get(cota).acquire();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void leaveInCriticalRegion(Obra obra)
    {
        try
        {
            String cota = obra.getCota();
            this.obrasSemaphores.get(cota).release();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        System.out.println("Initializing Books Semaphones ...");

        for (Obra obra:(List<Obra>) this.crudService.getAll(Obra.class))
        {
            this.obrasSemaphores.put(obra.getCota(), new Semaphore(1));
        }

        System.out.println("Books Semaphones Initialized.");
    }
}
