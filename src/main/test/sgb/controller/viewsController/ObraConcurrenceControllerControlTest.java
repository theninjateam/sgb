package sgb.controller.viewsController;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zkoss.zats.mimic.DesktopAgent;
import org.zkoss.zats.mimic.Zats;

public class ObraConcurrenceControllerControlTest
{
    @BeforeClass
    public static void init()
    {
        Zats.init("./src/main/webapp/WEB-INF");
    }

    @AfterClass
    public static void end() {
        Zats.end();
    }

    @After
    public void after() {
        Zats.cleanup();
    }

    @Test
    public void saveData()
    {
    //    DesktopAgent desktop = Zats.newClient().connect("/views/addObra.zul");
//
//        ComponentAgent authorListBox = desktop.query("listbox[id='authorListBox']");
//        ComponentAgent autor = desktop.query("textbox[id='autor']");
//
//        autor.type("Bania Fonseca");
//        desktop.query("button[id='addAuthor']").click();
//
//        List<ComponentAgent> cells = authorListBox.queryAll("listitem").get(0).getChildren();
//        System.out.println("Typed author: "+ cells.get(0).as(Listcell.class).getLabel());
        //Assert.assertEquals("Hello Mimic", label.as(Label.class).getValue());
    }
}
