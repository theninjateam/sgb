package sgb.deadline;

import sgb.controller.domainController.ConfigSingleton;

import java.util.Calendar;

public class DeadlineReservedBooks extends Deadline
{
    private ConfigSingleton configSingleton;

    public DeadlineReservedBooks(ConfigSingleton configSingleton)
    {
        this.configSingleton = configSingleton;
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
