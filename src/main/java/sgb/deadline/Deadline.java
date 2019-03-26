package sgb.deadline;

import java.util.Calendar;

public abstract class Deadline
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
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);

        if (isSunDay(calendar))
        {
            goToNextWorkingDay(calendar);
        }
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
}