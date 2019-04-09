package sgb.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import sgb.controller.domainController.AreaCientificaController;
import sgb.controller.domainController.ObraController;
import sgb.domain.AreaCientifica;
import sgb.domain.ObraCategoria;
import sgb.service.CRUDService;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerarRelatorio {
    private CRUDService crudService;

    public GerarRelatorio(CRUDService crudService){
        this.crudService = crudService;
    }

    public void createPdf(ListModelList<ObraCategoria> listModelList) throws JRException, IOException {

        Map parametros = new HashMap();
        List<ObraCategoria> lista = new ArrayList<>();

        String path = "src/main/java/sgb/report/relatorio.jrxml";

        JasperCompileManager.compileReportToFile("src/main/java/sgb/report/relatorio2.jrxml","src/main/java/sgb/report/relatorio2.jasper");

        String subreportPath = new File("src/main/java/sgb/report/relatorio2.jasper").getCanonicalPath();

        for(ObraCategoria o: listModelList){
            lista.add(o);
        }

        parametros.put("pathSubreport", subreportPath);

        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista));

        JasperViewer.viewReport(jasperPrint, false);
    }

}
