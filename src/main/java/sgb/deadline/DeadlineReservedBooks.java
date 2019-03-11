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
    public Calendar getDeadline(Calendar requestDate)
    {
        if (isWeekend(requestDate))
        {
            return calculateDeadlineForWeekendReservations(requestDate);
        }
        else
        {
            return calculateDeadlineForWeekdaysReservations(requestDate);
        }
    }

    public Calendar calculateDeadlineForWeekdaysReservations(Calendar requestDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(requestDate.getTime());

        return calendar;
    }

    public Calendar calculateDeadlineForWeekendReservations(Calendar requestDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(requestDate.getTime());


        return calendar;
    }
}
