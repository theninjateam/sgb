package sgb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.domain.Emprestimo;

import java.lang.Thread;
import java.util.Calendar;

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

        if (isWeekend(calendar))
        {
        }
        else if (!isWeekend(calendar))
        {
            isTimeOut = currentDate.compareTo(getLiftingTimeout(calendar)) > 0
                    || currentDate.get(Calendar.HOUR) >= eCSingleton.eRSingleton.EXIT_TIME_ON_WEEKDAYS ? true : false;
        }

        return isTimeOut;
    }


    public boolean isWeekend(Calendar c)
    {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
          return true;
        else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        else
            return false;
    }

    public Calendar getLiftingTimeout(Calendar c)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(c.getTime());

        if (!isWeekend(c))
        {
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + eCSingleton.eRSingleton.MAXIMUM_TIME);
        }
        
        return calendar;
    }
}