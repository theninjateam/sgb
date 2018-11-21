package sgb.controller.viewsController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author Fonseca
 */

public class Cesta extends GenericForwardComposer
{
    @Wire
    private Label qtdObrasNaCesta;
    @Wire
    private Div divBusket;

    @Wire
    Include xcontents;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);

        String s="";
        for(Component c: this.execution.getDesktop().getComponents()){
            if(c.getId().equals("main")){

                Borderlayout main = (Borderlayout) c;

          //      North center = main.getNorth();

//                if(center == null)
        //        alert(center.getId());


//
//                if ( main.getFellow("xcontents") != null)
//                    alert(main.getFellow("xcontents").getId());
                //
//                for(Component x:c.getChildren()){
//                    s+=x.getId()+" ";
//                }

                break;
            }
        }


      //  alert(s);

//
//        for (Page page: Executions.getCurrent().getDesktop().getPages()) {
//
//
//            System.out.println("\n\n------------------------------------- " + page.getId() + "-------------------------");
//            for (Component component : page.getDesktop().getComponents()) {
//                System.out.println("\n\n=========================== "+component.getId() + " ======================\n\n");
//            }
//        }
//
//        for (Component component: this.getPage().getDesktop().getComponents())
//        {
//            System.out.println("\n\n=========================== "+component.getId() + " ======================\n\n");
//
//            if (components.keySet().contains(component.getId()))
//            {
//                System.out.println(component.getId());
//            }
//
//                {
//                    components.add(component);
//                }
//            for (String id: targetCompontesID())
//            {
//                if(id.contains(component.getId()))
//                {
//                    components.add(component);
//                }
//            }
//        }
//
//        for (Component component: components)
//            System.out.println(component.getId());
    }

    @Listen("onShowCestaListBox = #divBusket")
    public void showCestaListBox(ForwardEvent event)
    {
        alert("DivBUsket, are you? Yes you are.");


//        gridListObra.setVisible(false);
//        buttonPesquisar.setVisible(false);
//        textboxPesquisar.setVisible(false);
//        griddetalhe.setVisible(false);
//        gridCesta.setVisible(true);
//
    }


    public HashMap<String, Component> getCompontes()
    {
        HashMap<String, Component> ids = new HashMap<String, Component>(4);

        ids.put("gridListObra", null);
        ids.put("gridPesquisar", null);
        ids.put("gridCesta", null);
        ids.put("griddetalhe", null);

        return  ids;
    }

}
