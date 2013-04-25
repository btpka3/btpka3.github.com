package me.test;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;

public class CasAuthenticationEntryPointEx extends CasAuthenticationEntryPoint {

    @Override
    protected String createRedirectUrl(final String serviceUrl) {
        if (this.getServiceProperties() instanceof ServicePropertiesEx) {
            ServicePropertiesEx servProps = (ServicePropertiesEx) this.getServiceProperties();
            return CommonUtils.constructRedirectUrl(this.getLoginUrl(),
                    servProps.getServiceParameter(),
                    serviceUrl,
                    servProps.isSendRenew(),
                    servProps.isGateway());
        }
        return super.createRedirectUrl(serviceUrl);
    }

}
