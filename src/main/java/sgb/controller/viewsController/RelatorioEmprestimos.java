package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.controller.domainController.AreaCientificaController;
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.EstadoDevolucaoControler;
import sgb.domain.Emprestimo;
import sgb.domain.EstadoDevolucao;
import sgb.report.GerarRelatorio;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RelatorioEmprestimos extends SelectorComposer<Component> {
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");
    private EmprestimoController emprestimoController = (EmprestimoController) SpringUtil.getBean("emprestimoController");
    private EstadoDevolucaoControler estadoDevolucaoControler = (EstadoDevolucaoControler) SpringUtil.getBean("estadoDevolucaoControler");
    private ListModelList<Emprestimo> emprestimoListModel;
    private ListModelList<EstadoDevolucao> estadoDevolucaoListModelList;

    @Wire
    private Listbox emprestimoListBox;

    @Wire
    private Listbox estadoDevolucaoListBox;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        emprestimoListModel = new ListModelList<Emprestimo>(emprestimoController.getBorrowedBooks());


        EstadoDevolucao estadoDevolucao = new EstadoDevolucao();
        estadoDevolucao.setDescricao("Todos");
        estadoDevolucao.setIdestadodevolucao(0);
        estadoDevolucaoListModelList = new ListModelList<EstadoDevolucao>(estadoDevolucaoControler.getEstadoDevolucao());
        estadoDevolucaoListModelList.add(0,estadoDevolucao);
        estadoDevolucaoListModelList.addToSelection(estadoDevolucao);

        estadoDevolucaoListBox.setModel(estadoDevolucaoListModelList);
        emprestimoListBox.setModel(emprestimoListModel);
    }

    @Listen("onSelect = #estadoDevolucaoListBox")
    public void change(){
        EstadoDevolucao estadoDevolucao = estadoDevolucaoListBox.getSelectedItem().getValue();

        if (estadoDevolucao.getIdestadodevolucao()!=0){
            emprestimoListModel = new ListModelList<Emprestimo>(emprestimoController.getBorrowedBooks(estadoDevolucao.getIdestadodevolucao()));
        }else{
            emprestimoListModel = new ListModelList<Emprestimo>(emprestimoController.getBorrowedBooks());
        }

        emprestimoListBox.setModel(emprestimoListModel);
    }

    @Listen("onClick=#savePdf")
    public void exportToPDF() throws Exception, JRException, IOException
    {
        Filedownload.save(JasperExportManager.exportReportToPdf(gerarRelatorio.createPdf(emprestimoListModel)),"pdf","RelatorioEmprestimo.pdf");
    }


    @Listen("onClick=#saveExcell")
    public void exportToExcell() throws IOException, JRException {
        JRXlsExporter exporter = new JRXlsExporter();

        String filePath = "src/main/java/sgb/report/relatorioEmprestimo/xls/RelatorioEmprestimo.xls";
        File xlsFile = new File(filePath);

        if(xlsFile.exists()){
            xlsFile.delete();
        }

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, gerarRelatorio.createPdf(emprestimoListModel));
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
        exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);

        exporter.exportReport();

        Filedownload.save(xlsFile,"xls");
    }
}