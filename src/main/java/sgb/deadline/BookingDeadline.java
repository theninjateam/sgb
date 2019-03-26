package sgb.deadline;

import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;

public class BookingDeadline extends Deadline
{
    private ConfigControler configControler;

    public BookingDeadline(ConfigControler configControler)
    {
        this.configControler = configControler;
    }

    public Calendar getDeadline(Calendar reservationDate)
    {
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(reservationDate.getTime());

        deadline.set(Calendar.DATE, reservationDate.get(Calendar.DATE) + this.configControler.DEADLINE_RESERVED_BOOKS);

        if (isSunDay(deadline))
            goToNextWorkingDay(deadline);

        return deadline;
    }
}
