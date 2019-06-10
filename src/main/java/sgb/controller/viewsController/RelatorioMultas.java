package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.controller.domainController.EstadoMultaControler;
import sgb.controller.domainController.MultaController;
import sgb.domain.EstadoMulta;
import sgb.domain.Multa;
import sgb.report.GerarRelatorio;

import javax.swing.plaf.synth.SynthRootPaneUI;
import java.io.File;
import java.io.IOException;

public class RelatorioMultas extends SelectorComposer<Component> {
    private EstadoMultaControler estadoMultaControler = (EstadoMultaControler) SpringUtil.getBean("estadoMultaControler");
    private MultaController multaController = (MultaController) SpringUtil.getBean("multaController");
    private ListModelList<Multa> multaListModelList;
    private ListModelList<EstadoMulta> estadoMultaListModelList;
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");

    @Wire
    private Listbox multasListbox;

    @Wire
    private Listbox estadoMultaListbox;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        multaListModelList = new ListModelList<Multa>(multaController.getMultas());

        EstadoMulta estadoMulta = new EstadoMulta();
        estadoMulta.setDescricao("Todos");
        estadoMulta.setIdestadomulta(0);
        estadoMultaListModelList = new ListModelList<EstadoMulta>(estadoMultaControler.getEstadoMultas());
        estadoMultaListModelList.add(0,estadoMulta);
        estadoMultaListModelList.addToSelection(estadoMulta);

        multasListbox.setModel(multaListModelList);
        estadoMultaListbox.setModel(estadoMultaListModelList);
    }

    @Listen("onSelect = #estadoMultaListbox")
    public void change(){
        EstadoMulta estadoMulta = estadoMultaListbox.getSelectedItem().getValue();

        if(estadoMulta.getIdestadomulta()!=0){
            multaListModelList = new ListModelList<Multa>(multaController.getFine(estadoMulta.getIdestadomulta()));
        }else{
            multaListModelList = new ListModelList<Multa>(multaController.getMultas());
        }

        multasListbox.setModel(multaListModelList);
    }

    @Listen("onClick=#savePdf")
    public void exportToPDF() throws Exception, JRException, IOException
    {
        Filedownload.save(JasperExportManager.exportReportToPdf(gerarRelatorio.createPdf1(multaListModelList)),"pdf","RelatorioMultas.pdf");
    }


    @Listen("onClick=#saveExcell")
    public void exportToExcell() throws IOException, JRException {
        JRXlsExporter exporter = new JRXlsExporter();

        String filePath = "src/main/java/sgb/report/relatorioMultas/xls/RelatorioMultas.xls";
        File xlsFile = new File(filePath);

        if(xlsFile.exists()){
            xlsFile.delete();
        }

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, gerarRelatorio.createPdf1(multaListModelList));
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();

        Filedownload.save(xlsFile,"xls");
    }


}
