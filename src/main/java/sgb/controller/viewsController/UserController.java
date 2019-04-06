package sgb.controller.viewsController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Textbox;
import sgb.domain.*;
import sgb.service.CRUDService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AlterarSenhaController extends SelectorComposer<Component> {

    private Users user = (Users)(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
    private Boolean isNormalUser = true;
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");
    private static MessageDigest m;


    @Wire
    private Textbox senhaActual;

    @Wire
    private Textbox novaSenha;

    @Wire
    private Textbox confirmarSenha;

    public Boolean isPasswordEqual(String actualPassword,String newPass,String confNewPass)
    {
        return (user.getPassword().equals(criptografar(actualPassword)) && newPass.equals(confNewPass));

    }


    public String criptografar(String password)
    {

        try
        {
            m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes(), 0, password.length());
            BigInteger login1 = new BigInteger(1, m.digest());

            password = String.format("%1$032X", login1).toLowerCase();


            return password;
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Listen("onClick = #savebtn")
    public void UpdatePassword()
    {


        if (isPasswordEqual(senhaActual.getValue(),novaSenha.getValue(),confirmarSenha.getValue()))
        {
            this.user.setPassword(novaSenha.getValue());

            this.crudService.update((Users)user);

        }


    }


}
