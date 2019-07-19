/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.util.CollectionsX;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.*;
import sgb.deadline.BorrowedBooksDeadline;
import sgb.domain.*;
import sgb.fine.Fine;
import sgb.service.CRUDService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class MultaModalController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private Fine fine = (Fine) SpringUtil.getBean("fine");


    private  ListModelList<Multa> multaListModel;
    private  ListModelList<Multa> LisModelFromListMulta; // List Model da view ListMulta

    private Session session;

    private Obra obra;
    private Multa multa;

    private int qtdMax;

    @Wire
    private Window multaModal;

    @Wire
    private Listbox multaListBox;


    private EstadoMultaControler eMController = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private EstadoDevolucaoControler eDController = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private Boolean isForDetails =false;

    private ConfigControler configControler = (ConfigControler) SpringUtil.getBean("configControler");
    private BorrowedBooksDeadline bBDeadline = (BorrowedBooksDeadline) SpringUtil.getBean("borrowedBooksDeadline");
    private UserController uController = (UserController) SpringUtil.getBean("userController");

    private Boolean isNormalUser = true;
    private Emprestimo emprestimo = null;





    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();


        isNormalUser = isNormalUser();

        isForDetails = false;

        isForDetails = (Boolean) session.getAttribute("ForDetais");

        LisModelFromListMulta = (ListModelList<Multa>)session.getAttribute("MultaListModel");


        if (isForDetails) {
            /*
            * Elabora uma multa falsa so para apresentar como detalhes do emprestimo
            */

            Emprestimo emprestimo = (Emprestimo) session.getAttribute ("EmprestimoMultado");

            multaListModel = new ListModelList<Multa>();
            multaListModel.add(fakeMulta(emprestimo));
            multaListBox.setModel(multaListModel);
            multaListBox.setName("Detalhes");

        } else {
            /*
             * Multa ja criada pelo sistema
             */
            multa = (Multa) session.getAttribute("Multa");
            emprestimo = crudService.get(Emprestimo.class,multa.getMultaPK());
            multa.setValorpago(fine.getAmoutToPay(emprestimo.getEmprestimoPK(), Calendar.getInstance()));

            if (ObraRETURNED(emprestimo.getEmprestimoPK())) {
                Calendar dataLimite = bBDeadline.getDeadline(emprestimo);
                multa.setDiasatraso(fine.getDelayDays(emprestimo.getDatadevolucao(),dataLimite));
            } else {
                multa.setDiasatraso(fine.getDelayDays(Calendar.getInstance(), emprestimo.getDatadevolucao()));
            }

            multaListModel = new ListModelList<Multa>();
            multaListModel.add(multa);
            multaListBox.setModel(multaListModel);
        }


    }

    public Multa fakeMulta (Emprestimo e) {
        Multa multa = new Multa();

         Calendar CurremtDate = Calendar.getInstance();

        Emprestimo emprestimo = e;
        int diaatraso = 0;
        if(CurremtDate.compareTo(emprestimo.getDatadevolucao())>0) {
            diaatraso = Math.abs(
                    (int) Duration.between(CurremtDate.toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays());
        }

        emprestimo.setEstadoDevolucao(null);

        multa.setMultaPK(emprestimo.getEmprestimoPK());
        multa.setDataemissao(Calendar.getInstance());
        multa.setDataemprestimo(emprestimo.getDataaprovacao());
        multa.setEstadoMulta(null);
        multa.setDiasatraso(diaatraso);

        float taxaD = this.configControler.DAILY_RATE_FINE;
        multa.setTaxadiaria(taxaD);
        multa.setValorpago((diaatraso*taxaD));
        return multa;

    }
    public boolean isForDetails () {
        return this.isForDetails;
    }

    public boolean isNormalUser () {
        return uController.isNormalUser(this.user);
    }


    public String dataDevolucao(EmprestimoPK multapk) {

        Calendar dataP;
        Emprestimo emprestimo = crudService.get(Emprestimo.class,multapk);
        dataP = emprestimo.getDatadevolucao();
        return dataConvert(dataP);
    }

    public String dataPrevistaDevolucao(EmprestimoPK multapk) {

        Calendar dataP;
        Emprestimo emprestimo = crudService.get(Emprestimo.class,multapk);
        dataP = bBDeadline.getDeadline(emprestimo);
        return dataConvert(dataP);
    }

    public boolean ObraRETURNED (EmprestimoPK emprestimoPK) {

        Emprestimo emprestimo = crudService.get(Emprestimo.class,emprestimoPK);
        return emprestimo.getEstadoDevolucao().getDescricao().equals("RETURNED") ? true:false;

    }


    @Listen("onExit= #multaListBox")
    public void exit ()
    {
        session.removeAttribute("ForDetais");
        session.removeAttribute("EmprestimoMultado");
        session.removeAttribute("Multa");
        multaModal.detach();
    }

    @Listen("onPagar= #multaListBox")
    public void doPagar (ForwardEvent event)
    {

        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {

            if (ObraRETURNED(emprestimo.getEmprestimoPK())){
                fine.pay(multa.getMultaPK(), Calendar.getInstance());

                exit();
                Clients.showNotification("Multa paga", null, null, null, 5000);

            }else{
                EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);
                emprestimo.setEstadoDevolucao(estadoDevolucao);
                emprestimo.setComentario("Multa paga");
                crudService.update(emprestimo);

                fine.pay(multa.getMultaPK(), Calendar.getInstance());

                exit();
                Clients.showNotification("Multa paga", null, null, null, 5000);
            }
        }
        LisModelFromListMulta.remove(multa);
        session.removeAttribute("MultaListModel");

    }

    @Listen("onRevogar = #multaListBox")
    public void doRevogar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {

            if (ObraRETURNED(emprestimo.getEmprestimoPK())){
                fine.revoke(multa.getMultaPK());


                exit();
                Clients.showNotification("Multa Revogada", null, null, null, 5000);

            }else{
                EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);
                emprestimo.setEstadoDevolucao(estadoDevolucao);
                emprestimo.setComentario("Multa Revogada");
                crudService.update(emprestimo);

                fine.revoke(multa.getMultaPK());



                exit();
                Clients.showNotification("Multa Revogada", null, null, null, 5000);
            }
        }
        LisModelFromListMulta.remove(multa);
        session.removeAttribute("MultaListModel");

    }
    @Listen("onDevolver = #multaListBox")
    public void doDevolver(ForwardEvent event) {
        if (isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {

            EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);
            emprestimo.setEstadoDevolucao(estadoDevolucao);
            emprestimo.setComentario("Obra devolvida e Multa nao paga");

            crudService.update(emprestimo);

            exit();
            Clients.showNotification("Obra devolvida e Multa nao paga", null, null, null, 5000);
        }
    }

    public Component getListitem (Button btn)
    {
        Component component = btn.getParent();

        while(true)
        {
            if ( component instanceof  Listitem)
            {
                return  component;
            }

            if(component == null)
                return null;

            component = component.getParent();
        }
    }

    public String dataConvert (Calendar dataa) {


        SimpleDateFormat timeFormatter = new SimpleDateFormat("");
        DateFormat dateFormatter = new SimpleDateFormat();
        Locale MOZAMBIQUE = new Locale("pt","MZ");
        StringBuilder builder = new StringBuilder();


        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, MOZAMBIQUE);

        builder.append(dateFormatter.format(dataa.getTime()));
        builder.append("\n");
        builder.append(timeFormatter.format(dataa.getTime()));

        String dataEntrada = builder.toString();

//        String[] aa = dataEntrada.split("(");

        return dataEntrada;
    }

}
