package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zats.mimic.Client;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import sgb.deadline.Deadline;
import sgb.domain.*;
import sgb.fine.Fine;
import sgb.request.Request;
import sgb.service.CRUDService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Fonseca, Emerson
 */

public class ListPedidoExemplarController extends SelectorComposer<Component>
{
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Request request = (Request) SpringUtil.getBean("request");

    private EstadoPedidoControler ePController = (EstadoPedidoControler) SpringUtil.getBean("estadoPedidoControler");

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private EmprestimoController eController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler eDController = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private EstadoMultaControler eMController = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private MultaController mController = (MultaController) SpringUtil.getBean("multaController");
    private Deadline deadline = (Deadline) SpringUtil.getBean("deadline");
    private BorrowedBooksDeadline bBDeadline = (BorrowedBooksDeadline) SpringUtil.getBean("borrowedBooksDeadline");
    private Session session;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;
    private EstadoRenovacao estadoRenovacao;
    private ListModelList<Emprestimo> emprestimos;


    private Multa multa;
    private ConfigControler configControler =(ConfigControler) SpringUtil.getBean("configControler");
    private Fine fine = (Fine) SpringUtil.getBean("fine");

    private Boolean isNormalUser = true;


    @Wire
    private Window listObra;

    @Wire
    private Vlayout listObras;

    @Wire
    private Listbox EmprestimoListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();
        boolean c = isNormalUser();

        isNormalUser = isNormalUser();

        emprestimos = (ListModelList<Emprestimo>) getEmprestimos();
        EmprestimoListBox.setModel(emprestimos);
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


    public ListModelList<Emprestimo> getEmprestimos() {
        List<Emprestimo> listemprestimo;
        if(isNormalUser()) {
           listemprestimo = eController.getRequest(this.user,ePController.ACCEPTED, eDController.NAO_DEVOLVIDO);

           return new ListModelList<Emprestimo>(listemprestimo);
       }else {
           listemprestimo = eController.getRequest(ePController.ACCEPTED, eDController.NAO_DEVOLVIDO);
//            emprestimos = (ListModelList<Emprestimo>) listemprestimo;
//            EmprestimoListBox.setModel(emprestimos);
           return new ListModelList<Emprestimo>(listemprestimo);
       }
    }

    @Listen("onDevolver = #gridListEmprestimoMobile, #gridListEmprestimoDesktop")
    public void doDevolver(ForwardEvent event)
    {

        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Emprestimo emp = (Emprestimo) event.getData();

            if (fine.wasFinedBefore(emp.getEmprestimoPK())) {

                multa = mController.getFine(emp.getEmprestimoPK());

                fine.fine(emp,Calendar.getInstance());

                session.setAttribute("Multa", multa);
                Boolean isForDetails = false;
                session.setAttribute("ForDetais", isForDetails);

                Window window = (Window) Executions.createComponents("/views/multamodal.zul", null, null);
                window.setClosable(true);
                window.doModal();
            } else {
                EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class, eDController.DEVOLVIDO);
                emp.setEstadoDevolucao(estadoDevolucao);
                emp.setComentario("");
//                emprestimos.remove(emp);

                crudService.update(emp);

                Clients.showNotification("Obra devolvida a tempo", null, null, null, 5000);

            }
        }

    }
    @Listen("onDetalheEmprestimo = #gridListEmprestimoMobile, #gridListEmprestimoDesktop")
    public void doDetalhes(ForwardEvent event)
    {
        Emprestimo emp = (Emprestimo) event.getData();

        session.setAttribute("EmprestimoMultado", emp);
        Boolean isForDetails = true;
        session.setAttribute("ForDetais", isForDetails);
        Window window =(Window) Executions.createComponents("/views/multamodal.zul", null, null);
        window.setClosable(true);
        window.doModal();

    }
    @Listen("onRenovarEmprestimo = #gridListEmprestimoMobile, #gridListEmprestimoDesktop")
    public void doRenovar(ForwardEvent event)
    {
        if(isNormalUser) {
            /*
             * Metodo a ser descutido
             * Ideia 1 : O usuario submete o pedido de renovacao e o sistema aprova;
             * Ideia 2 : O usuario vai ter com o bibliotecario e esse acede o pedido e renova;
             * Necessidade de um Metodo que verifica se existe alguem a espera daquele livro para desabilitar o
             * Botao
             */
            Emprestimo emp = (Emprestimo) event.getData();
            estadoRenovacao = crudService.get(EstadoRenovacao.class, 2);
            emp.setEstadoRenovacao(estadoRenovacao);
            crudService.update(emp);
            Clients.showNotification("Renovacao do Emprestimo", null, null, null, 5000);
        } else {
            Clients.showNotification("Precisa ser Utente para executar essa acao ", null, null, null, 5000);
        }

    }





    @Listen("onShowOperacoes = #gridListEmprestimoMobile")
    public void doShowOperacoes(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Div div = (Div) btn.getNextSibling();

        if (div.isVisible())
        {
            btn.setLabel("Outras operações");
            div.setVisible(false);
        }
        else
        {
            btn.setLabel("Ocultar operações");
            div.setVisible(true);
        }
    }

    public Boolean Expired(Emprestimo emp) {
        Boolean aBoolean = false;

        Calendar DataLimite = bBDeadline.getDeadline(emp);
       if(deadline.exceededDeadline(DataLimite,Calendar.getInstance())){
           aBoolean = true;
       }

       return aBoolean;
    }


    public String dataConvert (Calendar dataa) {

        SimpleDateFormat timeFormatter = new SimpleDateFormat("'('HH:mm:s')'");
        DateFormat dateFormatter = new SimpleDateFormat();
        Locale MOZAMBIQUE = new Locale("pt","MZ");
        StringBuilder builder = new StringBuilder();


        dateFormatter = DateFormat.getDateInstance(DateFormat.LONG, MOZAMBIQUE);

        builder.append(dateFormatter.format(dataa.getTime()));
        builder.append("\n");
        builder.append(timeFormatter.format(dataa.getTime()));

        String dataEntrada = builder.toString();

        return dataEntrada;
    }


}