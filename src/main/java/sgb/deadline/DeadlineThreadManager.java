package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeadlineThreadManager extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private int minuto = 1;
    private Calendar today;

    private BookingDeadlineController bDController;
    private BorrowedBooksDeadlineController bBDController;
    private MiniBookingDeadlineController mBDController;
    private ConfigControler configControler;

    private final AtomicBoolean running = new AtomicBoolean(false);
    private final AtomicBoolean wasThreadsStarted = new AtomicBoolean(false);
    private final AtomicBoolean isServerStarting  = new AtomicBoolean(false);

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
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void startThreads()
    {
        boolean canStartThreads = false;

        if (this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            if (this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_SATURDAY &&
                    this.isServerStarting.get())
            {
                canStartThreads = true;
            }
            else if ((today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_SATURDAY) )
            {
                canStartThreads = true;
            }
        }
        else if (this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            if (this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_WEEKDAYS &&
                    this.isServerStarting.get())
            {
                canStartThreads = true;
            }
            else  if ((today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_WEEKDAYS))
            {
                canStartThreads = true;
            }
        }

        if (canStartThreads)
        {
            this.mBDController.setName("Mini Booking Deadline Controller - Thread");
            this.mBDController.getRunning().set(true);
            this.mBDController.run();

            this.bDController.setName("Booking Deadline Controller - Thread");
            this.bDController.getRunning().set(true);
            this.bDController.run();

            this.bBDController.setName("Borrowed Books Deadline Controller - Thread");
            this.bBDController.run();
            this.bBDController.getRunning().set(true);
            this.wasThreadsStarted.set(true);
        }
    }

    public void endThreads()
    {
        boolean canEndThreads = false;

        if ((this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) &&
                this.today.get(Calendar.HOUR_OF_DAY) > configControler.EXIT_TIME_ON_SATURDAY)
        {
            canEndThreads = true;
        }
        else if ((this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) &&
                this.today.get(Calendar.HOUR_OF_DAY) > configControler.EXIT_TIME_ON_WEEKDAYS)
        {
            canEndThreads = true;
        }

        if (canEndThreads)
        {
            this.mBDController.getRunning().set(false);
            this.bBDController.getRunning().set(false);
            this.bDController.getRunning().set(false);

            this.wasThreadsStarted.set(false);
        }
    }

    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        this.today = Calendar.getInstance();
        this.isServerStarting.set(true);
        this.wasThreadsStarted.set(false);
        this.running.set(true);

        this.setName("DeadLine Thread Manager");
        this.start();
    }

    public Calendar getToday() {
        return today;
    }

    public void setToday(Calendar today) {
        this.today = today;
    }

    public AtomicBoolean getRunning() {
        return running;
    }

    public AtomicBoolean getWasThreadsStarted() {
        return wasThreadsStarted;
    }

    public AtomicBoolean getIsServerStarting() {
        return isServerStarting;
    }
}
