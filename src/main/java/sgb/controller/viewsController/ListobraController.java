package sgb.controller.viewsController;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.annotation.Command;
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
import sgb.domain.*;
import sgb.service.CRUDService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListobraController extends SelectorComposer<Component> {
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Session session;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;

    private ListModelList<Obra> obraListModel;

    private ListModelList<Requisicao> cestaListModel = new ListModelList<Requisicao>();

    @Wire
    private Window listObra;

    @Wire
    private Listbox obraListBox;

    @Wire
    private Listbox cestaListBox;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        session = Sessions.getCurrent();

        session.setAttribute("qtdObrasRequisitadas", new Integer(getQtdObrasRequisitadas()));
        obraListModel = getObraListModel();
        cestaListModel = getCestaListModel();
        obraListBox.setModel(obraListModel);
        cestaListBox.setModel(cestaListModel);
    }


    public ListModelList<Obra> getObraListModel() {
        List<Obra> listaobra = crudService.getAll(Obra.class);
        return new ListModelList<Obra>(listaobra);
    }


    @Listen("onEliminarObra = #obraListBox")
    public void doEliminar(ForwardEvent event)
    {
        Clients.showNotification("Eliminar");

    }

    @Listen("onDetalheObra = #obraListBox")
    public void doDetalhe(ForwardEvent event)
    {
        Clients.showNotification("Detalhes Obra");
    }

    @Listen("onEditarObra = #obraListBox")
    public void doEditar(ForwardEvent event)
    {
        Clients.showNotification("Editar Obra");
    }


    @Listen("onAdicionarNaCesta = #obraListBox")
    public void doAdicionarNaCesta(ForwardEvent event)
    {
        if (temObrasPorDevolver())
        {
            Clients.showNotification("Voce tem obras por devolver");
            return;
        }

        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();
        Obra obra = (Obra) litem.getValue();
        insertOncestaListModel(obra);
    }

    @Listen("onRequisitarObra = #cestaListBox")
    public void doRequisitar(ForwardEvent event)
    {
        if (cestaListModel.size() == 0)
        {
            Clients.showNotification("A Cesta esta fazia");
            return;
        }


        // begin transactio
        for (Requisicao requisicao: cestaListModel)
        {
            Obra obra = crudService.findEntByJPQueryT("SELECT o FROM Obra o WHERE o.cota = '"+ requisicao.getObra().getCota()+"'", null);

            if (requisicao.getQuantidade() > obra.getQuantidade())
                continue;

            obra.setQuantidade(obra.getQuantidade() - requisicao.getQuantidade());

            emprestimo = new Emprestimo();
            emprestimoPK = new EmprestimoPK();
            estadoDevolucao = crudService.get(EstadoDevolucao.class, 1);
            estadoPedido= crudService.get(EstadoPedido.class, 1);

            emprestimoPK.setObra(requisicao.getObra());
            emprestimoPK.setUser(user);

            emprestimo.setEstadoDevolucao(estadoDevolucao);
            emprestimo.setEstadoPedido(estadoPedido);
            emprestimo.setEmprestimoPK(emprestimoPK);
            emprestimo.setComentario("--");
            emprestimo.setDataaprovacao(null);
            emprestimo.setDatadevolucao(null);
            emprestimo.setDataentrada(Calendar.getInstance());
            emprestimo.setQuantidade(requisicao.getQuantidade());

            try
            {
                crudService.update(obra);
                crudService.Save(emprestimo);

                int qtdR = ((Integer) session.getAttribute("qtdObrasRequisitadas")).intValue();
                qtdR += requisicao.getQuantidade();
                session.setAttribute("qtdObrasRequisitadas", new Integer(qtdR));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }

        cestaListModel.removeAll(cestaListModel);
        //ende transation

        Clients.showNotification("successful");
    }


    @Listen("onAumentarQtd = #cestaListBox")
    public void doAumentarQtd(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent();
        Requisicao requisicao = (Requisicao) litem.getValue();
        aumentarQtd(requisicao);
    }

    @Listen("onReduzirQtd = #cestaListBox")
    public void doReduzirQtd(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent();
        Requisicao requisicao = (Requisicao) litem.getValue();

        if (requisicao.getQuantidade() > 1)
            requisicao.setQuantidade(requisicao.getQuantidade() - 1);

        for (int i = 0; i < cestaListModel.size(); i++)
        {
            if ( cestaListModel.get(i).getObra().getCota().equals(requisicao.getObra().getCota()))
            {
                cestaListModel.remove(i);
                cestaListModel.add(i, requisicao);
                break;
            }
        }
    }


    @Listen("onRemoverDaCesta = #cestaListBox")
    public void doEliminarcesta(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();
        Requisicao requisicao = (Requisicao) litem.getValue();

        int pos = 0;

        for (int i = 0; i < cestaListModel.size(); i++ )
        {
            if (requisicao.getObra().getCota().equals(cestaListModel.get(i).getObra().getCota()))
                break;

            pos++;
        }

        cestaListModel.remove(pos);
    }

    public void insertOncestaListModel(Obra obra)
    {
        if (getQtdObrasRestantes() == 0)
            return;

        boolean obraExists = false;
        Requisicao requisicao = new Requisicao();

        for(int i = 0; i <  cestaListModel.size(); i++)
        {
            if(obra.getCota() == cestaListModel.get(i).getObra().getCota())
            {
                requisicao = cestaListModel.get(i);
                obraExists = true;
                break;
            }
        }

        if (!obraExists)
        {
            requisicao = new Requisicao();
            requisicao.setObra(obra);
            requisicao.setQuantidade(1);
            cestaListModel.add(requisicao);
        }
        else
        {
            aumentarQtd(requisicao);
        }
    }

    public int getQtdObrasNaCesta()
    {
        int qtdObrasNaCesta = 0;

        for (Requisicao requisicao: cestaListModel)
            qtdObrasNaCesta += requisicao.getQuantidade();

        return qtdObrasNaCesta;
    }

    public int getQtdMaxObras()
    {
        int qtdMaxObras  = 0;

        for ( Role role: user.getRoles())
            qtdMaxObras = Math.max(qtdMaxObras, role.getQtdMaxObras());
        return qtdMaxObras;
    }

    public int getQtdObrasRestantes()
    {
        return getQtdMaxObras() - (getQtdObrasNaCesta()
                +((Integer) session.getAttribute("qtdObrasRequisitadas")).intValue());
    }

    public void setObraListModel(ListModelList<Obra> obraListModel) {
        this.obraListModel = obraListModel;
    }

    public ListModelList<Requisicao> getCestaListModel()
    {
        return this.cestaListModel;
    }

    public void setCestaListModel(ListModelList<Requisicao> cestaListModel) {
        this.cestaListModel = cestaListModel;
    }

    public void aumentarQtd(Requisicao requisicao)
    {

        if (getQtdObrasRestantes() > 0)
            requisicao.setQuantidade(requisicao.getQuantidade() + 1);

        for (int i = 0; i < cestaListModel.size(); i++)
        {
            if ( cestaListModel.get(i).getObra().getCota().equals(requisicao.getObra().getCota()))
            {
                cestaListModel.remove(i);
                cestaListModel.add(i, requisicao);
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
}