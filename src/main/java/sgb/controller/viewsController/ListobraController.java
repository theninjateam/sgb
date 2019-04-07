package sgb.controller.viewsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.Template;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.request.Request;
import sgb.service.CRUDService;
import sgb.controller.domainController.*;

import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author Fonseca, Emerson
 */

public class ListobraController extends SelectorComposer<Component>
{
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Request request = (Request) SpringUtil.getBean("request");

    private EstadoPedidoControler ePController = (EstadoPedidoControler) SpringUtil.getBean("estadoPedidoControler");

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private EmprestimoController eController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler eDController = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private EstadoMultaControler eMController = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private MultaController mController = (MultaController) SpringUtil.getBean("multaController");
    private Session session;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;
    private EstadoRenovacao estadoRenovacao;
    private ListModelList<Obra> obras;
    private ListModelList<Item> cestaListModel = new ListModelList<Item>();
    private ListModelList<Obra> detalheobra;
    private boolean isHomeRequisition;

    @Wire
    private Button buttonPesquisar;

    @Wire
    protected Grid  gridPesquisar;

    @Wire
    private Button buttonVoltar;

    @Wire
    private Vlayout cesta;

    @Wire
    private Vlayout listObras;
    @Wire
    private Div divCesta;

    @Wire
    private Textbox textboxPesquisar;

    @Wire
    private Window listObra;

    @Wire
    private Grid griddetalhe;

    @Wire
    private Listbox obraShow;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();
        boolean c = isNormalUser();
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

    
    @Listen("onPesquisar = #gridPesquisar")
    public void doPesquisa(ForwardEvent event)
    {

    }

    @Listen("onVoltar = #buttonVoltar")
    public void listarObras(ForwardEvent event)
    {
        cesta.setVisible(false);
        griddetalhe.setVisible(false);
        listObras.setVisible(true);
        buttonPesquisar.setVisible(true);
        textboxPesquisar.setVisible(true);
        buttonVoltar.setVisible(false);
    }

    @Listen("onShowCesta = #divCesta")
    public void showCestaListBox(ForwardEvent event)
    {
        listObras.setVisible(false);
        buttonPesquisar.setVisible(false);
        textboxPesquisar.setVisible(false);
        griddetalhe.setVisible(false);
        cesta.setVisible(true);
        buttonVoltar.setVisible(true);
    }

    @Listen("onShowOperacoes = #gridListObra")
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

    @Listen("onEliminarObra = #gridListObra")
    public void doEliminar(ForwardEvent event)
    {
        String cota = (String) event.getData();
        Obra obra = this.crudService.get(Obra.class, cota);

        session.setAttribute("obraToEdite", obra);

        Window window =(Window) Executions.createComponents("/views/modalEliminarExemplar.zul", null, null);
        window.doModal();
    }

    @Listen("onDetalheObra = #gridListObra")
    public void doDetalhe(ForwardEvent event)
    {
        detalheobra = new ListModelList<>();
        listObras.setVisible(false);
        buttonPesquisar.setVisible(false);
        textboxPesquisar.setVisible(false);
        buttonVoltar.setVisible(true);
        cesta.setVisible(false);

        String cota = (String) event.getData();
        Obra obra = this.crudService.get(Obra.class, cota);

        detalheobra.add(obra);
        obraShow.setModel(detalheobra);
        griddetalhe.setVisible(true);
    }

    @Listen("onAdicionarExemplares = #gridListObra")
    public void doEditar(ForwardEvent event)
    {
        String cota = (String) event.getData();
        Obra obra = this.crudService.get(Obra.class, cota);

        session.setAttribute("obraToEdite", obra);

        Window window =(Window) Executions.createComponents("/views/UpdateObra.zul", null, null);
        window.doModal();
    }


    @Listen("onPesquisar = #textboxPesquisar")
    public void doAutoPesquisar(ForwardEvent event)
    {
        pesquisar(textboxPesquisar.getValue());
    }

    @Listen("onPesquisar = #buttonPesquisar")
    public void doPesquisar(ForwardEvent event)
    {
        pesquisar(textboxPesquisar.getValue());
    }

    public void pesquisar(String keys)
    {
        if (textboxPesquisar.getValue().isEmpty())
        {
            obras.removeAll(obras);
            obras.addAll(getObras());
        }
        else
        {
            obras.removeAll(obras);

            for (Obra obra: getObras())
            {
                for ( String key: keys.split(" "))
                {
                    if( obra.getTitulo().toLowerCase().contains(key.toLowerCase()))
                    {
                        obras.add(obra);
                        break;
                    }
                }
            }
        }
    }

    public ListModelList<Obra> getObras()
    {
        List<Obra> listaobra = crudService.getAll(Obra.class);
        return new ListModelList<Obra>(listaobra);
    }
}