package sgb.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.controller.domainController.EstadoPedidoSingleton;
import sgb.domain.Emprestimo;

import java.lang.Thread;
import java.util.Calendar;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class TimeOutService extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    private CRUDService crudService;
    private EmprestimoControllerSingleton eCSingleton;
    private EstadoPedidoSingleton ePSingleton;
    private int minuto = 1;

    public TimeOutService(CRUDService crudService, EmprestimoControllerSingleton eCSingleton,
                          EstadoPedidoSingleton ePSingleton)
    {
        this.crudService = crudService;
        this.eCSingleton = eCSingleton;
        this.ePSingleton = ePSingleton;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                for (Emprestimo e: eCSingleton.getRequisicoes(ePSingleton.PENDING))
                {
                    if (isTimeOut(e.getEmprestimoPK().getDataentrada(), Calendar.getInstance()))
                    {
                        eCSingleton.cancelEmprestimo(e);
                    }
                }

                Thread.sleep(minuto * 60 * 1000);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();

            }
        }
    }


    public boolean isTimeOut(Calendar entryDate, Calendar currentDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(entryDate.getTime());
        boolean isTimeOut = false;

        if (currentDate.compareTo(getLiftingTimeout(calendar)) > 0)
        {
            isTimeOut = true;
        }

        return isTimeOut;
    }

    public boolean isWeekend(Calendar c)
    {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
        {
            return true;
        }
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Calendar getLiftingTimeout(Calendar c)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c.getTime());

        if (!isWeekend(c))
        {
            setLiftingTimeoutForWeekdaysRequests(calendar);
        }
        else if(isWeekend(c))
        {
            setLiftingTimeoutForWeekendRequests(calendar);
        }
        
        return calendar;
    }

    public void setLiftingTimeoutForWeekdaysRequests(Calendar calendar)
    {
        if (calendar.get(Calendar.HOUR_OF_DAY) >= this.eCSingleton.eRSingleton.EXIT_TIME_ON_WEEKDAYS)
        {
            calendar.set(Calendar.MINUTE, 00);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
            {
                incrementNDays(calendar, 1);

                calendar.set(Calendar.HOUR_OF_DAY, this.eCSingleton.eRSingleton.ENTRY_TIME_ON_SATURDAY);
            }
            else
            {
                incrementNDays(calendar, 1);

                calendar.set(Calendar.HOUR_OF_DAY, this.eCSingleton.eRSingleton.ENTRY_TIME_ON_WEEKDAYS);
            }

            incrementNMinutes(calendar, this.eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) < this.eCSingleton.eRSingleton.ENTRY_TIME_ON_WEEKDAYS)
        {
            calendar.set(Calendar.HOUR_OF_DAY, this.eCSingleton.eRSingleton.ENTRY_TIME_ON_WEEKDAYS);

            calendar.set(Calendar.SECOND, 00);

            calendar.set(Calendar.MILLISECOND, 00);

            calendar.set(Calendar.MINUTE, this.eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
        else
        {
            incrementNMinutes(calendar, eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
    }

    public void setLiftingTimeoutForWeekendRequests(Calendar calendar)
    {
        if (isSaturDay(calendar) && calendar.get(Calendar.HOUR_OF_DAY) < this.eCSingleton.eRSingleton.ENTRY_TIME_ON_SATURDAY)
        {
            calendar.set(Calendar.MINUTE, 00);

            calendar.set(Calendar.HOUR_OF_DAY, this.eCSingleton.eRSingleton.ENTRY_TIME_ON_SATURDAY);

            incrementNMinutes(calendar, this.eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
        else if (isSaturDay(calendar) && calendar.get(Calendar.HOUR_OF_DAY) < this.eCSingleton.eRSingleton.EXIT_TIME_ON_SATURDAY)
        {
                incrementNMinutes(calendar, eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
        else
        {
            incrementNDays(calendar, isSaturDay(calendar) ? 2 : 1);

            calendar.set(Calendar.MILLISECOND, 00);

            calendar.set(Calendar.SECOND, 00);

            calendar.set(Calendar.MINUTE, 00);

            calendar.set(Calendar.HOUR_OF_DAY, eCSingleton.eRSingleton.ENTRY_TIME_ON_WEEKDAYS);

            incrementNMinutes(calendar, eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
    }

    public boolean isSaturDay(Calendar c)
    {
        return (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ? true : false;
    }

    public void incrementNMinutes(Calendar calendar, int minutes)
    {
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
    }

    public void incrementNDays(Calendar calendar, int days)
    {
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
    }


    public EmprestimoControllerSingleton geteCSingleton()
    {
        return eCSingleton;
    }

    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        this.start();
    }
}