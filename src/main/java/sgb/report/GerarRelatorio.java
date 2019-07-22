package sgb.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.io.*;
import java.nio.file.Path;
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
            , String path
            , String dataFiltro) throws JRException, IOException {

        String auxPath;
        String pathLogo = Executions.getCurrent().getDesktop().getWebApp().getRealPath("img/logoPNG.png");
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;
        JasperReport jasperReport = null;

        switch (selected) {
            case 0:{
                parametros.put("pathLogo", pathLogo);
                parametros.put("pathSubreport", Executions.getCurrent().getDesktop()
                        .getWebApp().getRealPath("jasperFiles/relatorioObras/relatorio2.jasper"));
                parametros.put("totalObras", value);
                parametros.put("dataFiltro", dataFiltro);

                try {
                    jasperReport = JasperCompileManager.compileReport(path);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obraCategoriaList));
                }catch (Exception e){

                }

                break;
            }
            case 1:{
                parametros.put("pathLogo", pathLogo);
                parametros.put("dataFiltro", dataFiltro);

                try {
                    jasperReport = JasperCompileManager.compileReport(path);
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(obrasregistadasList));
                }catch (Exception e){

                }
                break;
            }
            case 2:{
                parametros.put("pathLogo", pathLogo);
                parametros.put("dataFiltro", dataFiltro);

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
            , String path
            , String dataFiltro) throws JRException {
        String pathLogo = Executions.getCurrent().getDesktop().getWebApp().getRealPath("img/logoPNG.png");
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;

        parametros.put("pathLogo", pathLogo);
        parametros.put("dataFiltro", dataFiltro);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(emprestimoList));
        }catch (Exception e){

        }

        return jasperPrint;
    }

    public JasperPrint createPdfMulta(List<Multa> multaList
            , String path
            , double totalValor
            , String dataFiltro) throws JRException{
        String pathLogo = Executions.getCurrent().getDesktop().getWebApp().getRealPath("img/logoPNG.png"), s ="";
        Map parametros = new HashMap();
        JasperPrint jasperPrint = null;

        s +=totalValor;
        parametros.put("pathLogo", pathLogo);
        parametros.put("totalValor", s);
        parametros.put("dataFiltro", dataFiltro);

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(path);
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(multaList));
        }catch (Exception e){

        }

        return jasperPrint;
    }

}