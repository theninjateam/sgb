package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeadlineThreadManager extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    int minuto = 1;
    Calendar today = Calendar.getInstance();

    private BookingDeadlineController bDController;
    private BorrowedBooksDeadlineController bBDController;
    private MiniBookingDeadlineController mBDController;
    private ConfigControler configControler;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean wasThreadsStarted = new AtomicBoolean(false);
    private final AtomicBoolean isServerStarting  = new AtomicBoolean(true);

    public DeadlineThreadManager(BookingDeadlineController bDController,
                                 BorrowedBooksDeadlineController bBDController,
                                 MiniBookingDeadlineController mBDController,
                                 ConfigControler configControler)
    {
        this.bBDController = bBDController;
        this.bDController = bDController;
        this.mBDController = mBDController;
        this.configControler = configControler;
    }

    public void run()
    {
        while(this.running.get())
        {
            try
            {
                if (!this.wasThreadsStarted.get())
                {
                    this.startThreads();
                }

                if (this.wasThreadsStarted.get())
                {
                    this.endThreads();
                }

                this.isServerStarting.set(false);
                this.sleep(minuto * 60 * 1000);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void startThreads()
    {
        boolean canStartDaemons = false;

        if (this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            if (this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_SATURDAY &&
                    this.isServerStarting.get())
            {
                canStartDaemons = true;
            }
            else if ((today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_SATURDAY -3) )
            {
                canStartDaemons = true;
            }
        }
        else if (this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            if (this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_WEEKDAYS &&
                    this.isServerStarting.get())
            {
                canStartDaemons = true;
            }
            else  if ((today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_WEEKDAYS - 3))
            {
                canStartDaemons = true;
            }
        }

        if (canStartDaemons)
        {
            this.mBDController.setName("Mini Booking Deadline Controller - Daemon");
            this.mBDController.getRunning().set(true);
            this.mBDController.run();

            this.bDController.setName("Booking Deadline Controller - Daemon");
            this.bDController.run();

            this.bBDController.setName("Borrowed Books Deadline Controller - Daemon");
            this.bBDController.run();

            this.wasThreadsStarted.set(true);
        }
    }

    public void endThreads()
    {
        boolean canEndDaemons = false;

        if ((this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) &&
                this.today.get(Calendar.HOUR_OF_DAY) > configControler.EXIT_TIME_ON_SATURDAY)
        {
            canEndDaemons = true;
        }
        else if ((this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) &&
                this.today.get(Calendar.HOUR_OF_DAY) > configControler.EXIT_TIME_ON_WEEKDAYS)
        {
            canEndDaemons = true;
        }

        if (canEndDaemons)
        {
            if (this.mBDController.isAlive())
            {
                this.mBDController.getRunning().set(false);
            }

            this.wasThreadsStarted.set(false);
        }
    }

    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        this.setName("DeadLine Thread Manager");
        this.isServerStarting.set(true);
        this.running.set(true);
        this.start();

        System.out.println("Starting DeadLine Thread Manager ...");
    }
}
