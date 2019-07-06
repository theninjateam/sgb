package sgb.report;

import net.sf.jasperreports.engine.JRException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sgb.controller.domainController.*;
import sgb.domain.*;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})

public class GerarRelatorioTest {

    @Autowired
    private ApplicationContext context;
    private EmprestimoController emprestimoController;
    private MultaController multaController;
    private GerarRelatorio gerarRelatorio;
    private ObraController obraController;
    private RegistroObraController registroObraController;
    private ObraEliminadasController obraEliminadasController;
    private AreaCientificaController areaCientificaController;

    @Before
    @Transactional
    public void before() {
        this.multaController = (MultaController) context.getBean("multaController");
        this.emprestimoController = (EmprestimoController) context.getBean("emprestimoController");
        this.gerarRelatorio = (GerarRelatorio) context.getBean("gerarRelatorio");
        this.obraController = (ObraController) context.getBean("obraController");
        this.registroObraController = (RegistroObraController) context.getBean("registroObraController");
        this.obraEliminadasController = (ObraEliminadasController) context.getBean("obraEliminadasController");
        this.areaCientificaController = (AreaCientificaController) context.getBean("areaCientificaController");
    }

    @Test
    @Transactional
    public void createPdf1() throws JRException, IOException {
        AreaCientifica areaCientifica;
        List<ObraCategoria> obraCategorias;
        List<RegistroObra> registroObras;
        List<ObraEliminadas> obraEliminadas;

        areaCientifica = areaCientificaController.getAreaCientifica().get(1);
        obraCategorias = obraController.getObrasCategorias(areaCientifica);
        registroObras = registroObraController.getObrasRegistadas();
        obraEliminadas = obraEliminadasController.getObrasEliminadas();

        //For Obras Quantidade
        assertNotNull(gerarRelatorio.createPdfObras(obraCategorias
                ,registroObras
                ,obraEliminadas
                ,0
                ,"100"
                ,"src/main/java/sgb/report/relatorioObras/relatorio.jrxml"));

        assertNull(gerarRelatorio.createPdfObras(obraCategorias
                ,registroObras
                ,obraEliminadas
                ,0
                ,"100"
                , null));

        //For Obras Registradas
        assertNotNull(gerarRelatorio.createPdfObras(obraCategorias
                ,registroObras
                ,obraEliminadas
                ,1
                ,"100"
                ,"src/main/java/sgb/report/relatorioObras/relatorioObrasReg.jrxml"));

        assertNull(gerarRelatorio.createPdfObras(obraCategorias
                ,registroObras
                ,obraEliminadas
                ,1
                ,"100"
                , null));

        //For Obras Eliminadas
        assertNotNull(gerarRelatorio.createPdfObras(obraCategorias
                ,registroObras
                ,obraEliminadas
                ,2
                ,"100"
                ,"src/main/java/sgb/report/relatorioObras/relatorioObrasEli.jrxml"));

        assertNull(gerarRelatorio.createPdfObras(obraCategorias
                ,registroObras
                ,obraEliminadas
                ,2
                ,"100"
                , null));
    }

    @Test
    @Transactional
    public void createPdf2() throws JRException {
        List<Emprestimo> emprestimoList;

        emprestimoList = emprestimoController.getBorrowedBooks();

        assertNotNull(gerarRelatorio.createPdfEmprestimo(emprestimoList
                , "src/main/java/sgb/report/relatorioEmprestimo/relatorio.jrxml"));

        assertNull(gerarRelatorio.createPdfEmprestimo(emprestimoList, null));
    }

    @Test
    @Transactional
    public void createPdf3() throws JRException {
        List<Multa> multaList;

        multaList = multaController.getMultas();

        assertNotNull(gerarRelatorio.createPdfMulta(multaList
                ,"src/main/java/sgb/report/relatorioMultas/relatorio.jrxml", 1));

        assertNull(gerarRelatorio.createPdfMulta(multaList,null, 1));

    }
}