package sgb.controller.viewsController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
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
import sgb.domain.Emprestimo;
import sgb.domain.EstadoPedido;
import sgb.domain.Role;
import sgb.domain.Users;
import sgb.service.CRUDService;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Set;

public class Relatorio extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
//    private ListModelList<Emprestimo> relatorioListModel;
    private Boolean isNormalUser = true;
    private EmprestimoControllerSingleton emprestimoControllerSingleton = (EmprestimoControllerSingleton)
            SpringUtil.getBean("emprestimoControllerSingleton");
    @Wire
    private Listbox relatorioListBox;

    class Geral{
        String descricao;
        Integer qtd;

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public Integer getQtd() {
            return qtd;
        }

        public void setQtd(Integer qtd) {
            this.qtd = qtd;
        }
    }

    private ListModelList<Geral> relatorioListModel;



    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);
        relatorioListModel = new ListModelList<Geral>(getSomething());
        relatorioListBox.setModel(relatorioListModel);

    }

    public ListModelList<Geral> getSomething() {
         return  new ListModelList<Geral>( crudService.findByJPQuery("SELECT e.comentario, e.quantidade FROM Emprestimo e ", null));
    }


}
