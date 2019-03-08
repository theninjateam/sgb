package sgb.deadline;

import java.util.Calendar;

public abstract class Deadline
{
    public abstract Calendar getDeadline(Calendar requestDate);

    public boolean exceededDeadline(Calendar previousDate, Calendar currentDate)
    {
        boolean  exceededDeadline = false;

        if (currentDate.compareTo(getDeadline(previousDate)) > 0)
        {
            exceededDeadline = true;
        }

        return  exceededDeadline;
    }


    public boolean isSaturDay(Calendar c)
    {
        return (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ? true : false;
    }

    public void incrementNMinutes(Calendar calendar, int minutes)
    {
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
    }

    public void incrementNDays(Calendar calendar, int days)
    {
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + days);
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