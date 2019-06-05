package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.concurrence.MiniBookingConcurrenceController;
import sgb.controller.domainController.ConfigControler;
import sgb.email.SendEmailController;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeadlineThreadManager extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private BookingDeadlineController bDController;
    private BorrowedBooksDeadlineController bBDController;
    private MiniBookingDeadlineController mBDController;
    private ConfigControler configControler;
    private MiniBookingConcurrenceController miniBookingConcurrenceController;
    private SendEmailController sendEmailController;

    private int delay = 1*60*1000;

    public int delayForEmail = 0;

    public Calendar today;
    public final AtomicBoolean running = new AtomicBoolean(false);
    public final AtomicBoolean wasBookingDeadlineControllerStarted = new AtomicBoolean(false);
    public final AtomicBoolean wasBorrowedBooksDeadlineControllerStarted = new AtomicBoolean(false);
    public final AtomicBoolean wasMiniBookingDeadlineControllerStarted = new AtomicBoolean(false);
    public final AtomicBoolean wasSendEmailControllerStarted = new AtomicBoolean(false);
    public final AtomicBoolean isServerStarting  = new AtomicBoolean(false);

    public DeadlineThreadManager(BookingDeadlineController bDController,
                                 BorrowedBooksDeadlineController bBDController,
                                 MiniBookingDeadlineController mBDController,
                                 ConfigControler configControler,
                                 MiniBookingConcurrenceController miniBookingConcurrenceController,
                                 SendEmailController sendEmailController)
    {
        this.bBDController = bBDController;
        this.bDController = bDController;
        this.mBDController = mBDController;
        this.configControler = configControler;
        this.miniBookingConcurrenceController = miniBookingConcurrenceController;
        this.sendEmailController = sendEmailController;
    }

    public void run()
    {
        while(this.running.get())
        {
            this.startBookingDeadlineController();
            this.startBorrowedBooksDeadlineController();
            this.startSendEmailController();

            this.miniBookingConcurrenceController.enterInCriticalRegion();
            this.startMiniBookingDeadlineController();
            this.miniBookingConcurrenceController.leaveInCriticalRegion();

            this.isServerStarting.set(false);

            this.delayForEmail+=(60*1000);

            try
            {
                Thread.sleep(delay);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
    }

    public void startMiniBookingDeadlineController()
    {
        boolean canStart = false;

        if (this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            if (this.isServerStarting.get() &&
                this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_SATURDAY)
            {
                canStart = true;
            }
            else if ((today.get(Calendar.HOUR_OF_DAY) >= configControler.ENTRY_TIME_ON_SATURDAY - 2 )
                    && (today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_SATURDAY))
            {
                canStart = true;
            }
        }
        else if (this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            if (this.isServerStarting.get()
                    && this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_WEEKDAYS)
            {
                canStart = true;
            }
            else  if ((today.get(Calendar.HOUR_OF_DAY) >= configControler.ENTRY_TIME_ON_WEEKDAYS - 2 )
                  &&  (today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_WEEKDAYS))
            {
                canStart = true;
            }
        }

        if (canStart)
        {
            new Thread(mBDController).start();
            this.wasMiniBookingDeadlineControllerStarted.set(true);
        }
    }

    public void startBookingDeadlineController()
    {
        boolean canStart = false;

        if (this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            if (this.isServerStarting.get() &&
                    this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_SATURDAY)
            {
                canStart = true;
            }
            else if ((today.get(Calendar.HOUR_OF_DAY) >= configControler.ENTRY_TIME_ON_SATURDAY - 2 )
                    && (today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_SATURDAY)
                    && !this.isServerStarting.get())
            {
                canStart = true;
            }
        }
        else if (this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            if (this.isServerStarting.get()
                    && this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_WEEKDAYS)
            {
                canStart = true;
            }
            else  if ((today.get(Calendar.HOUR_OF_DAY) >= configControler.ENTRY_TIME_ON_WEEKDAYS -2 )
                    &&  (today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_WEEKDAYS)
                    && !this.isServerStarting.get())
            {
                canStart = true;
            }
        }

        if (canStart)
        {
            new Thread(bDController).start();
            this.wasBookingDeadlineControllerStarted.set(true);
        }
    }

    public void startBorrowedBooksDeadlineController()
    {
        boolean canStart = false;

        if (this.today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            if (this.isServerStarting.get() &&
                    this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_SATURDAY)
            {
                canStart = true;
            }
            else if ((today.get(Calendar.HOUR_OF_DAY) >= configControler.ENTRY_TIME_ON_SATURDAY - 2 )
                    && (today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_SATURDAY)
                    && !this.isServerStarting.get())
            {
                canStart = true;
            }
        }
        else if (this.today.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
        {
            if (this.isServerStarting.get()
                    && this.today.get(Calendar.HOUR_OF_DAY) < configControler.EXIT_TIME_ON_WEEKDAYS)
            {
                canStart = true;
            }
            else  if ((today.get(Calendar.HOUR_OF_DAY) >= configControler.ENTRY_TIME_ON_WEEKDAYS -2 )
                    &&  (today.get(Calendar.HOUR_OF_DAY) < configControler.ENTRY_TIME_ON_WEEKDAYS)
                    && !this.isServerStarting.get())
            {
                canStart = true;
            }
        }

        if (canStart)
        {
            new Thread(bBDController).start();
            this.wasBorrowedBooksDeadlineControllerStarted.set(true);
        }
    }


    public void startSendEmailController() {

        boolean canStart = false;

        if (this.isServerStarting.get()==true){

           canStart = true;
        } else if (this.delayForEmail>=120*60*1000)
            canStart = true;

        if (canStart){

            new Thread(sendEmailController).start();

            this.wasSendEmailControllerStarted.set(true);
            this.delayForEmail = 0;
            return;
        }

        this.wasSendEmailControllerStarted.set(false);

    }

    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        System.out.println("Initializing Threads ...");

        this.today = Calendar.getInstance();
        this.isServerStarting.set(true);
        this.running.set(true);

        this.setName("DeadLine Thread Manager");
        this.start();
    }
}
