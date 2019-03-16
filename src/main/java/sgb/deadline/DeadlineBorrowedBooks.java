package sgb.deadline;

import sgb.controller.domainController.ConfigControler;

import java.util.Calendar;

public class DeadlineBorrowedBooks extends Deadline
{
    private ConfigControler configControler;
    private boolean isStudent;
    public DeadlineBorrowedBooks(ConfigControler configControler)
    {
        this.configControler = configControler;
    }

    public Calendar getDeadline(Calendar borrowDate, boolean isStudent)
    {
        this.isStudent = isStudent;
        return getDeadline(borrowDate);
    }

    @Override
    public Calendar getDeadline(Calendar borrowDate)
    {
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(borrowDate.getTime());

        if (this.isStudent)
        {
            deadline.set(Calendar.DATE, borrowDate.get(Calendar.DATE) +
                    this.configControler.DEADLINE_STUDENT_BORROWED_BOOKS);
        }
        else
        {
            deadline.set(Calendar.DATE, borrowDate.get(Calendar.DATE) +
                    this.configControler.DEADLINE_TEACHER_BORROWED_BOOKS);
        }

        if (isSunDay(deadline))
            goToNextWorkingDay(deadline);

        return deadline;
    }
}
