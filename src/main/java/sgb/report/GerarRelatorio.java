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

    public JasperPrint createPdfObras(List<ObraCategoria> obraCategoriaList
            , List<RegistroObra> obrasregistadasList
            , List<ObraEliminadas> obraEliminadasList
            , int selected
            , String value
            , String path) throws JRException, IOException {

        String pathLogo = "src/main/webapp/img/logoPNG.png";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;

        switch (selected) {
            case 0:{
                JasperCompileManager.compileReportToFile("src/main/java/sgb/report/relatorioObras/relatorio2.jrxml", "src/main/java/sgb/report/relatorioObras/relatorio2.jasper");

                String subreportPath = new File("src/main/java/sgb/report/relatorioObras/relatorio2.jasper").getCanonicalPath();

                parametros.put("pathLogo", pathLogo);
                parametros.put("pathSubreport", subreportPath);
                parametros.put("totalObras", value);

                try {
                    jasperReport = JasperCompileManager.compileReport(path);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obraCategoriaList));
                }catch (Exception e){

                }

                break;
            }
            case 1:{
                parametros.put("pathLogo", pathLogo);

                try {
                    jasperReport = JasperCompileManager.compileReport(path);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obrasregistadasList));
                }catch (Exception e){

                }
                break;
            }
            case 2:{
                parametros.put("pathLogo", pathLogo);

                try {
                    jasperReport = JasperCompileManager.compileReport(path);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obraEliminadasList));
                }catch (Exception e){

                }
                break;
            }
        }

        return jasperPrint;
    }

    public JasperPrint createPdfEmprestimo(List<Emprestimo> emprestimoList
            , String path) throws JRException {
        String pathLogo = "src/main/webapp/img/logoPNG.png";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;

        parametros.put("pathLogo", pathLogo);
        //parametros.put("datas", datas);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(emprestimoList));
        }catch (Exception e){

        }

        return jasperPrint;
    }

    public JasperPrint createPdfMulta(List<Multa> multaList
            , String path
            , double totalValor) throws JRException{
        String pathLogo = "src/main/webapp/img/logoPNG.png", s ="";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;

        s +=totalValor;
        parametros.put("pathLogo", pathLogo);
        parametros.put("totalValor", s);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(multaList));
        }catch (Exception e){

        }

        return jasperPrint;
    }

}