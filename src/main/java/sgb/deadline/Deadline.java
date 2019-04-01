package sgb.deadline;

import java.util.Calendar;

public class Deadline
{
    public boolean exceededDeadline(Calendar deadline, Calendar currentDate)
    {
        boolean  exceededDeadline = false;

        if (currentDate.compareTo(deadline) > 0)
        {
            exceededDeadline = true;
        }

        return  exceededDeadline;
    }

    public boolean isSaturDay(Calendar c)
    {
        return (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ? true : false;
    }

    public boolean isSunDay(Calendar c)
    {
        return (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ? true : false;
    }

    public void incrementNMinutes(Calendar calendar, int minutes)
    {
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
    }

    public void goToNextWorkingDay(Calendar calendar)
    {
        if (isSunDay(calendar))
        {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
            goToNextWorkingDay(calendar);
        }
        else
        {
            return;
        }
    }


    public boolean isWeekend(Calendar c)
    {
        return c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? true : false;
    }
}