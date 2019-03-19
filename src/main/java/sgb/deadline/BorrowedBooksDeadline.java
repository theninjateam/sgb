package sgb.deadline;

import sgb.controller.domainController.ConfigControler;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoMultaControler;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.time.Duration;
import java.util.Calendar;

public class BorrowedBooksDeadline extends Deadline
{
    private ConfigControler configControler;
    private boolean isStudent;
    private EmprestimoController eController;
    private EstadoMultaControler eMController;
    private CRUDService crudService;
    private EstadoDevolucaoControler eDController;

    public BorrowedBooksDeadline(ConfigControler configControler,
                                 EmprestimoController eController,
                                 EstadoMultaControler eMController,
                                 CRUDService crudService,
                                 EstadoDevolucaoControler eDController)
    {
        this.configControler = configControler;
        this.eController = eController;
        this.eMController = eMController;
        this.crudService = crudService;
        this.eDController = eDController;
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

    public void multar (Emprestimo e)
    {
        Multa multa = new Multa();
        Emprestimo emprestimo = this.eController.getRequest(e.getEmprestimoPK());
        EstadoDevolucao estadoDevolucao = this.crudService.get(EstadoDevolucao.class, this.eDController.NOT_RETURNED);
        EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,this.eMController.NOT_PAID);
        int diaatraso = Math.abs (
                (int) Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays());

        emprestimo.setEstadoDevolucao(estadoDevolucao);

        multa.setMultaPK(emprestimo.getEmprestimoPK());
        multa.setDataemissao(Calendar.getInstance());
        multa.setDataemprestimo(emprestimo.getDataaprovacao());
        multa.setEstadoMulta(estadoMulta);
        multa.setDiasatraso(diaatraso);

        float taxaD = this.configControler.DAILY_RATE_FINE;
        multa.setTaxadiaria(taxaD);
        multa.setValorpago((diaatraso*taxaD));

        crudService.Save(multa);
        crudService.update(emprestimo);
    }
}
