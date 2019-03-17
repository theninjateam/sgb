package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.EmprestimoControllerSingleton;
import sgb.domain.*;
import sgb.service.CRUDService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import javax.swing.plaf.PanelUI;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ListEmprestimo extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModelList<Emprestimo> emprestimoListModel;
//    private ListModel<EstadoPedido> estadopedidoModel;
    private Session session;

    private EmprestimoControllerSingleton emprestimoControllerSingleton = (EmprestimoControllerSingleton)
            SpringUtil.getBean("emprestimoControllerSingleton");


    private Boolean isNormalUser = true;
    private EstadoRenovacao estadoRenovacao;
    @Wire
    private Listbox emprestimoListBox;

    @Wire
    private Listbox estadopedidoListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        Set<Role> userrole =user.getRoles();

        for(Role role : userrole) {
            if(role.getRole().equals("ADMIN"))
                isNormalUser = false;
        }
        if (isNormalUser) {
            ComposeUserNormal();
        }
        else {
            ComposeUserAdmin();
        }

    }

    public void ComposeUserAdmin(){
        emprestimoListModel = new ListModelList<Emprestimo>(emprestimoControllerSingleton.getRequisicoes(3));
        emprestimoListBox.setModel(emprestimoListModel);
    }

    public void ComposeUserNormal() {
        emprestimoListModel = new ListModelList<Emprestimo>(emprestimoControllerSingleton.getRequisicoes(this.user, 3));
        emprestimoListBox.setModel(emprestimoListModel);
    }


    @Listen("onNotificarUtente = #emprestimoListBox")
    public void doNotificarUtente(ForwardEvent event)
    {
         Clients.showNotification(" Enviar email a notificar utente da devolucao do livo",null,null,null,5000);
    }
    @Listen("onDevolver = #emprestimoListBox")
    public void doDevolver(ForwardEvent event)
    {
//        Clients.showNotification("Devolver Obra",null,null,null,5000);
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent().getParent();
        Emprestimo emp = (Emprestimo) litem.getValue();

        multar(emp);
        Window window =(Window) Executions.createComponents("/views/multamodal.zul", null, null);
        window.setClosable(true);
        window.doModal();

    }

    @Listen("onDetalheEmprestimo = #emprestimoListBox")
    public void doDetalhes(ForwardEvent event)
    {
        Clients.showNotification("Detalhes do Emprestimo",null,null,null,5000);
    }

    @Listen("onRenovarEmprestimo = #emprestimoListBox")
    public void doRenovar(ForwardEvent event)
    {
        Button btn = (Button) event.getOrigin().getTarget();
        Listitem litem = (Listitem) btn.getParent().getParent().getParent();
        Emprestimo emp = (Emprestimo) litem.getValue();
        estadoRenovacao = crudService.get(EstadoRenovacao.class,2);
        emp.setEstadoRenovacao(estadoRenovacao);
        crudService.update(emp);
        Clients.showNotification("Renovacao do Emprestimo",null,null,null,5000);
    }

    public void multar (Emprestimo emprestimo) {
        MultaPK multaPK =new MultaPK();
        Multa multa = new Multa();
        EstadoMulta estadoMulta = crudService.get(EstadoMulta.class,2);

        multaPK.setObra(emprestimo.getEmprestimoPK().getObra());
        multaPK.setUser(emprestimo.getEmprestimoPK().getUser());
        multaPK.setDataentradapedido(emprestimo.getEmprestimoPK().getDataentrada());

        multa.setDataemissao(Calendar.getInstance());
        multa.setMultaPK(multaPK);
        multa.setDataemprestimo(emprestimo.getDataaprovacao());
        multa.setEstadoMulta(estadoMulta);

        int diaatraso = -1 * (int)Duration.between(Calendar.getInstance().toInstant(), emprestimo.getDatadevolucao().toInstant()).toDays();

        multa.setDiasatraso(diaatraso );

        Float taxaD = Float.parseFloat(crudService.get(Config.class,"DAILY_RATE_FINE").getValor());
        multa.setTaxadiaria(taxaD);

        multa.setValorpago((diaatraso*taxaD));

        session.setAttribute("Multaa", multa);

    }


}
