
package me.test.db.router;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将第一个方法的参数作为DataSource的路由Key。
 */
public class FirstParamDataSourceKeyResolver implements DataSourceKeyResolver {

    private Logger logger = LoggerFactory.getLogger(FirstParamDataSourceKeyResolver.class);

    @Override
    public String resolveKey(Object thisObj, Method targetMethod, Object[] args) {

        if (args == null || args.length == 0) {
            return null;
        }

        if (args[0] == null) {
            return null;
        }
        String strValue = args[0].toString();
        logger.debug("using '{}' as datasource key", strValue);
        return strValue;
    }
}
