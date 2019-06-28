package sgb.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.zkoss.zul.ListModelList;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerarRelatorio {
    private CRUDService crudService;

    public GerarRelatorio(CRUDService crudService){
        this.crudService = crudService;
    }

    public JasperPrint createPdf(List<ObraCategoria> obraCategoriaList,
                                 List<RegistroObra> obrasregistadasList,
                                 List<ObraEliminadas> obraEliminadasList, int selected, String value) throws JRException, IOException {

        String path = null, pathLogo = "src/main/webapp/img/logoPNG.png";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;

        switch (selected) {
            case 0:{
                path = "src/main/java/sgb/report/relatorioObras/relatorio.jrxml";

                JasperCompileManager.compileReportToFile("src/main/java/sgb/report/relatorioObras/relatorio2.jrxml", "src/main/java/sgb/report/relatorioObras/relatorio2.jasper");

                String subreportPath = new File("src/main/java/sgb/report/relatorioObras/relatorio2.jasper").getCanonicalPath();

                parametros.put("pathLogo", pathLogo);
                parametros.put("pathSubreport", subreportPath);
                parametros.put("totalObras", value);

                jasperReport = JasperCompileManager.compileReport(path);

                jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obraCategoriaList));
                break;}
            case 1:{
                path = "src/main/java/sgb/report/relatorioObras/relatorioObrasReg.jrxml";

                parametros.put("pathLogo", pathLogo);

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obrasregistadasList));
                break;}
            case 2:{
                path = "src/main/java/sgb/report/relatorioObras/relatorioObrasEli.jrxml";

                parametros.put("pathLogo", pathLogo);

                jasperReport = JasperCompileManager.compileReport(path);
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obraEliminadasList));
                break;}
        }

        return jasperPrint;
    }

    public JasperPrint createPdf(List<Emprestimo> emprestimoList, String path) throws JRException {
        String pathLogo = "src/main/webapp/img/logoPNG.png";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;

        parametros.put("pathLogo", pathLogo);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(emprestimoList));
        }catch (Exception e){

        }

        return jasperPrint;
    }

    public JasperPrint createPdf1(List<Multa> multaList, String path) throws JRException{
        String pathLogo = "src/main/webapp/img/logoPNG.png";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;

        parametros.put("pathLogo", pathLogo);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(multaList));
        }catch (Exception e){

        }

        return jasperPrint;
    }

}