package sgb.controller.viewsController;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.domain.*;
import sgb.service.CRUDService;

//import org.zkoss.zk.chart.Charts;
//import org.zkoss.chart.model.CategoryModel;
//import org.zkoss.chart.model.DefaultCategoryModel;

import java.util.List;

public class Relatorio extends SelectorComposer<Component> {

    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
//    private ListModelList<Emprestimo> relatorioListModel;
    private Boolean isNormalUser = true;

    private Listbox emprestimo;


    private Listbox obraeliminadas;


    private Listbox obraregistadas;

    @Wire
    private Include idInclRelatorioGeral;

    @Wire
    private Include idInclRegistroObras;

    @Wire
    private Include idInclObrasEliminadas;


    private ListModelList<Geral> emprestimoListModel;
    private ListModelList<ObraEliminadas> obraEliminadasListModel;
    private ListModelList<RegistroObra> obrasRegistadasListModel;


    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);

        idInclRegistroObras.setSrc("views/relatorioobrasregistadas.zul");
        idInclObrasEliminadas.setSrc("views/relatorioobraseliminadas.zul");
        idInclRelatorioGeral.setSrc("views/relatoriogeral.zul");

        emprestimo = (Listbox)idInclRelatorioGeral.getFellow("emprestimo");
        obraeliminadas = (Listbox)idInclObrasEliminadas.getFellow("obraeliminadas");
        obraregistadas = (Listbox)idInclRegistroObras.getFellow("obraregistadas");

        emprestimoListModel  = new ListModelList<Geral> ();
        getEmprestimo();
        emprestimo.setModel(emprestimoListModel);

        obraEliminadasListModel =new ListModelList<ObraEliminadas>(getObraEliminadas());
        obraeliminadas.setModel(obraEliminadasListModel);

        obrasRegistadasListModel = new ListModelList<RegistroObra>(getObraRegistadas());
        obraregistadas.setModel(obrasRegistadasListModel);




    }
    private ListModelList<ObraEliminadas> getObraEliminadas() {
        List<ObraEliminadas> lista = crudService.getAll(ObraEliminadas.class);
        return new ListModelList<ObraEliminadas>(lista);
    }

    private ListModelList<RegistroObra> getObraRegistadas() {
        List<RegistroObra> lista = crudService.getAll(RegistroObra.class);
        return new ListModelList<RegistroObra>(lista);
    }



    public void getEmprestimo() {

        List<Emprestimo> aa = crudService.getAll(Emprestimo.class);

        int emprealizados = aa.size();
        int empaprovados = 0;
        int empreprovados =0;

        Geral emprealizado = new Geral();
        Geral empaprovado= new Geral();
        Geral empReprovado= new Geral();

        for(Emprestimo emp: aa) {
            if(emp.getEstadoPedido().getIdestadopedido()==3)
                empaprovados ++;
            if(emp.getEstadoPedido().getIdestadopedido()==2)
                empreprovados ++;

        }

        emprealizado.setDescricao("Emprestimo Realizados ");
        emprealizado.setQtd(emprealizados);

        empaprovado.setDescricao("Emprestimo Aprovados");
        empaprovado.setQtd(empaprovados);

        empReprovado.setDescricao("Emprestimo Reprovados");
        empReprovado.setQtd(empreprovados);

        emprestimoListModel.add(emprealizado);
        emprestimoListModel.add(empaprovado);
        emprestimoListModel.add(empReprovado);

    }


}
