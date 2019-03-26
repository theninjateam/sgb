/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import sgb.controller.domainController.ConfigControler;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoMultaControler;
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
    private Session session;

    private Obra obra;
    private Multa multa;

    private int qtdMax;

    @Wire
    private Window multaModal;

    @Wire
    private Listbox multaListBox;

    private Emprestimo emprestimo;
    private EstadoMultaControler eMController;
    private EstadoDevolucaoControler eDController;
    private Boolean isForDetails;

    private ConfigControler configControler = (ConfigControler) SpringUtil.getBean("configControler");
    private boolean isStudent;
    private EmprestimoController eController;

    private Boolean isNormalUser = true;





    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();

        isNormalUser = isNormalUser();

        isForDetails = false;

        emprestimo = (Emprestimo) session.getAttribute ("EmprestimoMultado");
        Calendar dataP;

        isForDetails = (Boolean) session.getAttribute("ForDetais");

        multa = (Multa) session.getAttribute("multa");

        multa.setValorpago(fine.getAmoutToPay(multa.getMultaPK()));


        if (isForDetails) {
            /*
            * Elabora uma multa falsa so para apresentar como detalhes do emprestimo
            */
            multaListModel = new ListModelList<Multa>();
            multaListModel.add(fakeMulta(emprestimo));
            multaListBox.setModel(multaListModel);
            multaListBox.setName("Detalhes");

        } else {
            /*
             * Multa ja criada pelo sistema
             */
            multaListModel = new ListModelList<Multa>();
            multaListModel.add(multa);
            multaListBox.setModel(multaListModel);
        }


    }

    public Multa fakeMulta (Emprestimo e) {
        Multa multa = new Multa();

        Emprestimo emprestimo = e;
        int diaatraso = Math.abs (
                (int) Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays());

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

    public boolean isNormalUser () {
        Boolean a = true;

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRole().equals("ADMIN"))
                a = false;
        }
        return a;
    }


    public String dataPrevistaDevolucao(EmprestimoPK multapk) {

        Calendar dataP;
        emprestimo = crudService.get(Emprestimo.class,multapk);
        dataP = emprestimo.getDatadevolucao();
        return dataConvert(dataP);
    }

    @Listen("onExit= #multaListBox")
    public void exit ()
    {
        session.removeAttribute("ForDetais");
        session.removeAttribute("EmprestimoMultado");
        session.removeAttribute("multa");
        multaModal.detach();
    }

    @Listen("onPagar= #multaListBox")
    public void doPagar (ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {

            EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);
            emprestimo.setEstadoDevolucao(estadoDevolucao);
            emprestimo.setComentario("");
            crudService.update(emprestimo);

            fine.pay(multa.getMultaPK());

            exit();
            Clients.showNotification("Multa paga", null, null, null, 5000);
        }

    }

    @Listen("onRevogar = #multaListBox")
    public void doRevogar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {

            EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);
            emprestimo.setEstadoDevolucao(estadoDevolucao);
            emprestimo.setComentario("");
            crudService.update(emprestimo);

            fine.pay(multa.getMultaPK());

            exit();
            Clients.showNotification("Multa Revogada", null, null, null, 5000);
        }

    }
    @Listen("onDevolver = #multaListBox")
    public void doDevolver(ForwardEvent event) {
        if (isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.RETURNED);

            emprestimo.setEstadoDevolucao(estadoDevolucao);
            emprestimo.setComentario("");

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


        SimpleDateFormat timeFormatter = new SimpleDateFormat("'('HH:mm:s')'");
        DateFormat dateFormatter = new SimpleDateFormat();
        Locale MOZAMBIQUE = new Locale("pt","MZ");
        StringBuilder builder = new StringBuilder();


        dateFormatter = DateFormat.getDateInstance(DateFormat.FULL, MOZAMBIQUE);

        builder.append(dateFormatter.format(dataa.getTime()));
        builder.append("\n");
        builder.append(timeFormatter.format(dataa.getTime()));

        String dataEntrada = builder.toString();

        return dataEntrada;
    }

}
