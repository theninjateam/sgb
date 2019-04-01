package sgb.concurrence;

import java.util.concurrent.Semaphore;

public class MiniBookingConcurrenceController
{
    public Semaphore semaphore;

    public MiniBookingConcurrenceController()
    {
        this.semaphore = new Semaphore(1);
    }

    public synchronized void enterInCriticalRegion()
    {
        try
        {
            this.semaphore.acquire();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public synchronized void leaveInCriticalRegion()
    {
        try
        {
            this.semaphore.release();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}