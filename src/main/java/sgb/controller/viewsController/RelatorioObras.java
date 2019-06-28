package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.exolab.castor.dsml.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
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
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");
    private ObraController obraController  = (ObraController) SpringUtil.getBean("obraController");
    private RegistroObraController registroObraController  = (RegistroObraController) SpringUtil.getBean("registroObraController");
    private ObraEliminadasController obraEliminadasController  = (ObraEliminadasController) SpringUtil.getBean("obraEliminadasController");
    private AreaCientificaController areaCientificaController  = (AreaCientificaController) SpringUtil.getBean("areaCientificaController");

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

    @Listen("onClick=#savePdf")
    public void show() throws JRException, IOException {

        String reportName = null;

        if(selected == 0)
            reportName = "RelatorioObrasQuantidade";
        else if(selected == 1)
            reportName = "RelatorioObrasRegistadas";
        else
            reportName = "RelatorioObrasEliminadas";


        byte [] arr = JasperExportManager.exportReportToPdf(gerarRelatorio.createPdf(obraCategoriaListModel,
                obrasregistadasListModel, obraEliminadasListModel, selected, qtdd.getValue()));
        AMedia media = new AMedia(reportName, "pdf", "application/pdf", arr);
        final Window window = new Window();
        window.setClosable(true);
        window.setSizable(false);
        Iframe iframe = new Iframe();
        iframe.setContent(media);
        Borderlayout borderlayout = new Borderlayout();
        North north = new North();
        Toolbar toolbar = new Toolbar();

        toolbar.setWidth("100%");


        toolbar.setAlign("end");


        Toolbarbutton close = new Toolbarbutton("Exit");
        close.setMode("overlapped");

        close.setTooltiptext("Sair");
        close.setClass("w3-btn w3-light-grey");
        close.addEventListener("onClick", (Event t) -> {         window.onClose();     });


        toolbar.appendChild(close);
        north.appendChild(toolbar);
        Center cntr = new Center();
        cntr.appendChild(iframe);
        borderlayout.appendChild(cntr);
        borderlayout.resize();
        window.appendChild(toolbar);
        window.appendChild(borderlayout);
        iframe.setWidth("100%");
        iframe.setHeight("100%");
        window.setWidth("100%");
        window.setHeight("100%");

        window.setPage(getPage());
        window.doModal();
    }

    public void exportToPDF() throws Exception, JRException, IOException
    {
        String reportName = null;

        if(selected == 0)
            reportName = "RelatorioObrasQuantidade";
        else if(selected == 1)
            reportName = "RelatorioObrasRegistadas";
        else
            reportName = "RelatorioObrasEliminadas";

        Filedownload.save(JasperExportManager.exportReportToPdf(gerarRelatorio.createPdf(obraCategoriaListModel,
                obrasregistadasListModel, obraEliminadasListModel, selected, qtdd.getValue()))
                , "pdf"
                , reportName+".pdf");
    }


    @Listen("onClick=#saveExcell")
    public void exportToExcell() throws IOException, JRException {
        String reportName = null;
        JRXlsExporter exporter = new JRXlsExporter();

        if(selected == 0)
            reportName = "RelatorioObrasQuantidade";
        else if(selected == 1)
            reportName = "RelatorioObrasRegistadas";
        else
            reportName = "RelatorioObrasEliminadas";

        String filePath = "src/main/java/sgb/report/xlsFiles/"+reportName+".xls";
        File xlsFile = new File(filePath);

        if(xlsFile.exists()){
            xlsFile.delete();
        }

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, gerarRelatorio.createPdf(obraCategoriaListModel,
                obrasregistadasListModel, obraEliminadasListModel, selected, qtdd.getValue()));
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();

        Filedownload.save(xlsFile,"xls");
    }


    @Listen("onSelect = #obrasTabBox")
    public void knowSelectedTab(){
        selected = obrasTabBox.getSelectedTab().getIndex();
    }
}