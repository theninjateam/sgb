package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;
import sgb.controller.domainController.*;

import java.util.Calendar;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Fonseca, Emerson
 */

public class ListobraController extends SelectorComposer<Component>
{
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Session session;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;
    private EstadoRenovacao estadoRenovacao;
    private ListModelList<Obra> obraListModel;
    private ListModelList<Item> cestaListModel = new ListModelList<Item>();
    private ListModelList<Obra> detalheobra;
    private EmprestimoControllerSingleton emprestimoControllerSingleton =  EmprestimoControllerSingleton.getInstance(crudService);

    @Wire
    private Button buttonPesquisar;

    @Wire
    protected Grid  gridPesquisar;

    @Wire
    private Grid gridListObra;

    @Wire
    private Button buttonVoltar;
    @Wire
    private Grid gridCesta;

    @Wire
    private Div divCesta;

    @Wire
    private Label qtdObrasNaCesta;
    @Wire
    private Textbox textboxPesquisar;

    @Wire
    private Window listObra;

    @Wire
    private Listbox obraListBox;

    @Wire
    private Listbox cestaListBox;

    @Wire
    private Grid griddetalhe;

    @Wire
    private Listbox obraShow;

    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        obraListModel = getObraListModel();
        cestaListModel = getCestaListModel();
        obraListBox.setModel(obraListModel);
        cestaListBox.setModel(cestaListModel);
        this.qtdObrasNaCesta.setValue("0");
    }


    public ListModelList<Obra> getObraListModel() {
        List<Obra> listaobra = crudService.getAll(Obra.class);
        return new ListModelList<Obra>(listaobra);
    }



    @Listen("onPesquisar = #gridPesquisar")
    public void doPesquisa(ForwardEvent event)
    {

    }

    @Listen("onVoltar = #buttonVoltar")
    public void listarObras(ForwardEvent event)
    {
        gridCesta.setVisible(false);
        griddetalhe.setVisible(false);
        gridListObra.setVisible(true);
        buttonPesquisar.setVisible(true);
        textboxPesquisar.setVisible(true);
        buttonVoltar.setVisible(false);
    }

    @Listen("onShowCestaListBox = #divCesta")
    public void showCestaListBox(ForwardEvent event)
    {
        gridListObra.setVisible(false);
        buttonPesquisar.setVisible(false);
        textboxPesquisar.setVisible(false);
        griddetalhe.setVisible(false);
        gridCesta.setVisible(true);
        buttonVoltar.setVisible(true);
    }

    @Listen("onShowOperacoes = #obraListBox")
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

    @Listen("onEliminarObra = #obraListBox")
    public void doEliminar(ForwardEvent event)
    {

        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem =  (Listitem) getListitem(btn);
        Obra obra = (Obra) litem.getValue();
        obra = crudService.get(Obra.class, obra.getCota());

        session.setAttribute("obraToEdite", obra);

        Window window =(Window) Executions.createComponents("/views/modalEliminarExemplar.zul", null, null);
        window.doModal();


//        Button btn = (Button)event.getOrigin().getTarget();
//        Listitem litem = (Listitem) getListitem(btn);
//        Obra obra = (Obra) litem.getValue();
//        Messagebox.show("Tem certeza que deseja eliminar a obra ?", "deletar obra",
//                Messagebox.YES + Messagebox.NO, Messagebox.QUESTION,
//                new EventListener<Event>() {
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        if (Messagebox.ON_YES.equals(event.getName())) {
//                            obraListModel.remove(obra);
////                            obra.setAutores(null);
//                            obra.setAutores(null);
//                            crudService.delete(obra);
//                            Clients.showNotification("Obra eliminado com sucesso",null,null,null,5000);
//                        }
//                    }
//                });

    }

    @Listen("onDetalheObra = #obraListBox")
    public void doDetalhe(ForwardEvent event)
    {
//        Clients.showNotification("Detalhes Obra");
        detalheobra = new ListModelList<>();
        gridListObra.setVisible(false);
        buttonPesquisar.setVisible(false);
        textboxPesquisar.setVisible(false);
        buttonVoltar.setVisible(true);
        gridCesta.setVisible(false);
//        divCesta.setVisible(false);

        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem) getListitem(btn);
        Obra obra = (Obra) litem.getValue();

        for (Obra o : getObraListModel())
        {
            if (o.getCota().equals(obra.getCota()))
            {
                obra = o;
                break;
            }
        }

        detalheobra.add(obra);
        detalheobra.addSelection(obra);
        obraShow.setModel(detalheobra);
        griddetalhe.setVisible(true);

    }

    @Listen("onEditarObra = #obraListBox")
    public void doEditar(ForwardEvent event)
    {

        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem =  (Listitem) getListitem(btn);
        Obra obra = (Obra) litem.getValue();
        obra = crudService.get(Obra.class, obra.getCota());

        session.setAttribute("obraToEdite", obra);

        Window window =(Window) Executions.createComponents("/views/UpdateObra.zul", null, null);
        window.doModal();
    }


    @Listen("onAdicionarNaCesta = #obraListBox")
    @Transactional
    public void doAdicionarNaCesta(ForwardEvent event)
    {
        if (temObrasPorDevolver())
        {
            Clients.showNotification("Voce tem obras por devolver",null,null,null,5000);
            return;
        }

        try
        {
            Button btn = (Button)event.getOrigin().getTarget();
            Listitem litem =  (Listitem) getListitem(btn);
            Obra obra = (Obra) litem.getValue();
            insertOncestaListModel(obra);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Listen("onRequisitarObra = #cestaListBox")
    public void doRequisitar(ForwardEvent event)
    {
        if (cestaListModel.size() == 0)
        {
            Clients.showNotification("A Cesta esta vazia",null,null,null,5000);
            return;
        }

        List<Emprestimo> emprestimos = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = "+
                user.getId()+" and e.estadoDevolucao.idestadodevolucao = 1", null);

        // begin transaction
        for (Item item : cestaListModel)
        {
            boolean exists = false;

            emprestimo = new Emprestimo();
            emprestimoPK = new EmprestimoPK();
            estadoDevolucao = crudService.get(EstadoDevolucao.class, 1);
            estadoPedido= crudService.get(EstadoPedido.class, 1);
            estadoRenovacao = crudService.get(EstadoRenovacao.class,1);

            emprestimoPK.setObra(item.getObra());
            emprestimoPK.setUser(user);
            emprestimoPK.setDataentrada(Calendar.getInstance());

            emprestimo.setEstadoDevolucao(estadoDevolucao);
            emprestimo.setEstadoPedido(estadoPedido);
            emprestimo.setEmprestimoPK(emprestimoPK);
            emprestimo.setComentario("--");
            emprestimo.setDataaprovacao(null);
            emprestimo.setDatadevolucao(null);
            emprestimo.setQuantidade(item.getQuantidade());
            emprestimo.setEstadoRenovacao(estadoRenovacao);
            emprestimo.setDatarenovacao(null);
            emprestimo.setDatadevolucaorenovacao(null);


            try
            {
                for (Emprestimo e: emprestimos)
                {
                    if (e.getEmprestimoPK().getObra().getCota().
                        equals(emprestimoPK.getObra().getCota())
                        && e.getEmprestimoPK().getUser().getId() == emprestimoPK.getUser().getId())
                    {
                        e.setQuantidade(e.getQuantidade() + emprestimo.getQuantidade());
                        crudService.update(e);
                        exists = true;
                        break;
                    }
                }

                if (!exists) { crudService.Save(emprestimo); }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

        cestaListModel.removeAll(cestaListModel);
        this.qtdObrasNaCesta.setValue(""+getQtdObrasNaCesta());
        //ende transation

        Clients.showNotification("successful",null,null,null,5000);
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
            obraListModel.removeAll(obraListModel);
            obraListModel.addAll(getObraListModel());
        }
        else
        {
            obraListModel.removeAll(obraListModel);

            for (Obra obra: getObraListModel())
            {
                for ( String key: keys.split(" "))
                {
                    if( obra.getTitulo().toLowerCase().contains(key.toLowerCase()))
                    {
                        obraListModel.add(obra);
                        break;
                    }
                }
            }
        }
    }

    @Listen("onAumentarQtd = #cestaListBox")
    public void doAumentarQtd(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem) getListitem(btn);;
        Item item = (Item) litem.getValue();
        aumentarQtd(item);

        this.qtdObrasNaCesta.setValue(""+getQtdObrasNaCesta());
    }

    @Listen("onReduzirQtd = #cestaListBox")
    public void doReduzirQtd(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem) getListitem(btn);;
        Item item = (Item) litem.getValue();

        if (item.getQuantidade() > 1)
            item.setQuantidade(item.getQuantidade() - 1);

        for (int i = 0; i < cestaListModel.size(); i++)
        {
            if ( cestaListModel.get(i).getObra().getCota().equals(item.getObra().getCota()))
            {
                cestaListModel.remove(i);
                cestaListModel.add(i, item);
                break;
            }
        }

        this.qtdObrasNaCesta.setValue(""+getQtdObrasNaCesta());
    }


    @Listen("onRemoverDaCesta = #cestaListBox")
    public void doEliminarcesta(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem) getListitem(btn);
        Item item = (Item) litem.getValue();

        int pos = 0;

        for (int i = 0; i < cestaListModel.size(); i++ )
        {
            if (item.getObra().getCota().equals(cestaListModel.get(i).getObra().getCota()))
                break;

            pos++;
        }

        cestaListModel.remove(pos);

        this.qtdObrasNaCesta.setValue(""+getQtdObrasNaCesta());
    }

    public void insertOncestaListModel(Obra obra)
    {
        if (getQtdObrasRestantes() == 0)
        {
            Clients.showNotification("Voce so pode requisitar '"+ getQtdMaxObras()+"' obras no maximo",null,null,null,5000);
            return;
        }
        else if ( getQtdExemplaresRequisitados(obra)+
                getQtdExemplaresNaCesta(obra) + 1 > obra.getQuantidade() )
        {
            Clients.showNotification("So existem '"+ obra.getQuantidade() +"' exemplares dessa obra",null,null,null,5000);
            return;
        }

        boolean obraExists = false;
        Item item = new Item();

        for(int i = 0; i <  cestaListModel.size(); i++)
        {
            if(obra.getCota().equals(cestaListModel.get(i).getObra().getCota()))
            {
                item = cestaListModel.get(i);
                obraExists = true;
                break;
            }
        }

        if (!obraExists)
        {
            item = new Item();
            item.setObra(obra);
            item.setQuantidade(1);
            cestaListModel.add(item);
        }
        else
        {
            aumentarQtd(item);
        }

        this.qtdObrasNaCesta.setValue(""+getQtdObrasNaCesta());

    }

    public int getQtdObrasNaCesta()
    {
        int qtdObrasNaCesta = 0;

        for (Item item : cestaListModel)
            qtdObrasNaCesta += item.getQuantidade();

        return qtdObrasNaCesta;
    }

    public int getQtdMaxObras()
    {
        int qtdMaxObras  = 0;

        for ( Role role: user.getRoles())
            qtdMaxObras = Math.max(qtdMaxObras, role.getQtdMaxObras());
        return qtdMaxObras;
    }

    /*
    * quantidade de obras que um utilizador ainda pode requisitar
    * */
    public int getQtdObrasRestantes()
    {
        return getQtdMaxObras() - (getQtdObrasNaCesta()
                +(getQtdObrasRequisitadas()));
    }

    public void setObraListModel(ListModelList<Obra> obraListModel) {
        this.obraListModel = obraListModel;
    }

    public ListModelList<Item> getCestaListModel()
    {
        return this.cestaListModel;
    }

    public void setCestaListModel(ListModelList<Item> cestaListModel) {
        this.cestaListModel = cestaListModel;
    }

    public void aumentarQtd(Item item)
    {

        if (getQtdObrasRestantes() == 0)
        {
            Clients.showNotification("Voce so pode requisitar '"+ getQtdMaxObras()+"' obras no maximo",null,null,null,5000);
            return;
        }
        else if (getQtdExemplaresRequisitados(item.getObra())
                + getQtdExemplaresNaCesta(item.getObra()) +  1 > item.getObra().getQuantidade() )
        {
            Clients.showNotification("So existem '"+ item.getObra().getQuantidade() +"' exemplares dessa obra",null,null,null,5000);
            return;
        }

        item.setQuantidade(item.getQuantidade() + 1);

        for (int i = 0; i < cestaListModel.size(); i++)
        {
            if ( cestaListModel.get(i).getObra().getCota().equals(item.getObra().getCota()))
            {
                cestaListModel.remove(i);
                cestaListModel.add(i, item);
                break;
            }
        }
    }


    public  boolean temObrasPorDevolver()
    {
        List<Emprestimo> emprestimos = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = "+
                user.getId()+" and e.estadoDevolucao.idestadodevolucao = 2", null);

        return emprestimos.size() > 0 ?  true : false;
    }

    public int getQtdObrasRequisitadas()
    {
        int qtd = 0;

        List<Emprestimo> emprestimos = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = "+
                user.getId()+" and e.estadoDevolucao.idestadodevolucao = 1", null);

        for (Emprestimo e: emprestimos)
            qtd += e.getQuantidade();

        return qtd;
    }

    public int getQtdExemplaresRequisitados(Obra obra)
    {
        int qtd = 0;

        List<Emprestimo> emprestimos = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = "+
                user.getId()+" and e.estadoDevolucao.idestadodevolucao = 1 and e.emprestimoPK.obra.cota = '"+obra.getCota() +"'", null);

        for (Emprestimo e: emprestimos)
            qtd += e.getQuantidade();

        return qtd;
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

    public int getQtdExemplaresNaCesta(Obra obra)
    {

        for ( Item item: cestaListModel)
        {
            if (item.getObra().getCota().equals(obra.getCota()))
            {
                return item.getQuantidade();
            }
        }
        return 0;
    }
}