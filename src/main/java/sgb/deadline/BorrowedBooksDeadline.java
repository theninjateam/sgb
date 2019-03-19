package sgb.deadline;

import sgb.controller.domainController.ConfigControler;
import sgb.domain.*;

import java.time.Duration;
import java.util.Calendar;

public class BorrowedBooksDeadline extends Deadline
{
    private ConfigControler configControler;
    private boolean isStudent;
    public BorrowedBooksDeadline(ConfigControler configControler)
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

    public void multar (Emprestimo e)
    {
        Emprestimo emprestimo = this.eController.getRequest(e.getEmprestimoPK());
        EstadoDevolucao estadoDevolucao = this.crudService.get(EstadoDevolucao.class, this.estadoDevolucaoControler.NOT_RETURNED);

        if(emprestimo.getEstadoDevolucao().equals(estadoDevolucao)) {
            /*
             *  Gera a multa e atualiza a tabela emprestimo com estado de devolucao NOT_RETURNED
             */
            emprestimo.setEstadoDevolucao(estadoDevolucao);

            Multa multa = new Multa();
            multa.setMultaPK(emprestimo.getEmprestimoPK());

            EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,2);

            multa.setDataemissao(Calendar.getInstance());
            multa.setDataemprestimo(emprestimo.getDataaprovacao());
            multa.setEstadoMulta(estadoMulta);

            int diaatraso = -1 * (int) Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays();

            multa.setDiasatraso(diaatraso );

            Float taxaD = Float.parseFloat(crudService.get(Config.class,"DAILY_RATE_FINE").getValor());
            multa.setTaxadiaria(taxaD);

            multa.setValorpago((diaatraso*taxaD));
            this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());
            crudService.Save(multa);
            crudService.update(emprestimo);
            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());


        }
        else
        {
            /*
             *atualiza a multa nos campos de dias de atraso e total a pagar
             */
            Multa multa =new Multa();
            multa = crudService.get(Multa.class,emprestimo.getEmprestimoPK());

            int diaatraso = -1 * (int) Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays();

            multa.setDiasatraso(diaatraso );

            Float taxaD = Float.parseFloat(crudService.get(Config.class,"DAILY_RATE_FINE").getValor());
            multa.setTaxadiaria(taxaD);

            multa.setValorpago((diaatraso*taxaD));
            this.obraConcurrenceControl.enterInCriticalRegion(e.getEmprestimoPK().getObra());
            crudService.update(multa);
            this.obraConcurrenceControl.leaveInCriticalRegion(e.getEmprestimoPK().getObra());

        }
    }
}
