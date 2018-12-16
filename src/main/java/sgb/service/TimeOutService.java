package sgb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.domain.Emprestimo;

import java.lang.Thread;
import java.util.Calendar;

/**
 * @author Fonseca, bfonseca@unilurio.ac.mz
 */

public class TimeOutService extends Thread
{
    private CRUDService crudService;
    private EmprestimoControllerSingleton eCSingleton;
    private int minuto = 1;

    public TimeOutService(CRUDService crudService, EmprestimoControllerSingleton eCSingleton)
    {
        this.crudService = crudService;
        this.eCSingleton = eCSingleton;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                for (Emprestimo e: eCSingleton.getRequisicoes(1))
                {
                    if (isTimeOut(e.getEmprestimoPK().getDataentrada(), Calendar.getInstance()))
                    {

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
            incrementNMinutes(calendar, eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
        else if(isWeekend(c))
        {
            setLiftingTimeoutForWeekendRequests(calendar);
        }
        
        return calendar;
    }

    public void setLiftingTimeoutForWeekendRequests(Calendar calendar)
    {
        incrementNDays(calendar, isSaturDay(calendar) ? 2 : 1);

        calendar.set(Calendar.MILLISECOND, 00);

        calendar.set(Calendar.SECOND, 00);

        calendar.set(Calendar.MINUTE, 00);

        calendar.set(Calendar.HOUR, eCSingleton.eRSingleton.ENTRY_TIME_ON_WEEKDAYS);

        incrementNMinutes(calendar, eCSingleton.eRSingleton.MAXIMUM_TIME);
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
}