package sgb.deadline;

import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;

public class DeadlineReservedBooks extends Deadline
{
    private ConfigControler configControler;

    public DeadlineReservedBooks(ConfigControler configControler)
    {
        this.configControler = configControler;
    }


    @Override
    public Calendar getDeadline(Calendar reservationtDate)
    {
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(reservationtDate.getTime());

        deadline.set(Calendar.DATE, reservationtDate.get(Calendar.DATE) + this.configControler.DEADLINE_RESERVED_BOOKS);

        if (isSunDay(deadline))
            goToNextWorkingDay(deadline);

        return deadline;
    }
}
