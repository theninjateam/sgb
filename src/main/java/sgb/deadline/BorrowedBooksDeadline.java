package sgb.deadline;

import sgb.controller.domainController.*;
import sgb.domain.*;
import java.util.Calendar;

public class BorrowedBooksDeadline extends Deadline
{
    private ConfigControler configControler;
    private RoleController rController;
    private EstadoRenovacaoControler eRController;
    private TipoRequisicaoControler tipoRequisicaoControler;

    public BorrowedBooksDeadline(ConfigControler configControler,
                                 RoleController rController,
                                 EstadoRenovacaoControler eRController,
                                 TipoRequisicaoControler tipoRequisicaoControler
                                 )
    {
        this.configControler = configControler;
        this.rController = rController;
        this.eRController = eRController;
        this.tipoRequisicaoControler = tipoRequisicaoControler;
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
        if (e.getTipoRequisicao().getIdTipoRequisicao() == tipoRequisicaoControler.INTERNAL_REQUISITION) {
            deadline.setTime(e.getDataaprovacao().getTime());
            return deadline;
        } else {
            if (role.getRoleId() == this.rController.STUDENT) {
                deadline.set(Calendar.DATE, borrowDate.get(Calendar.DATE) +
                        this.configControler.DEADLINE_STUDENT_BORROWED_BOOKS);
            } else if (role.getRoleId() == this.rController.TEACHER) {
                deadline.set(Calendar.DATE, borrowDate.get(Calendar.DATE) +
                        this.configControler.DEADLINE_TEACHER_BORROWED_BOOKS);
            }
            if (e.getEstadoRenovacao().getIdestadorenovacao() == eRController.RENOVADO) {
                deadline.add(Calendar.DATE, this.configControler.DAY_OF_RENEWAL);
            }

            goToNextWorkingDay(deadline);

            return deadline;
        }
    }
}
