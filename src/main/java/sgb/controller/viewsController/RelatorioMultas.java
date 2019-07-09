package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.controller.domainController.EmprestimoController;
import org.zkoss.zul.*;
import sgb.controller.domainController.EstadoMultaControler;
import sgb.controller.domainController.MultaController;
import sgb.fine.Fine;
import sgb.domain.EstadoMulta;
import sgb.domain.Multa;
import sgb.report.GerarRelatorio;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RelatorioMultas extends SelectorComposer<Component> {
    private EstadoMultaControler estadoMultaControler = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private EmprestimoController eController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private MultaController multaController = (MultaController) SpringUtil.getBean("multaController");
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");
    private Fine fine = (Fine) SpringUtil.getBean("fine");
    private ListModelList<EstadoMulta> estadoMultaListModelList;
    private ListModelList<Multa> multaListModelList;
    private List<Multa> multaList;
    private EstadoMulta estadoMulta;
    String dateFiltered;
    Calendar dataI = Calendar.getInstance();
    Calendar dataF = Calendar.getInstance();

    @Wire
    private Label totalToPay;

    @Wire
    private Datebox dataInicio;

    @Wire
    private Datebox dataFim;

    @Wire
    private Listbox multasListbox;

    @Wire
    private Listbox estadoMultaListbox;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        estadoMulta = new EstadoMulta();
        estadoMulta.setDescricao("Todos");
        estadoMulta.setIdestadomulta(0);
        estadoMultaListModelList = new ListModelList<EstadoMulta>(estadoMultaControler.getEstadoMultas());
        estadoMultaListModelList.add(0,estadoMulta);
        estadoMultaListModelList.addToSelection(estadoMulta);

        estadoMultaListbox.setModel(estadoMultaListModelList);
        setListModelsallData();
        setListBoxsModels();
    }

    @Listen("onClick=#savePdf")
    public void show() throws JRException {
        byte [] arr = JasperExportManager.exportReportToPdf(gerarRelatorio.createPdfMulta(multaListModelList
                , "src/main/java/sgb/report/relatorioMultas/relatorio.jrxml"
                , Double.parseDouble(totalToPay.getValue())
                , dateFiltered));
        AMedia media = new AMedia("RelatorioMultas", "pdf", "application/pdf", arr);
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

        String filePath = "src/main/java/sgb/report/relatorioMultas/xls/RelatorioMultas.xls";
        File xlsFile = new File(filePath);

        if(xlsFile.exists()){
            xlsFile.delete();
        }

        exporter.setParameter(JRExporterParameter.JASPER_PRINT
                , gerarRelatorio.createPdfMulta(multaListModelList
                        , "src/main/java/sgb/report/relatorioMultas/relatorio.jrxml"
                        , Double.parseDouble(totalToPay.getValue())
                        , dateFiltered));
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();

        Filedownload.save(xlsFile,"xls");
    }

    @Listen("onChange = #dataInicio;onChange = #dataFim")
    public void dataChange() {
        getUpdateDate();

        multaList = multaController.getMultasByDate(dataI, dataF, estadoMulta);

        setListModelsallData();
        setListBoxsModels();
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

    @Listen("onSelect = #estadoMultaListbox")
    public void change(){
        estadoMulta = estadoMultaListbox.getSelectedItem().getValue();

        setListModelsallData();
        setListBoxsModels();
    }

    public void setListModelsallData(){
        getUpdateDate();

        if(estadoMulta.getIdestadomulta()!=0){
            multaList = multaController.getMultasByDate(dataI, dataF, estadoMulta);
        }else{
            multaList = multaController.getMultasByDate(dataI, dataF, null);
        }
        multaListModelList = new ListModelList<Multa>(multaList);
        dateFiltered = "Lista de "+dataConvert(dataI)+" a "+dataConvert(dataF);
    }

    public void setListBoxsModels(){
        multasListbox.setModel(multaListModelList);
        totalToPay.setValue(""+fine.totalDinheiro(multaList)+"");
        fine.updateDelayDays(multaList);
    }
}