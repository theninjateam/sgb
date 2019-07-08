package sgb.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import sgb.controller.domainController.*;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class BasketViewModel
{
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Session session = Sessions.getCurrent();

    private Request request = (Request) SpringUtil.getBean("request");
    private MultaController multaController = (MultaController) SpringUtil.getBean("multaController");
    private RoleController roleController = (RoleController) SpringUtil.getBean("roleController");
    private ConfigControler configControler = (ConfigControler) SpringUtil.getBean("configControler");
    private EstadoMultaControler estadoMultaControler = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private EstadoDevolucaoControler estadoDevolucaoControler = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private EmprestimoController emprestimoController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoPedidoControler estadoPedidoControler = (EstadoPedidoControler) SpringUtil.getBean("estadoPedidoControler");

    private int basketBooksQuantity;
    private List<Item> items = new ArrayList<Item>();
    private boolean canAddToBasket;
    private boolean homeRequisition;
    private boolean internalRequisition;
    private boolean canShowRadios;
    private final int MINIMUM_NUMBER_OF_COPIES = this.configControler.MINIMUM_NUMBER_OF_COPIES;
    private Semaphore semaphore = new Semaphore(1);

    @Init
    public void init() throws Exception
    {

        if (this.session.getAttribute("basket") == null)
        {
            this.basketBooksQuantity = 0;
            this.canAddToBasket = this.getCanAddToBasket();
            this.internalRequisition = false;
            this.homeRequisition = false;
            this.canShowRadios = false;
            session.setAttribute("basket", this);

        }
        else
        {
            this.basketBooksQuantity = ((BasketViewModel) this.session.getAttribute("basket")).basketBooksQuantity;
            this.canAddToBasket = ((BasketViewModel) this.session.getAttribute("basket")).canAddToBasket;
            this.internalRequisition = ((BasketViewModel) this.session.getAttribute("basket")).internalRequisition;
            this.homeRequisition = ((BasketViewModel) this.session.getAttribute("basket")).homeRequisition;
            this.canShowRadios = ((BasketViewModel) this.session.getAttribute("basket")).canShowRadios;
            this.items = ((BasketViewModel) this.session.getAttribute("basket")).items;
        }

    }

    @NotifyChange("*")
    @Command("setHomeRequisition")
    public void setHomeRequisition()
    {
        this.homeRequisition = true;
        this.internalRequisition = false;

        for (int i = 0; i < this.items.size() && !this.items.isEmpty(); i++)
        {
            this.items.get(i).setInternalRequisition(this.internalRequisition);
            this.items.get(i).setHomeRequisition(this.homeRequisition);

            if (this.items.get(i).isCanDoHomeRequisition())
            {
                this.items.get(i).setCanBeViewed(true);
            }
            else
            {
                this.items.get(i).setCanBeViewed(false);
            }
        }
    }

    @NotifyChange("*")
    @Command("setInternalRequisition")
    public void setInternalRequisition()
    {
        this.homeRequisition = false;
        this.internalRequisition = true;

        for (int i = 0; i < this.items.size() && !this.items.isEmpty(); i++)
        {
            this.items.get(i).setInternalRequisition(this.internalRequisition);
            this.items.get(i).setHomeRequisition(this.homeRequisition);

            if (this.items.get(i).isInternalRequisition())
            {
                this.items.get(i).setCanBeViewed(true);
            }
            else
            {
                this.items.get(i).setCanBeViewed(false);
            }
        }
    }

    @NotifyChange("*")
    @Command("add")
    public void add(@BindingParam("obra") Obra obra ) throws Exception
    {
        if (getCanAddToBasket() && !reachedMaximumCopiesPerBook(obra))
        {
            Obra o = this.crudService.get(Obra.class, obra.getCota());
            Item item = new Item();

            item.setObra(obra);
            item.setQuantidade(1);
            item.setHomeRequisition(this.homeRequisition);
            item.setInternalRequisition(this.internalRequisition);
            item.setCanDoInternalRequisition(this.request.canDoInternalRequisition(obra, item.getQuantidade()));
            item.setCanDoHomeRequisition(this.request.canDoHomeRequisition(obra, item.getQuantidade()));

            this.items.add(item);

            if (this.homeRequisition)
            {
                setHomeRequisition();
            }
            else if (this.internalRequisition)
            {
                setInternalRequisition();
            }

            this.basketBooksQuantity = this.basketBooksQuantity + 1;
        }
    }

    /*
    * must be a transation
    * */
    @NotifyChange({"basketBooksQuantity", "canAddToBasket", "homeRequisition", "internalRequisition", "canShowRadios"})
    @Command("request")
    public void request() throws Exception
    {
        if (this.basketBooksQuantity  > 0)
        {
            this.request.request(this.items, this.user);

            this.homeRequisition = false;
            this.internalRequisition = false;
            this.canShowRadios  = false;
            this.basketBooksQuantity = 0;

            this.items = new ArrayList<Item>();
            this.session.setAttribute("basket", null);
            BindUtils.postNotifyChange(null, null, this, "items");

            Clients.showNotification("Feito",null,null,null,5000);
        }
        else
        {
            Clients.showNotification("A Cesta de livros esta vazia",null,null,null,5000);
        }

    }

    @NotifyChange({"basketBooksQuantity", "canAddToBasket", "homeRequisition", "internalRequisition", "canShowRadios"})
    @Command("remove")
    public void remove(@BindingParam("item") Item item ) throws Exception
    {
        semaphore.acquire();

        List<Item> its = new ArrayList<Item>(this.items);
        this.items = new ArrayList<Item>();

        for (Item i: its)
        {
            if (!i.getObra().getCota().equals(item.getObra().getCota()))
            {
                this.items.add(i);
            }
        }

        this.basketBooksQuantity = this.basketBooksQuantity - 1;

        semaphore.release();

        if (this.basketBooksQuantity == 0)
        {
            this.homeRequisition = false;
            this.internalRequisition = false;
        }

        BindUtils.postNotifyChange(null, null, this, "items");
    }

    @NotifyChange({"items"})
    @Command("lineUp")
    public void lineUp(@BindingParam("item") Item item,  @BindingParam("lineUp") boolean lineUp)
    {
        int pos  = this.items.indexOf(item);
        this.items.get(pos).setLineUp(lineUp);
    }

    /*****************************************
    * Auxiliar Methods
    * ***************************************/

    public boolean getCanAddToBasket() throws Exception
    {
        boolean isStudent = false;
        boolean isTeacher = false;
        Role role = null;

        for (Role r: user.getRoles())
        {
            if (r.getRoleId() == this.roleController.STUDENT)
            {
                isStudent = true;
                role = r;
            }
            else  if (r.getRoleId() == this.roleController.TEACHER)
            {
                isTeacher = true;
                role = r;
            }
        }

        if (this.multaController.getFine(this.user, this.estadoMultaControler.NOT_PAID).size() > 0)
        {
            return false;
        }
        else if (this.getAllBooks() >= this.getMaximumBooksAllowed())
        {
            return false;
        }
        else if(!isStudent && !isTeacher)
        {
            return false;
        }

        return true;
    }

    public int getBasketBooksQuantity() throws Exception
    {
        return basketBooksQuantity;
    }

    public int getAllBooks() throws Exception
    {
        int allBooks =  this.getBasketBooksQuantity();

        List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

        emprestimos.addAll(this.emprestimoController.getBorrowedBooks(this.user, this.estadoDevolucaoControler.NOT_RETURNED));
        emprestimos.addAll(this.emprestimoController.getRequest(this.user, this.estadoPedidoControler.ON_WAINTING_QUEUE));
        emprestimos.addAll(this.emprestimoController.getRequest(this.user, this.estadoPedidoControler.PENDING_BOOKING));
        emprestimos.addAll(this.emprestimoController.getRequest(this.user, this.estadoPedidoControler.PENDING_MINI_BOOKING));

        for (Emprestimo e: emprestimos)
            allBooks = allBooks + e.getQuantidade();

        return allBooks;
    }

    public int getMaximumBooksAllowed()
    {
            return this.configControler.MAXIMUM_NUMBER_OF_COPIES;
    }

    public List<Item> getItems() throws Exception
    {
        return this.items;
    }

    public void setItems(List<Item> items)
    {
        this.items = items;
    }

    public boolean reachedMaximumCopiesPerBook(Obra obra)
    {
        int qtd = 0;

        for (Item item: this.items)
        {
            if (item.getObra().getCota().equals(obra.getCota()))
            {
                qtd = qtd + item.getQuantidade();
            }
        }

        return qtd == this.configControler.MAXIMUM_COPIES_PER_BOOK? true : false;
    }

    public boolean isHomeRequisition()
    {
        return homeRequisition;
    }

    public void setHomeRequisition(boolean homeRequisition)
    {
        this.homeRequisition = homeRequisition;
    }

    public boolean isInternalRequisition()
    {
        return internalRequisition;
    }

    public void setInternalRequisition(boolean internalRequisition)
    {
        this.internalRequisition = internalRequisition;
    }

    public boolean isCanShowRadios()
    {
        return !this.items.isEmpty();
    }

    public void setCanShowRadios(boolean canShowRadios) {
        this.canShowRadios = canShowRadios;
    }

    public int getMINIMUM_NUMBER_OF_COPIES()
    {
        return MINIMUM_NUMBER_OF_COPIES;
    }
}