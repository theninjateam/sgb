
package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.domain.Emprestimo;
import sgb.domain.Obra;
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

import java.util.List;

public class viewRequestModalController extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;


    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private ListModel emprestimoListModel;
    private Session session;

    @Wire
    private Listbox emprestimoListBox;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        session = Sessions.getCurrent();
        super.doAfterCompose(comp);
        emprestimoListModel = getEmprestimoListModel();
        emprestimoListBox.setModel(emprestimoListModel);
    }

    public ListModelList<Emprestimo> getEmprestimoListModel() {

        Requisicao r = (Requisicao) session.getAttribute("requisicoes");
        List<Emprestimo> lista = new ArrayList<>();

        for(Emprestimo e : r.getPedidos()) {
            List<Emprestimo> le = crudService.findByJPQuery("SELECT e FROM Emprestimo e WHERE e.emprestimoPK.user.id = " +
                    e.getEmprestimoPK().getUser().getId() +" and e.emprestimoPK.obra.cota = '"+e.getEmprestimoPK().getObra().getCota()+"'", null);

            for (Emprestimo emp : le)
                lista.add(emp);
        }

        return new ListModelList<Emprestimo>(lista);
    }

}
