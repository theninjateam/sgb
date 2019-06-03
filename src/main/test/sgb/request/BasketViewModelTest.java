//package sgb.request;
//
//import static org.junit.Assert.assertNotNull;
//
//import org.junit.*;
//import org.zkoss.zats.junit.AutoClient;
//import org.zkoss.zats.junit.AutoEnvironment;
//import org.zkoss.zats.mimic.*;
//import org.zkoss.zk.ui.Page;
//
//import javax.servlet.http.*;
//
//public class BasketViewModelTest
//{
//    @ClassRule
//    public static AutoEnvironment env =
//            new AutoEnvironment( "./src/main/webapp");
//
//    @Rule
//    public AutoClient autoClient = env.autoClient();
//
//    @BeforeClass
//    public static void init()
//    {
//    }
//
//    @AfterClass
//    public static void end()
//    {
//    }
//
//    @Test
//    public void initTest()
//    {
//        DesktopAgent desktop  = autoClient.connect("/views/config.zul");
//        assertNotNull(desktop);
//
//
//         ComponentAgent button = desktop.query("#j_username");
//    }
//}
