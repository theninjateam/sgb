package sgb.controller.viewsController;

import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zk.ui.util.*;
import org.zkoss.zk.ui.ext.*;
import org.zkoss.zk.au.*;
import org.zkoss.zk.au.out.*;
import org.zkoss.zul.*;
import org.zkoss.util.media.AMedia;
import java.io.*;
import java.net.*;

public class PdfController extends GenericForwardComposer{

    Iframe report;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        //src="http://wwwimages.adobe.com/www.adobe.com/content/dam/Adobe/en/devnet/pdf/pdfs/pdf_reference_archives/PDFReference16.pdf"
        URL url1 =
                new URL("digitalLibrary/pdf/isbd-cons_2007-en.pdf");
        byte[] ba1 = new byte[1024];
        int baLength;
        InputStream is1 = null;
        ByteArrayOutputStream bios = new ByteArrayOutputStream();
        try {
            URLConnection urlConn = url1.openConnection();

            // Checking whether the URL contains a PDF
            if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
                Messagebox.show("Not a pdf!!!");
            } else {
                try {

                    // Read the PDF from the URL and save to a local file
                    is1 = url1.openStream();
                    while ((baLength = is1.read(ba1)) != -1) {
                        bios.write(ba1, 0, baLength);
                    }
                }catch(Exception e) {

                } finally {
                    is1.close();
                }


                final AMedia amedia = new AMedia("PDFReference16.pdf", "pdf","application/pdf", bios.toByteArray());
                report.setContent(amedia);
            }
        } catch(Exception ex) {

        }
    }

    public void onClick$btn(Event e) throws InterruptedException{
        Messagebox.show("Hi btn");
    }
}
