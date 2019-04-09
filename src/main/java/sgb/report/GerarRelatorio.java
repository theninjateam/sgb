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
import sgb.domain.ObraEliminadas;
import sgb.domain.RegistroObra;
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

    public void createPdf(ListModelList<ObraCategoria> obraCategoriaListModelList,
                          ListModelList<RegistroObra> obrasregistadasListModel,
                          ListModelList<ObraEliminadas> obraEliminadasListModel, int selected, String value) throws JRException, IOException {

        String path = null;
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;
        switch (selected) {
            case 0:{
                Map parametros = new HashMap();
                List<ObraCategoria> lista = new ArrayList<>();

                path = "src/main/java/sgb/report/relatorio.jrxml";

                JasperCompileManager.compileReportToFile("src/main/java/sgb/report/relatorio2.jrxml", "src/main/java/sgb/report/relatorio2.jasper");

                String subreportPath = new File("src/main/java/sgb/report/relatorio2.jasper").getCanonicalPath();

                for (ObraCategoria o : obraCategoriaListModelList) {
                    lista.add(o);
                }

                parametros.put("pathSubreport", subreportPath);
                parametros.put("totalObras", value);

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista));
                break;}
            case 1:{
                List<RegistroObra> lista1 = new ArrayList<>();
                path = "src/main/java/sgb/report/relatorioObrasReg.jrxml";

                for(RegistroObra r: obrasregistadasListModel){
                    lista1.add(r);
                }

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(lista1));
                break;}
            case 2:{
                List<ObraEliminadas> lista2 = new ArrayList<>();
                path = "src/main/java/sgb/report/relatorioObrasEli.jrxml";

                for(ObraEliminadas oe: obraEliminadasListModel){
                    lista2.add(oe);
                }

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(lista2));
                break;}
        }

        JasperViewer.viewReport(jasperPrint, false);
    }

}
