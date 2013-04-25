package me.test;

import org.springframework.security.cas.ServiceProperties;
import org.springframework.util.Assert;

public class ServicePropertiesEx extends ServiceProperties {

    private boolean gateway = false;

    public boolean isGateway() {
        return gateway;
    }

    public void setGateway(boolean gateway) {
        this.gateway = gateway;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();

        Assert.isTrue(!(this.gateway && this.isSendRenew()),
                "sendRenew and gateway can't be set at same time.");

    }

}
