
package me.test.db.router;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.Assert;

/**
 * 将目标方法上参数名是 paramName 的参数值作为数据源路由key。
 */
public class ParamDataSourceKeyResolver implements DataSourceKeyResolver, BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(ParamDataSourceKeyResolver.class);
    private String paramName;
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public String resolveKey(Object thisObj, Method targetMethod, Object[] args) {

        String[] paramNames = parameterNameDiscoverer.getParameterNames(targetMethod);
        if (paramNames == null) {
            return null;
        }

        Object keyObj = null;
        for (int i = 0; i < paramNames.length; i++) {
            if (paramName.equals(paramNames[i])) {
                keyObj = args[i];
                break;
            }
        }

        if (keyObj == null) {
            return null;
        }
        String strValue = keyObj.toString();
        logger.debug("using '{}' as datasource key", strValue);
        return strValue;
    }

    public String getParamName() {

        return paramName;
    }

    public void setParamName(String paramName) {

        this.paramName = paramName;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Assert.isTrue(paramName != null && paramName.length() > 0, "paramName is not specified.");
        return bean;
    }

}
