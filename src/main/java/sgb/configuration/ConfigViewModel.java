package sgb.configuration;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import sgb.controller.domainController.*;
import sgb.domain.*;
import sgb.request.Request;
import sgb.service.CRUDService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConfigViewModel
{
    private ConfigControler configControler = (ConfigControler) SpringUtil.getBean("configControler");
    private CRUDService crudService = (CRUDService) SpringUtil.getBean("CRUDService");

    private List<Config> configs = new ArrayList<Config>();

    @Init
    public void init() throws Exception
    {
        this.configs = this.crudService.getAll(Config.class);
    }

    @NotifyChange("*")
    @Command("updateConfig")
    public void updateConfig()
    {
        Clients.showNotification("olaha:");
    }
    public List<Config> getConfigs()
    {
        return configs;
    }

    public void setConfigs(List<Config> configs)
    {
        this.configs = configs;
    }
}