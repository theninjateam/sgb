package sgb.deadline;

import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;

public class MiniBookingDeadline extends Deadline
{
    private ConfigControler configControler;
    
    public MiniBookingDeadline(ConfigControler configControler)
    {
        this.configControler = configControler;
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

        if (calendar.get(calendar.HOUR_OF_DAY) >= this.configControler.EXIT_TIME_ON_WEEKDAYS)
        {
            calendar.set(Calendar.MINUTE, 00);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
            {
                this.goToNextWorkingDay(calendar);

                calendar.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_SATURDAY);
            }
            else
            {
                this.goToNextWorkingDay(calendar);
                calendar.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);
            }

            incrementNMinutes(calendar, this.configControler.DEADLINE_REQUESTED_BOOKS);
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) < this.configControler.ENTRY_TIME_ON_WEEKDAYS)
        {
            calendar.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);
            calendar.set(Calendar.SECOND, 00);
            calendar.set(Calendar.MILLISECOND, 00);
            calendar.set(Calendar.MINUTE, this.configControler.DEADLINE_REQUESTED_BOOKS);
        }
        else
        {
            incrementNMinutes(calendar, this.configControler.DEADLINE_REQUESTED_BOOKS);
        }

        return calendar;
    }

    public Calendar calculateDeadlineForWeekendRequest(Calendar requestDate)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(requestDate.getTime());
        
        if (this.isSaturDay(calendar) && calendar.get(Calendar.HOUR_OF_DAY) < this.configControler.ENTRY_TIME_ON_SATURDAY)
        {
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_SATURDAY);
            
            this.incrementNMinutes(calendar, this.configControler.DEADLINE_REQUESTED_BOOKS);
        }
        else if (this.isSaturDay(calendar) && calendar.get(Calendar.HOUR_OF_DAY) < this.configControler.EXIT_TIME_ON_SATURDAY)
        {
            this.incrementNMinutes(calendar, this.configControler.DEADLINE_REQUESTED_BOOKS);
        }
        else
        {
            this.goToNextWorkingDay(calendar);

            calendar.set(Calendar.MILLISECOND, 00);
            calendar.set(Calendar.SECOND, 00);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.HOUR_OF_DAY, this.configControler.ENTRY_TIME_ON_WEEKDAYS);

            this.incrementNMinutes(calendar, this.configControler.DEADLINE_REQUESTED_BOOKS);
        }
        
        return calendar;
    }

    public ConfigControler getConfigControler()
    {
        return this.configControler;
    }
}
