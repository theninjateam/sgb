package sgb.report;

import java.nio.file.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.zkoss.zul.ListModelList;
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

    public JasperPrint createPdf(ListModelList<ObraCategoria> obraCategoriaListModelList,
                          ListModelList<RegistroObra> obrasregistadasListModel,
                          ListModelList<ObraEliminadas> obraEliminadasListModel, int selected, String value) throws JRException, IOException {

        String path = null, pathLogo = "src/main/webapp/img/logoPNG.png";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;

        switch (selected) {
            case 0:{
                List<ObraCategoria> lista = new ArrayList<>();

                path = "src/main/java/sgb/report/relatorio.jrxml";

                JasperCompileManager.compileReportToFile("src/main/java/sgb/report/relatorio2.jrxml", "src/main/java/sgb/report/relatorio2.jasper");

                String subreportPath = new File("src/main/java/sgb/report/relatorio2.jasper").getCanonicalPath();

                for (ObraCategoria o : obraCategoriaListModelList) {
                    lista.add(o);
                }

                parametros.put("pathLogo", pathLogo);
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

                parametros.put("pathLogo", pathLogo);

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista1));
                break;}
            case 2:{
                List<ObraEliminadas> lista2 = new ArrayList<>();
                path = "src/main/java/sgb/report/relatorioObrasEli.jrxml";

                for(ObraEliminadas oe: obraEliminadasListModel){
                    lista2.add(oe);
                }

                parametros.put("pathLogo", pathLogo);

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista2));
                break;}
        }

        //JasperViewer.viewReport(jasperPrint, false);

        return jasperPrint;
    }

}
