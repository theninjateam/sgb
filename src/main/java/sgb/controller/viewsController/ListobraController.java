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
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    private Session session;
    private EmprestimoPK emprestimoPK;
    private Emprestimo emprestimo;
    private EstadoPedido estadoPedido;
    private EstadoDevolucao estadoDevolucao;
    private int qtdMaxObras;
    private ListModelList<Obra> obraListModel;

    private ListModelList<Requisicao> cestaListModel = new ListModelList<Requisicao>();


    @Wire
    private Button requisitar;

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

        obraListModel = getObraListModel();
        obraListBox.setModel(obraListModel);
//        cestaListBox.setModel(cestaListModel);
        //
        this.qtdMaxObras = getQtdMaxObras();

    }

    public ListModelList<Obra> getObraListModel() {
        List<Obra> listaobra = crudService.findByJPQuery("SELECT o FROM Obra o WHERE o.quantidade > 0", null);
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
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();
        Obra obra = (Obra) litem.getValue();

//        StringBuilder msg = new  StringBuilder();
//        boolean podeAdicionar = true;
//
//
//        if ( getcurrentQtdMaxObras() + 1 > this.qtdMaxObras)
//        {
//            msg.append("Voce so pode requisitar "+ this.qtdMaxObras+" obras no Maximo");
//            podeAdicionar = false;
//
//        }
//
//        if (!podeAdicionar)
//        {
//            Clients.showNotification(msg.toString());
//            return;
//        }

        Requisicao requisicao = new Requisicao();

        requisicao.setObra(obra);
        requisicao.setCurrentQtd(1);
        requisicao.setRangeQtd(this.qtdMaxObras);

        cestaListModel.add(requisicao);
    //    insertOnCestaListModel(obra);
    }


    @Listen("onClick = #requisitar")
    public void doRequisitar()
    {
        try
        {
            // begin transaction

            int size = cestaListModel.size();

            if (size == 0)
            {
                Clients.showNotification("A cesta esta vazia");
                return;
            }
            for (int i = 0; i < size  ; i++)
            {
                Listitem listitem = cestaListBox.getItemAtIndex(i);

                Listbox listbox = (Listbox) listitem.query("listbox");
                int qtd =  listbox.getSelectedItem().getValue();
                Requisicao requisicao = (Requisicao) listitem.getValue();

                Obra obra = crudService.get(Obra.class, requisicao.getObra().getCota());

                if(obra.getQuantidade() < qtd)
                    continue;

                emprestimo = new Emprestimo();
                emprestimoPK = new EmprestimoPK();

                estadoDevolucao = crudService.get(EstadoDevolucao.class, 1);
                estadoPedido= crudService.get(EstadoPedido.class, 1);
                emprestimoPK.setObra(requisicao.getObra());
                emprestimoPK.setUser(user);
                emprestimo.setEstadoDevolucao(estadoDevolucao);
                emprestimo.setEstadoPedido(estadoPedido);
                emprestimo.setEmprestimoPK(emprestimoPK);
                emprestimo.setComentario("requistou a(as) obra(s)");
                emprestimo.setDataaprovacao(null);
                emprestimo.setDataentrada(Calendar.getInstance());
                emprestimo.setQuantidade(qtd);

                obra.setQuantidade(obra.getQuantidade() - qtd);

                if(obra.getQuantidade() == 0)
                {
                    int position = -1;

                    for (int j = 0; j < obraListModel.size(); j++)
                    {
                        if ( obra.getCota().equals(obraListModel.get(j).getCota()))
                        {
                            position = j;
                            break;
                        }
                    }

                    if(position >= 0)
                        obraListModel.remove(position);
                }

                crudService.update(obra);
                crudService.Save(emprestimo);

            }

            Clients.showNotification("Requisicao feita com sucesso.");

            //end transsation
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Listen("onRemoverDaCesta = #cestaListBox")
    public void doEliminarobraRequisitar(ForwardEvent event)
    {
        Button btn = (Button)event.getOrigin().getTarget();
        Listitem litem = (Listitem)btn.getParent().getParent().getParent().getParent().getParent();

        Requisicao requisicao = (Requisicao) litem.getValue();

        removetFromCestaListModel(requisicao.getObra());
    }

    public void insertOnCestaListModel(Obra obra)
    {

        boolean obraExists = false;

        for(int i = 0; i <  cestaListModel.size(); i++)
        {
            if(obra.getCota() == cestaListModel.get(i).getObra().getCota())
            {
                obraExists = true;
                break;
            }
        }

        if (!obraExists)
        {
            Requisicao requisicao = new Requisicao();
            requisicao.setObra(obra);
            requisicao.setCurrentQtd(1);
            requisicao.setRangeQtd(this.qtdMaxObras - getcurrentQtdMaxObras());
            cestaListModel.add(requisicao);
       }
            upadateCestaListModel();

    }

    public void removetFromCestaListModel(Obra obra)
    {

        int position = -1;

        for (int i = 0; i < cestaListModel.size(); i++)
        {
            String cota = cestaListModel.get(i).getObra().getCota();
            if (obra.getCota().equals(cota))
            {
                position = i;
                break;
            }
        }

        if (position >= 0)
            cestaListModel.remove(position);
    }

    public int getQtdMaxObras()
    {
        int qtdMaxObras = 0;

        for (Role role: user.getRoles())
        {
            qtdMaxObras = Math.max(qtdMaxObras, role.getQtdmaxobras());
        }

        return qtdMaxObras;
    }

    public int getcurrentQtdMaxObras()
    {
        int currentQtdMaxObras = 0;

        for (int i = 0; i < this.cestaListModel.size(); i++)
        {

            int qtd = cestaListModel.get(i).getCurrentQtd();
            currentQtdMaxObras += qtd;
        }

        return currentQtdMaxObras;
    }

    public void upadateCestaListModel()
    {
        ListModelList<Requisicao> newCestaListModel = new ListModelList<Requisicao>();
        int resto  = this.qtdMaxObras - getcurrentQtdMaxObras();


        for (int i = 0; i <  this.cestaListModel.size(); i++)
        {
            int qtd = cestaListModel.get(i).getCurrentQtd();
            Requisicao temp = (Requisicao) cestaListModel.get(i);
            Requisicao requisicao = new Requisicao();

            requisicao.setObra(temp.getObra());
            requisicao.setCurrentQtd(temp.getCurrentQtd());

            requisicao.setRangeQtd(requisicao.getCurrentQtd() + resto);

            cestaListModel.set(i, requisicao);

            //newCestaListModel.add(requisicao);
        }

        //cestaListBox = new Listbox();
    //    cestaListBox.setModel(cestaListModel);
        //cestaListModel =  new ListModelList<Requisicao>(newCestaListModel);
        //
    }

    public ListModelList<Requisicao> getCestaListModel() {
        alert("ola");

        Requisicao requisicao = new Requisicao();
        requisicao.setObra(obraListModel.get(0));
        requisicao.setCurrentQtd(1);
        requisicao.setRangeQtd(this.qtdMaxObras);

        cestaListModel.add(requisicao);

        return this.cestaListModel;
    }

    public void setCestaListModel(ListModelList<Requisicao> cestaListModel) {
        this.cestaListModel = cestaListModel;
    }
}