package sgb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.domain.Emprestimo;

import java.lang.Thread;
import java.util.Calendar;

public class TimeOutService extends Thread
{
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private EmprestimoControllerSingleton emprestimoControllerSingleton = (EmprestimoControllerSingleton)
            SpringUtil.getBean("emprestimoControllerSingleton");

    private int minuto = 1;

    public TimeOutService() {}

    public void run()
    {
        while(true)
        {
            try
            {
                for (Emprestimo e: emprestimoControllerSingleton.getRequisicoes(1))
                {

                }

                Thread.sleep(minuto * 60 * 1000);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public boolean isTimeOut(Calendar calendar)
    {
        boolean isTimeOut = false;

        if (isWeekend(calendar))
        {

        }
        else
        {

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

}
