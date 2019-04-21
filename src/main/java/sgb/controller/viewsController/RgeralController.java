package sgb.controller.viewsController;

import net.sf.jasperreports.engine.JRException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.exporter.pdf.PdfExporter;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;
import sgb.controller.domainController.AreaCientificaController;
import sgb.controller.domainController.ObraController;
import sgb.controller.domainController.ObraEliminadasController;
import sgb.controller.domainController.RegistroObraController;
import sgb.domain.*;
import sgb.report.GerarRelatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

//import org.zkoss.zk.chart.Charts;
//import org.zkoss.chart.model.CategoryModel;
//import org.zkoss.chart.model.DefaultCategoryModel;


public class RgeralController extends SelectorComposer<Component> {
    private GerarRelatorio gerarRelatorio = (GerarRelatorio) SpringUtil.getBean("gerarRelatorio");;
    private ObraController obraController  = (ObraController) SpringUtil.getBean("obraController");;
    private RegistroObraController registroObraController  = (RegistroObraController) SpringUtil.getBean("registroObraController");;
    private ObraEliminadasController obraEliminadasController  = (ObraEliminadasController) SpringUtil.getBean("obraEliminadasController");;
    private AreaCientificaController areaCientificaController  = (AreaCientificaController) SpringUtil.getBean("areaCientificaController");;

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Boolean isNormalUser = true;
    private int selected = 0;

    @Wire
    private Listbox obraeliminadas;
    @Wire
    private Listbox obrasregistadas;
    @Wire
    private Listbox ObraDetalhe;

    @Wire
    private Grid detalhesObra;

    private Label qtdd;
    Calendar dataI = Calendar.getInstance();
    Calendar dataF = Calendar.getInstance();

    @Wire
    private Listbox areaCientificaListBox;

    @Wire
    private Button save;

    @Wire
    private Datebox dataInicio;

    @Wire
    private Datebox dataFim;

    @Wire
    private Grid detalhesCategoria;

    @Wire
    private Label titulodetalhesCategoria;

    @Wire
    private Grid RelatorioResumo;

    @Wire
    private Listbox obracategoriaDetalhes;

    @Wire
    private Include idInclRelatorioObrasRegistadas;

    @Wire
    private Include idInclRelatorioObrasQuantidade;

    @Wire
    private Include idInclObrasEliminadas;

    @Wire
    private Listbox obracategoria;

    private ListModelList<RegistroObra> obrasregistadasListModel;
    private ListModelList<ObraEliminadas> obraEliminadasListModel;
    private ListModelList<ObraCategoria> obraCategoriaListModel;
    private ListModelList<AreaCientifica> areaCientificaListModel;
    private ListModelList<Obra> obraListModelList;
    private ListModelList<Obra> listObra = new ListModelList<Obra>();



    @Override
    public void doAfterCompose(Component comp) throws Exception
    {
        super.doAfterCompose(comp);


        AreaCientifica a = new AreaCientifica();
        a.setDescricao("Todas Areas"); a.setIdarea(0);
        areaCientificaListModel = new ListModelList<AreaCientifica>(areaCientificaController.getAreaCientifica());
        areaCientificaListModel.add(0,a);
        areaCientificaListModel.addToSelection(a);
        areaCientificaListBox.setModel(areaCientificaListModel);

        obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(null));
        obracategoria.setModel(obraCategoriaListModel);
    }



    public int getQtddTotalObra(ListModelList<ObraCategoria> obraCategoriaListModel){
        int total=0;

        for (ObraCategoria obraCategoria : obraCategoriaListModel) {
            for(Obra o: obraCategoria.getObras())
                total +=o.getQuantidade();
        }
        return total;
    }

    public void getUpdateDate(){

        try {
            dataI.setTime(dataInicio.getValue());
            dataF.setTime(dataFim.getValue());
        }catch (Exception e){

        };

    }


    @Listen("onClick = #save")
    public void exportar() throws Exception, JRException, IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfExporter exporter = new PdfExporter();
        exporter.export(this.obracategoria, out);

        Filedownload.save(out.toByteArray(), "pdf", "report.pdf");

//        Filedownload.save(JasperExportManager.exportReportToPdf(gerarRelatorio.createPdf(obraCategoriaListModel,
//                obrasregistadasListModel, obraEliminadasListModel, selected, qtdd.getValue())), "pdf", "report.pdf");

        out.close();
    }

    public int getQtd (List<Obra> obras) {
        int qtd = 0;
        return obras.size();
    }

    @Listen("onDetalhes = #obracategoria")
    public void doDelahes(ForwardEvent event)
    {

        AreaCientifica areaCientifica = (AreaCientifica) event.getData();

        obraListModelList = new ListModelList<Obra>(obraController.getObras(areaCientifica));
        titulodetalhesCategoria.setValue("Area Cientifica " + areaCientifica.getDescricao());

        RelatorioResumo.setVisible(false);
        detalhesCategoria.setVisible(true);
        obracategoriaDetalhes.setModel(obraListModelList);
    }

    @Listen("onDetalhes = #obracategoriaDetalhes")
    public void doDelahesC(ForwardEvent event)
    {

        Obra obra = (Obra) event.getData();
        RelatorioResumo.setVisible(false);
        detalhesCategoria.setVisible(false);
        detalhesObra.setVisible(true);

        listObra.add(obra);
        ObraDetalhe.setModel(listObra);

        obrasregistadasListModel = new ListModelList<RegistroObra>( obra.getRegistroObras());
        obraEliminadasListModel = new ListModelList<ObraEliminadas>(obra.getObraEliminadas());

        obrasregistadas.setModel(obrasregistadasListModel);
        obraeliminadas.setModel(obraEliminadasListModel);




    }


    @Listen("onClick = #buttonVoltar")
    public void saveData() throws ParseException {
        RelatorioResumo.setVisible(true);
        detalhesCategoria.setVisible(false);
    }

    @Listen("onClick = #buttonVoltarCatDetalhes")
    public void Return() throws ParseException {
        RelatorioResumo.setVisible(false);
        detalhesCategoria.setVisible(true);
        detalhesObra.setVisible(false);

        obraCategoriaListModel = new ListModelList<ObraCategoria>(obraController.getObrasCategorias(null));
        obracategoria.setModel(obraCategoriaListModel);

        listObra.remove(0);

    }




}
