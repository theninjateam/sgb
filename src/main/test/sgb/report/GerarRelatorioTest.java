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
import sgb.controller.domainController.EmprestimoController;
import sgb.controller.domainController.MultaController;
import sgb.domain.Emprestimo;
import sgb.domain.Multa;

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

    @Before
    @Transactional
    public void before() {
        this.multaController = (MultaController) context.getBean("multaController");
        this.emprestimoController = (EmprestimoController) context.getBean("emprestimoController");
        this.gerarRelatorio = (GerarRelatorio) context.getBean("gerarRelatorio");
    }

    @Test
    @Transactional
    public void createPdf() throws JRException {
        List<Emprestimo> emprestimoList;

        emprestimoList = emprestimoController.getBorrowedBooks();

        assertNotNull(gerarRelatorio.createPdf(emprestimoList
                , "src/main/java/sgb/report/relatorioEmprestimo/relatorio.jrxml"));

        assertNull(gerarRelatorio.createPdf(emprestimoList, null));
    }

    @Test
    @Transactional
    public void createPdfMulta() throws JRException {
        List<Multa> multaList;

        multaList = multaController.getMultas();

        assertNotNull(gerarRelatorio.createPdf1(multaList
                ,"src/main/java/sgb/report/relatorioMultas/relatorio.jrxml"));

        assertNull(gerarRelatorio.createPdf1(multaList,null));

    }
}