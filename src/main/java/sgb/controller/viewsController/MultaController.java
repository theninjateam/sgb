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
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.controller.domainController.EstadoPedidoSingleton;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author Fonseca, Emerson, Matimbe
 */

public class MultaController extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private EmprestimoControllerSingleton emprestimoControllerSingleton = (EmprestimoControllerSingleton)
            SpringUtil.getBean("emprestimoControllerSingleton");
    private EmprestimoControllerSingleton eCSingleton;
    private EstadoPedidoSingleton ePSingleton;
    private EstadoPedido estadoPedido;


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



    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        session = Sessions.getCurrent();

        multa = (Multa) session.getAttribute ("Multaa");
        EmprestimoPK emprestimopk= new EmprestimoPK();
        Calendar dataP;
        emprestimopk.setDataentrada(multa.getMultaPK().getDataentradapedido());
        emprestimopk.setObra(multa.getMultaPK().getObra());
        emprestimopk.setUser(multa.getMultaPK().getUser());

        emprestimo = crudService.get(Emprestimo.class,emprestimopk);


//        obra = multa.getMultaPK().getObra();
//        alert( obra.getTitulo());


        multaListModel = new ListModelList<Multa>();
        multaListModel.add(multa);
        multaListBox.setModel(multaListModel);

    }

    public String dataPrevistaDevolucao(MultaPK multapk) {
        EmprestimoPK emprestimopk= new EmprestimoPK();
        Calendar dataP;
        emprestimopk.setDataentrada(multapk.getDataentradapedido());
        emprestimopk.setObra(multapk.getObra());
        emprestimopk.setUser(multapk.getUser());

         emprestimo = crudService.get(Emprestimo.class,emprestimopk);

        if(emprestimo.getEstadoRenovacao().getIdestadorenovacao()==1) {
            dataP = emprestimo.getDatadevolucaorenovacao();
        } else {
            dataP = emprestimo.getDatadevolucao();
        }
        return dataConvert(dataP);
    }

    @Listen("onExit= #multaListBox")
    public void exit ()
    {
        session.removeAttribute ("Multaa");
        multaModal.detach();
    }

    @Listen("onPagar= #multaListBox")
    public void doPagar (ForwardEvent event)
    {
        EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,2);
        EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, 1);
        multa.setBibliotecario(user);
        multa.setEstadoMulta(estadoMulta);

        emprestimo.setEstadoDevolucao(estadoDevolucao);
        emprestimo.setComentario("");

        crudService.Save(multa);
        crudService.update(emprestimo);

        Clients.showNotification("Multa paga",null,null,null,5000);
    }

    @Listen("onRevogar = #multaListBox")
    public void doRevogar(ForwardEvent event)
    {
        EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,4);
        multa.setBibliotecario(user);
        multa.setEstadoMulta(estadoMulta);
        crudService.Save(multa);
        Clients.showNotification("Multa Revogada",null,null,null,5000);
    }
    @Listen("onDevolver = #multaListBox")
    public void doDevolver(ForwardEvent event)
    {
        Clients.showNotification("Obra devolvida e Multa nao paga",null,null,null,5000);
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
