package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.AreaCientificaController;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.domain.Emprestimo;
import sgb.domain.EstadoDevolucao;
import sgb.report.GerarRelatorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RelatorioEmprestimos extends SelectorComposer<Component> {
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");
    private EmprestimoController emprestimoController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler estadoDevolucaoControler = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private ListModelList<Emprestimo> emprestimoListModel;
    private ListModelList<EstadoDevolucao> estadoDevolucaoListModelList;
    private EstadoDevolucao estadoDevolucao;
    private List<Emprestimo> emprestimoList;
    Calendar dataI = Calendar.getInstance();
    Calendar dataF = Calendar.getInstance();
    String dateFiltered;

    @Wire
    private Datebox dataInicio;

    @Wire
    private Datebox dataFim;

    @Wire
    private Listbox emprestimoListBox;

    @Wire
    private Listbox estadoDevolucaoListBox;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        estadoDevolucao = new EstadoDevolucao();
        estadoDevolucao.setDescricao("Todos");
        estadoDevolucao.setIdestadodevolucao(0);
        estadoDevolucaoListModelList = new ListModelList<EstadoDevolucao>(estadoDevolucaoControler.getEstadoDevolucao());
        estadoDevolucaoListModelList.add(0,estadoDevolucao);
        estadoDevolucaoListModelList.addToSelection(estadoDevolucao);

        estadoDevolucaoListBox.setModel(estadoDevolucaoListModelList);
        setListModelsallData();
        setListBoxsModels();
    }

    @Listen("onSelect = #estadoDevolucaoListBox")
    public void change(){
        estadoDevolucao = estadoDevolucaoListBox.getSelectedItem().getValue();

        setListModelsallData();
        setListBoxsModels();
    }

    @Listen("onChange = #dataInicio;onChange = #dataFim")
    public void dataChange() {
        setListModelsallData();
        setListBoxsModels();
    }

    public void setListModelsallData(){
        getUpdateDate();

        if(estadoDevolucao.getIdestadodevolucao()!=0){
            emprestimoList = emprestimoController.getBorrowedBooksByDate(dataI, dataF, estadoDevolucao);
        }else{
            emprestimoList = emprestimoController.getBorrowedBooksByDate(dataI, dataF, null);
        }
        emprestimoListModel = new ListModelList<Emprestimo>(emprestimoList);
        dateFiltered = "Lista de "+dataConvert(dataI)+" a "+dataConvert(dataF);
    }

    public void setListBoxsModels(){
        emprestimoListBox.setModel(emprestimoListModel);
    }

    @Listen("onClick=#savePdf")
    public void show() throws JRException {
        byte [] arr = JasperExportManager.exportReportToPdf(gerarRelatorio.createPdfEmprestimo(emprestimoListModel
                , Executions.getCurrent().getDesktop().getWebApp().getRealPath("jasperFiles/relatorioEmprestimo/relatorio.jrxml")
                , dateFiltered));
        AMedia media = new AMedia("RelatorioEmprestimo", "pdf", "application/pdf", arr);

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

        String filePath = Executions.getCurrent().getDesktop()
                .getWebApp().getRealPath("jasperFiles/relatorioEmprestimo/xls/RelatorioEmprestimo.xls");
        File xlsFile = new File(filePath);


        if(xlsFile.exists()){
            xlsFile.delete();
        }

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, gerarRelatorio.createPdfEmprestimo(emprestimoListModel
                , Executions.getCurrent().getDesktop().getWebApp().getRealPath("jasperFiles/relatorioEmprestimo/relatorio.jrxml")
                , dateFiltered));
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();

        Filedownload.save(xlsFile,"xls");
    }

    public String dataConvert (Calendar dataa) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder = new StringBuilder();

        builder.append(dateFormatter.format(dataa.getTime()));

        return builder.toString();
    }

    public Date reduzDataActual(){
        Calendar c = Calendar.getInstance();

        c.add(Calendar.DAY_OF_MONTH, -30);

        return c.getTime();
    }

    public void getUpdateDate(){
        try {
            dataI.setTime(dataInicio.getValue());
            dataF.setTime(dataFim.getValue());
        }catch (Exception e){

        };
    }
}