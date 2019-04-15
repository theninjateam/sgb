package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.exolab.castor.dsml.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.exporter.pdf.PdfExporter;
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
import java.io.*;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.*;

import javax.management.Notification;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

//import org.zkoss.zk.chart.Charts;
//import org.zkoss.chart.model.CategoryModel;
//import org.zkoss.chart.model.DefaultCategoryModel;


public class RelatorioObras extends SelectorComposer<Component> {
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");;
    private ObraController obraController  = (ObraController) SpringUtil.getBean("obraController");;
    private RegistroObraController registroObraController  = (RegistroObraController) SpringUtil.getBean("registroObraController");;
    private ObraEliminadasController obraEliminadasController  = (ObraEliminadasController) SpringUtil.getBean("obraEliminadasController");;
    private AreaCientificaController areaCientificaController  = (AreaCientificaController) SpringUtil.getBean("areaCientificaController");;

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Boolean isNormalUser = true;
    private int selected = 0;
    private Listbox obraeliminadas;
    private Listbox obrasregistadas;
    private Listbox obracategoria;
    private Label qtdd;
    Calendar dataI = Calendar.getInstance();
    Calendar dataF = Calendar.getInstance();

    @Wire
    private Listbox areaCientificaListBox;

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
    private Tabbox obrasTabBox;

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
           if(dataInicio.getValue() ==null) {
               obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(areaCientifica));
               qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
           }else{
               getUpdateDate();
               registroObras = registroObraController.getObrasByDate(dataI,dataF,areaCientifica);
               obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(registroObras,areaCientifica));
               qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
           }
        } else {
            qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
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
            qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
        } else {
            qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
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
    public void exportar() throws Exception, JRException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfExporter exporter = new PdfExporter();
        exporter.export(this.obracategoria, out);

        Filedownload.save(out.toByteArray(), "pdf", "report.pdf");

        Filedownload.save(JasperExportManager.exportReportToPdf(gerarRelatorio.createPdf(obraCategoriaListModel,
                obrasregistadasListModel, obraEliminadasListModel, selected, qtdd.getValue())), "pdf", "report.pdf");

        out.close();
    }

    @Listen("onSelect = #obrasTabBox")
    public void knowSelectedTab(){
        selected = obrasTabBox.getSelectedTab().getIndex();
    }
}
