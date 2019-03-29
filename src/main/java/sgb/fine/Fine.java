package sgb.fine;

import sgb.controller.domainController.*;
import sgb.deadline.BorrowedBooksDeadline;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.time.Duration;
import java.util.Calendar;

public class Fine
{
    private MultaController mController;
    private EstadoMultaControler eMController;
    private CRUDService crudService;
    private EstadoDevolucaoControler eDController;
    private ConfigControler configControler;
    private EmprestimoController eController;
    private BorrowedBooksDeadline bBDeadline;

    public Fine(ConfigControler configControler,
                MultaController mController,
                EstadoMultaControler eMController,
                CRUDService crudService,
                EstadoDevolucaoControler eDController,
                EmprestimoController eController,
                BorrowedBooksDeadline bBDeadline)
    {

        this.configControler = configControler;
        this.crudService = crudService;
        this.eController = eController;
        this.eMController = eMController;
        this.eDController = eDController;
        this.mController = mController;
        this.bBDeadline = bBDeadline;
    }

    public void fine(Emprestimo e, Calendar now)
    {
        if (!this.wasFinedBefore(e.getEmprestimoPK()))
        {
            Multa multa = new Multa();
            Emprestimo emprestimo = this.eController.getRequest(e.getEmprestimoPK());
            EstadoDevolucao estadoDevolucao = this.crudService.get(EstadoDevolucao.class, this.eDController.NOT_RETURNED);
            EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,this.eMController.NOT_PAID);

            int diaatraso = getDelayDays(now, e.getDatadevolucao());

            emprestimo.setEstadoDevolucao(estadoDevolucao);

            multa.setMultaPK(emprestimo.getEmprestimoPK());
            multa.setDataemissao(now);
            multa.setDataemprestimo(emprestimo.getDataaprovacao());
            multa.setEstadoMulta(estadoMulta);
            multa.setDiasatraso(diaatraso);

            float taxaD = this.configControler.DAILY_RATE_FINE;
            multa.setTaxadiaria(taxaD);
            multa.setValorpago((float) 0);

            crudService.Save(multa);
            crudService.update(emprestimo);

        }
    }

    public int getDelayDays(Calendar now, Calendar deadline)
    {
        return Math.abs ((int) Duration.between(now.toInstant(), deadline.toInstant()).toDays());
    }

    public boolean wasFinedBefore(EmprestimoPK emprestimoPK)
    {
        return  this.mController.getFine(emprestimoPK) == null? false : true;
    }

    public float getAmoutToPay(EmprestimoPK emprestimoPK, Calendar now)
    {
        Emprestimo emprestimo = this.eController.getRequest(emprestimoPK);
        int days = getDelayDays(now, emprestimo.getDatadevolucao());

        return (float) this.configControler.DAILY_RATE_FINE * days;
    }

    public void pay(EmprestimoPK emprestimoPK, Calendar now)
    {
        EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,this.eMController.PAID);
        Multa multa = this.mController.getFine(emprestimoPK);
        multa.setEstadoMulta(estadoMulta);
        multa.setValorpago(getAmoutToPay(emprestimoPK, now));
        this.crudService.update(multa);
    }

    public void revoke(EmprestimoPK emprestimoPK)
    {
        EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,this.eMController.REVOKED);
        Multa multa = this.mController.getFine(emprestimoPK);
        multa.setEstadoMulta(estadoMulta);

        this.crudService.update(multa);
    }
}
