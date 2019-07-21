package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RelatorioObras extends SelectorComposer<Component> {
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");
    private ObraController obraController  = (ObraController) SpringUtil.getBean("obraController");
    private RegistroObraController registroObraController  = (RegistroObraController) SpringUtil.getBean("registroObraController");
    private ObraEliminadasController obraEliminadasController  = (ObraEliminadasController) SpringUtil.getBean("obraEliminadasController");
    private AreaCientificaController areaCientificaController  = (AreaCientificaController) SpringUtil.getBean("areaCientificaController");
    private AreaCientifica areaCientifica;
    private List<RegistroObra> registroObras;
    private List<ObraEliminadas> obraEliminadas;
    private int selected = 0;
    private Listbox obraeliminadas;
    private Listbox obrasregistadas;
    private Listbox obracategoria;
    private Label qtdd;
    private String dateFiltered;
    private String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("jasperFiles/relatorioObras/relatorio.jrxml");
    private String reportName = "RelatorioObrasQuantidade";
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

        areaCientifica = new AreaCientifica();
        areaCientifica.setDescricao("Todas Areas"); areaCientifica.setIdarea(0);
        areaCientificaListModel = new ListModelList<AreaCientifica>(areaCientificaController.getAreaCientifica());
        areaCientificaListModel.add(0,areaCientifica);
        areaCientificaListModel.addToSelection(areaCientifica);
        areaCientificaListBox.setModel(areaCientificaListModel);
        setListModelsallData();
        setListBoxsModels();
    }

    @Listen("onSelect = #areaCientificaListBox")
    public void change() {
        areaCientifica = areaCientificaListBox.getSelectedItem().getValue();

        setListModelsallData();
        setListBoxsModels();
    }

    @Listen("onChange = #dataInicio;onChange = #dataFim")
    public void dataChange() {
        getUpdateDate();

        setListModelsallData();
        setListBoxsModels();
    }

    public void setListModelsallData(){
        getUpdateDate();

        if(areaCientifica.getIdarea()!=0){
            obraEliminadas = obraEliminadasController.getObrasEliminadasByDate(dataI, dataF, areaCientifica);
            registroObras = registroObraController.getObrasByDate(dataI, dataF, areaCientifica);

            obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(registroObras, areaCientifica));
        }else{
            obraEliminadas = obraEliminadasController.getObrasEliminadasByDate(dataI, dataF, null);
            registroObras = registroObraController.getObrasByDate(dataI, dataF, null);

            obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(registroObras, null));
        }
        obrasregistadasListModel = new ListModelList<RegistroObra>(registroObras);
        obraEliminadasListModel =new ListModelList<ObraEliminadas>(obraEliminadas);
        qtdd = (Label) idInclRelatorioObrasQuantidade.getFellow("qtdd");
        dateFiltered = "Lista de "+dataConvert(dataI)+" a "+dataConvert(dataF);
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

    public Date reduzDataActual(){
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DAY_OF_MONTH, -30);

        return c.getTime();
    }

    public String dataConvert (Calendar dataa) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder = new StringBuilder();

        builder.append(dateFormatter.format(dataa.getTime()));

        return builder.toString();
    }

    @Listen("onClick=#savePdf")
    public void show() throws JRException, IOException {
        byte [] arr = JasperExportManager.exportReportToPdf(gerarRelatorio.createPdfObras(obraCategoriaListModel
                , obrasregistadasListModel
                , obraEliminadasListModel
                , selected
                , qtdd.getValue()
                , path
                , dateFiltered));

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

    @Listen("onClick=#saveExcell")
    public void exportToExcell() throws IOException, JRException {
        JRXlsExporter exporter = new JRXlsExporter();

        String filePath = "xlsFiles/"+reportName+".xls";
        File xlsFile = new File(filePath);

        if(xlsFile.exists()){
            xlsFile.delete();
        }

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, gerarRelatorio.createPdfObras(obraCategoriaListModel,
                obrasregistadasListModel, obraEliminadasListModel, selected, qtdd.getValue(), path, dateFiltered));
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();

        Filedownload.save(xlsFile,"xls");
    }

    @Listen("onSelect = #obrasTabBox")
    public void knowSelectedTab() throws IOException {
        selected = obrasTabBox.getSelectedTab().getIndex();

        if(selected == 0){
            reportName = "RelatorioObrasQuantidade";
            path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("jasperFiles/relatorioObras/relatorio.jrxml");
        }
        else if(selected == 1){
            reportName = "RelatorioObrasRegistadas";
            path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("jasperFiles/relatorioObras/relatorioObrasReg.jrxml");
        }
        else{
            reportName = "RelatorioObrasEliminadas";
            path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("jasperFiles/relatorioObras/relatorioObrasEli.jrxml");
        }
    }
}