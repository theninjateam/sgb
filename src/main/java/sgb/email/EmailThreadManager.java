package sgb.email;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class EmailThreadManager extends Thread implements ApplicationListener<ContextRefreshedEvent> {

    private SendEmailController sendEmailController;
    private int delay = 1*60*1000;

    public final AtomicBoolean running = new AtomicBoolean(false);

    public EmailThreadManager(SendEmailController sendEmailController) {
        this.sendEmailController = sendEmailController;
    }


    public void run(){

        while(this.running.get()){

            this.startSendEmailController();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void startSendEmailController() {

        new Thread(sendEmailController).start();
    }

   public void onApplicationEvent(ContextRefreshedEvent event) {


        System.out.println("Initializing Threads ...");

        this.running.set(true);

        this.setName("Email Thread Manager");
        this.start();
    }


}
