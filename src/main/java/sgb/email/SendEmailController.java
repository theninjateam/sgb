package sgb.email;

import sgb.controller.domainController.MultaController;
import sgb.domain.Multa;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public class SendEmailController extends Thread {


    private MultaController multaController;
    private SendEmail sendEmail;

    public SendEmailController(MultaController multaController,SendEmail sendEmail){

        this.multaController = multaController;
        this.sendEmail = sendEmail;

    }
    public void run(){

        System.out.println("SendEmailController...");
        this.process(multaController.getByNotification(false));
    }

    public boolean process(List<Multa> nonNotified) {


        if (nonNotified !=null){

            String msg,subjet;
            subjet = "Notificacao de Multa";

            for (Multa m: nonNotified){


                msg = "Caro utente " + m.getMultaPK().getUtente().getName() + " " + m.getMultaPK().getUtente().getLastName()+ "\n" +
                        " o seu emprestimo da obra" + m.getMultaPK().getObra().getTitulo() + " feito na data " + m.getMultaPK().getDataentradapedido()+
                        "ultrapassou o tempo limite de emprestimo, tendo uma multa de " + m.getValorpago() +  " MTN.\n Porfavor, Regularize a sua situacao de multa, o mais breve possivel";

                try {
                    sendEmail.sendEmail(subjet, msg, m.getMultaPK().getUtente().getEmail());
                    m.setNotificacao(true);
                } catch (MessagingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            return true;
        }

        return false;



    }



}
