package sgb.book;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zats.mimic.Client;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Window;
import sgb.domain.Obra;
import sgb.domain.ObraEliminadas;
import sgb.domain.ObraEliminadasPK;
import sgb.domain.Users;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ReduceBookViewModel
{
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Session session = Sessions.getCurrent();
    private int vanishedBooksQuantity;
    private Obra obra;
    private String description;

    @Init
    public void init() throws Exception
    {
        this.vanishedBooksQuantity = 0;
        this.obra = (Obra) this.session.getAttribute("obraToEdit");

        if (obra == null)
        {
            throw new Exception("Obra can not be null");
        }
    }

    @Command
    public void reduceVanishedBooksQuantity()
    {
        if (this.vanishedBooksQuantity > 0)
        {
            this.vanishedBooksQuantity = this.vanishedBooksQuantity - 1;
            BindUtils.postNotifyChange(null, null, this, "vanishedBooksQuantity");
        }
    }

    @Command
    public void increaseVanishedBooksQuantity()
    {
        if (this.vanishedBooksQuantity < obra.getQuantidade())
        {
            this.vanishedBooksQuantity = this.vanishedBooksQuantity + 1;
        }
        BindUtils.postNotifyChange(null, null, this, "vanishedBooksQuantity");
    }

    @Command
    public void exit(@BindingParam("modal") Window modal)
    {
        modal.detach();
    }

    @Command
    public void save(@BindingParam("modal") Window modal)
    {
        try
        {
            obra.setQuantidade(this.obra.getQuantidade() - this.vanishedBooksQuantity);

            Set<ObraEliminadas> listobraEliminadas = new HashSet<>();
            listobraEliminadas = obra.getObraEliminadas();

            ObraEliminadas obraEliminada = new ObraEliminadas();
            ObraEliminadasPK obraEliminadaPK = new ObraEliminadasPK();

            obraEliminadaPK.setObra(obra);
            obraEliminadaPK.setDataRegisto(Calendar.getInstance());

            obraEliminada.setObraEliminadasPK(obraEliminadaPK);
            obraEliminada.setObra(obra);
            obraEliminada.setQuantidade(this.vanishedBooksQuantity);
            obraEliminada.setDescricao(this.description);
            obraEliminada.setUser(this.user);

            listobraEliminadas.add(obraEliminada);

            obra.setObraEliminadas(listobraEliminadas);

            crudService.update(obra);
            modal.detach();
            Clients.showNotification("Feito");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Obra getObra()
    {
        return obra;
    }

    public void setObra(Obra obra)
    {
        this.obra = obra;
    }

    public int getVanishedBooksQuantity()
    {
        return vanishedBooksQuantity;
    }

    public void setVanishedBooksQuantity(int vanishedBooksQuantity)
    {
        this.vanishedBooksQuantity = vanishedBooksQuantity;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
