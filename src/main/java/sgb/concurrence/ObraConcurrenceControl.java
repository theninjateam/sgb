package sgb.concurrence;

import sgb.domain.Obra;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ObraConcurrenceControl
{
    public HashMap<String, Semaphore> obrasSemaphores = new HashMap<String, Semaphore>();

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

}
