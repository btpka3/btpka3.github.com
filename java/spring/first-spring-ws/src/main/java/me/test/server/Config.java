package me.test.server;

import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.SmartEndpointInterceptor;
import org.springframework.ws.soap.addressing.server.AnnotationActionEndpointMapping;

public class Config extends ApplicationObjectSupport
        implements
            InitializingBean {

    @Autowired(required = false)
    private AnnotationActionEndpointMapping annotationActionEndpointMapping = null;

    public void afterPropertiesSet() throws Exception {

        if (annotationActionEndpointMapping != null) {
            Map<String, SmartEndpointInterceptor> smartInterceptors = BeanFactoryUtils
                    .beansOfTypeIncludingAncestors(getApplicationContext(),
                            SmartEndpointInterceptor.class, true, false);
            if (!smartInterceptors.isEmpty()) {
                SmartEndpointInterceptor[] interceptors = smartInterceptors
                        .values().toArray(
                                new SmartEndpointInterceptor[smartInterceptors
                                        .size()]);
//                annotationActionEndpointMapping.setPreInterceptors(interceptors);
                annotationActionEndpointMapping.setPostInterceptors(interceptors);
            }
        }
    }

}
