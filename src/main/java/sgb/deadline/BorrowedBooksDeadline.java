package sgb.deadline;

import sgb.controller.domainController.*;
import sgb.domain.*;
import java.util.Calendar;

public class BorrowedBooksDeadline extends Deadline
{
    private ConfigControler configControler;
    private RoleController rController;

    public BorrowedBooksDeadline(ConfigControler configControler,
                                 RoleController rController)
    {
        this.configControler = configControler;
        this.rController = rController;
    }

    public Calendar getDeadline(Emprestimo e)
    {
        Role role = null;
        Calendar borrowDate = e.getDataaprovacao();
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(borrowDate.getTime());

        for (Role r: e.getEmprestimoPK().getUtente().getRoles())
        {
            role = r;
        }
        if (role.getRoleId() == this.rController.STUDENT)
        {
            deadline.set(Calendar.DATE, borrowDate.get(Calendar.DATE) +
                    this.configControler.DEADLINE_STUDENT_BORROWED_BOOKS);
        }
        else if (role.getRoleId() == this.rController.TEACHER)
        {
            deadline.set(Calendar.DATE, borrowDate.get(Calendar.DATE) +
                    this.configControler.DEADLINE_TEACHER_BORROWED_BOOKS);
        }

        goToNextWorkingDay(deadline);

        return deadline;
    }
}
