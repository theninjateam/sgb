package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.controller.domainController.EstadoPedidoControler;
import sgb.deadline.BorrowedBooksDeadline;
import sgb.domain.Emprestimo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.request.Request;
import sgb.service.CRUDService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Calendar;

public class ListPedido extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private boolean isStudent = false;
    private BorrowedBooksDeadline bBDeadline = (BorrowedBooksDeadline) SpringUtil.getBean("borrowedBooksDeadline");
    private ListModelList<Emprestimo> pedidoListModel;
    private ListModel<EstadoPedido> estadopedidoModel;
    private Boolean isNormalUser = true;
    private Request request = (Request) SpringUtil.getBean("request");
    private EstadoPedidoControler ePController = (EstadoPedidoControler) SpringUtil.getBean("estadoPedidoControler");
    private EmprestimoController eController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler eDController = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");



    @Wire
    private Listbox pedidoListBox;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);

        isNormalUser = isNormalUser();

        if (isNormalUser) {
            ComposeUserNormal();
        }
        else {
           ComposeUserAdmin();
        }
    }

    public void ComposeUserAdmin(){
        List<Emprestimo> pedidos = eController.getRequest(ePController.PENDING_MINI_BOOKING);
        pedidos.addAll(eController.getRequest(ePController.PENDING_BOOKING));

        pedidoListModel = new ListModelList<Emprestimo>(pedidos);

        pedidoListBox.setModel(pedidoListModel);
    }

    public void ComposeUserNormal() {
        List<Emprestimo> pedidos = eController.getRequest(this.user, ePController.PENDING_MINI_BOOKING);
        pedidos.addAll(eController.getRequest(this.user, ePController.PENDING_BOOKING));
        pedidoListModel = new ListModelList<Emprestimo>(pedidos);

        pedidoListBox.setModel(pedidoListModel);
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


    @Listen("onEliminarEmprestimo = #pedidoListBox")
    public void doEliminar(ForwardEvent event)
    {
        if (isNormalUser) {
            Emprestimo emp = (Emprestimo) event.getData();

            Messagebox.show("Tem certeza que deseja eliminar esse pedido ?", "delete pedido",
                    Messagebox.YES + Messagebox.NO, Messagebox.QUESTION,
                    new EventListener<Event>() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            if (Messagebox.ON_YES.equals(event.getName())) {
                                pedidoListModel.remove(emp);
                                crudService.delete(emp);
                                Clients.showNotification("Pedido eliminado com sucesso", null, null, null, 5000);
                            }
                        }
                    });
        }else {
            Clients.showNotification("Precisa ser Utente para executar essa acao ", null, null, null, 5000);
        }

    }

    @Listen("onReprovarEmprestimo = #pedidoListBox")
    public void doReprovar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Emprestimo emp = (Emprestimo) event.getData();

            EstadoPedido estadoPedido = crudService.get(EstadoPedido.class,ePController.REJECTED);
            emp.setEstadoPedido(estadoPedido);
            emp.setDataaprovacao(Calendar.getInstance());
            pedidoListModel.remove(emp);
            crudService.update(emp);
            Clients.showNotification("Pedido reprovado com sucesso ", null, null, null, 5000);
        }
    }

    @Listen("onAprovarEmprestimo = #pedidoListBox")
    public void doAprovar(ForwardEvent event)
    {
        if(isNormalUser) {
            Clients.showNotification("Precisa ser Bibliotecario para executar essa acao ", null, null, null, 5000);
        } else {
            Emprestimo emp = (Emprestimo) event.getData();

            EstadoPedido estadoPedido = crudService.get(EstadoPedido.class,ePController.ACCEPTED);
            EstadoDevolucao estadoDevolucao = crudService.get(EstadoDevolucao.class,eDController.NAO_DEVOLVIDO);
            emp.setEstadoPedido(estadoPedido);
            emp.setDataaprovacao(Calendar.getInstance());
            emp.setBibliotecario(user); //
            emp.setEstadoDevolucao(estadoDevolucao);

            Set<Role> roles = emp.getEmprestimoPK().getUtente().getRoles();

            for(Role role: roles) {
                if(role.getRole().equals("student"))
                    isStudent= true;
                else
                    isStudent = false;
            }
            emp.setDatadevolucao(bBDeadline.getDeadline(emp)); // Calcula data de devolucao
            pedidoListModel.remove(emp);
            crudService.update(emp);
            Clients.showNotification("Pedido aprovado com sucesso ", null, null, null, 5000);
        }
    }


    @Listen("onShowOperacoes = #pedidoListBox")
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

    public Boolean Reserved(Emprestimo emp) {
        Boolean aBoolean = false;


        if(emp.getEstadoPedido().getIdestadopedido() == ePController.PENDING_BOOKING){
            aBoolean = true;
        }

        return aBoolean;
    }


}
