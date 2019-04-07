package sgb.controller.viewsController;

import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.AreaCientificaController;
import sgb.controller.domainController.ObraController;
import sgb.controller.domainController.ObraEliminadasController;
import sgb.controller.domainController.RegistroObraController;
import sgb.domain.*;
import sgb.report.GerarRelatorio;
import sgb.service.CRUDService;

//import org.zkoss.zk.chart.Charts;
//import org.zkoss.chart.model.CategoryModel;
//import org.zkoss.chart.model.DefaultCategoryModel;

import javax.management.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class RelatorioObras extends SelectorComposer<Component> {
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");;
    private ObraController obraController  = (ObraController) SpringUtil.getBean("obraController");;
    private RegistroObraController registroObraController  = (RegistroObraController) SpringUtil.getBean("registroObraController");;
    private ObraEliminadasController obraEliminadasController  = (ObraEliminadasController) SpringUtil.getBean("obraEliminadasController");;
    private AreaCientificaController areaCientificaController  = (AreaCientificaController) SpringUtil.getBean("areaCientificaController");;

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Boolean isNormalUser = true;




    private Listbox obraeliminadas;
    private Listbox obracategoria;
    private Listbox obrasregistadas;
    private Label qtdd;
    Calendar dataI = Calendar.getInstance();
    Calendar dataF = Calendar.getInstance();

    @Wire
    private Button save;

    @Wire
    private Datebox dataInicio;

    @Wire
    private Datebox dataFim;

    @Wire
    private Include idInclRelatorioObrasRegistadas;

    @Wire
    private Include idInclRelatorioObrasQuantidade;

    @Wire
    private Include idInclObrasEliminadas;

    @Wire
    private Listbox areaCientificaListBox;


    private ListModelList<RegistroObra> obrasregistadasListModel;
    private ListModelList<ObraEliminadas> obraEliminadasListModel;
    private ListModelList<ObraCategoria> obraCategoriaListModel;
    private ListModelList<AreaCientifica> areaCientificaListModel;



    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);

        idInclRelatorioObrasQuantidade.setSrc("views/relatorioobrasquantidade.zul");
        idInclObrasEliminadas.setSrc("views/relatorioobraseliminadas.zul");
        idInclRelatorioObrasRegistadas.setSrc("views/relatorioobrasregistadas.zul");

        obrasregistadas = (Listbox)idInclRelatorioObrasRegistadas.getFellow("obrasregistadas");
        obraeliminadas = (Listbox)idInclObrasEliminadas.getFellow("obraeliminadas");
        obracategoria = (Listbox)idInclRelatorioObrasQuantidade.getFellow("obracategoria");
        qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
        
        AreaCientifica a = new AreaCientifica();
        a.setDescricao("Todas Areas"); a.setIdarea(0);
        areaCientificaListModel = new ListModelList<AreaCientifica>(areaCientificaController.getAreaCientifica());
        areaCientificaListModel.add(0,a);
        areaCientificaListModel.addToSelection(a);
        areaCientificaListBox.setModel(areaCientificaListModel);
        setListModelsallData();
        setListBoxsModels();

    }

    @Listen("onSelect = #areaCientificaListBox")
    public void change() {

        List<RegistroObra> registroObras;
        AreaCientifica areaCientifica = areaCientificaListBox.getSelectedItem().getValue();

        if(areaCientifica.getIdarea()!=0){
           if(dataInicio.getValue() ==null)
               obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(areaCientifica));
           else{
               getUpdateDate();
               registroObras = registroObraController.getObrasByDate(dataI,dataF,areaCientifica);
               obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(registroObras,areaCientifica));
            }
        } else {

            setListModelsallData();
        }

        setListBoxsModels();
    }


    @Listen("onChange = #dataInicio;onChange = #dataFim")
    public void dataChange() {

        getUpdateDate();
        AreaCientifica areaCientifica = areaCientificaListBox.getSelectedItem().getValue();
        List<RegistroObra> registroObras = registroObraController.getObrasByDate(dataI,dataF,areaCientifica);

        if(areaCientifica.getIdarea()!=0){
            obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(registroObras,areaCientifica));
            obrasregistadasListModel = new ListModelList<RegistroObra>(registroObraController.getObrasRegistadas());
            obraEliminadasListModel =new ListModelList<ObraEliminadas>(obraEliminadasController.getObrasEliminadas());
        } else {
            setListModelsallData();
        }

        setListBoxsModels();
    }

    public void setListModelsallData(){

        List<RegistroObra> registroObras;

        if(dataInicio.getValue() ==null) {
            obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(null));
        }else {
            getUpdateDate();
            registroObras = registroObraController.getObrasByDate(dataI, dataF, null);
            obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(registroObras, null));
        }
        obrasregistadasListModel = new ListModelList<RegistroObra>(registroObraController.getObrasRegistadas());
        obraEliminadasListModel =new ListModelList<ObraEliminadas>(obraEliminadasController.getObrasEliminadas());

    }

    public void setListBoxsModels(){

        obracategoria.setModel(obraCategoriaListModel);
        obrasregistadas.setModel(obrasregistadasListModel);
        obraeliminadas.setModel(obraEliminadasListModel);
        qtdd.setValue(""+getQtddTotalObra(obraCategoriaListModel));
    }

    public int getQtddTotalObra(ListModelList<ObraCategoria> obraCategoriaListModel){
        int total=0;

        for (ObraCategoria obraCategoria : obraCategoriaListModel) {
            for(Obra o: obraCategoria.getObras())
                total +=o.getQuantidade();
        }

        return total;
    }

    public void getUpdateDate(){

        try {
            dataI.setTime(dataInicio.getValue());
            dataF.setTime(dataFim.getValue());
        }catch (Exception e){

        };

    }

    @Listen("onClick = #save")
    public void exportar() throws JRException, IOException {
        gerarRelatorio.createPdf(obraCategoriaListModel);
    }

}
