package sgb.deadline;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class DeadlineThreadManager extends Thread implements ApplicationListener<ContextRefreshedEvent>
{
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        this.setName("DeadLine Thread Manager");
        this.start();
    }
}
