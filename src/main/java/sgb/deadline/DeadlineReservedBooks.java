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
    public Calendar getDeadline(Calendar reserveDate)
    {
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(reserveDate.getTime());

        deadline.set(Calendar.DATE, reserveDate.get(Calendar.DATE) + this.configControler.DEADLINE_RESERVED_BOOKS);

        if (isSunDay(deadline))
            goToNextWorkingDay(deadline);

        return deadline;
    }
}
