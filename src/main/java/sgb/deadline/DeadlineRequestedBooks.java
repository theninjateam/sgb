package sgb.deadline;

import sgb.controller.domainController.ConfigSingleton;
import java.util.Calendar;

public class DeadlineRequestedBooks extends Deadline
{
    private ConfigSingleton configSingleton;
    
    public DeadlineRequestedBooks(ConfigSingleton configSingleton)
    {
        this.configSingleton = configSingleton;
    }


    @Override
    public Calendar getDeadline(Calendar requestDate)
    {
        if (isWeekend(requestDate))
        {
            return calculateDeadlineForWeekendRequest(requestDate);
        }
        else
        {
            return calculateDeadlineForWeekdaysRequest(requestDate);
        }
    }

    public Calendar calculateDeadlineForWeekdaysRequest(Calendar requestDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(requestDate.getTime());

        if (calendar.get(calendar.HOUR_OF_DAY) >= this.configSingleton.EXIT_TIME_ON_WEEKDAYS)
        {
            calendar.set(Calendar.MINUTE, 00);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
            {
                incrementNDays(calendar, 1);

                calendar.set(Calendar.HOUR_OF_DAY, this.configSingleton.ENTRY_TIME_ON_SATURDAY);
            }
            else
            {
                incrementNDays(calendar, 1);

                calendar.set(Calendar.HOUR_OF_DAY, this.configSingleton.ENTRY_TIME_ON_WEEKDAYS);
            }

            incrementNMinutes(calendar, this.configSingleton.MAXIMUM_TIME);
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) < this.configSingleton.ENTRY_TIME_ON_WEEKDAYS)
        {
            calendar.set(Calendar.HOUR_OF_DAY, this.configSingleton.ENTRY_TIME_ON_WEEKDAYS);

            calendar.set(Calendar.SECOND, 00);

            calendar.set(Calendar.MILLISECOND, 00);

            calendar.set(Calendar.MINUTE, this.configSingleton.MAXIMUM_TIME);
        }
        else
        {
            incrementNMinutes(calendar, this.configSingleton.MAXIMUM_TIME);
        }

        return calendar;
    }

    public Calendar calculateDeadlineForWeekendRequest(Calendar requestDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(requestDate.getTime());
        
        if (this.isSaturDay(calendar) && calendar.get(Calendar.HOUR_OF_DAY) < this.configSingleton.ENTRY_TIME_ON_SATURDAY)
        {
            calendar.set(Calendar.MINUTE, 00);

            calendar.set(Calendar.HOUR_OF_DAY, this.configSingleton.ENTRY_TIME_ON_SATURDAY);

            this.incrementNMinutes(calendar, this.configSingleton.MAXIMUM_TIME);
        }
        else if (this.isSaturDay(calendar) && calendar.get(Calendar.HOUR_OF_DAY) < this.configSingleton.EXIT_TIME_ON_SATURDAY)
        {
            this.incrementNMinutes(calendar, this.configSingleton.MAXIMUM_TIME);
        }
        else
        {
            this.incrementNDays(calendar, this.isSaturDay(calendar) ? 2 : 1);

            calendar.set(Calendar.MILLISECOND, 00);

            calendar.set(Calendar.SECOND, 00);

            calendar.set(Calendar.MINUTE, 00);

            calendar.set(Calendar.HOUR_OF_DAY, this.configSingleton.ENTRY_TIME_ON_WEEKDAYS);

            this.incrementNMinutes(calendar, this.configSingleton.MAXIMUM_TIME);
        }
        
        return calendar;
    }

    public ConfigSingleton getConfigSingleton()
    {
        return this.configSingleton;
    }
}
