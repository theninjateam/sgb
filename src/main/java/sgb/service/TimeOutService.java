package sgb.service;

import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import java.lang.Thread;
import java.util.Calendar;

public class TimeOutService extends Thread
{
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private EmprestimoControllerSingleton eCSingleton;
    private int minuto = 1;

    public TimeOutService()
    {
        this.eCSingleton = EmprestimoControllerSingleton.getInstance(crudService);
    }

    public void run()
    {
        while(true)
        {
            try
            {
                System.out.println("working: "+ Calendar.getInstance().getTime());
                Thread.sleep(minuto * 60 * 1000);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
