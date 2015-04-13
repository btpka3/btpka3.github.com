package me.test.kaptcha;

import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.ConfigException;
import com.google.code.kaptcha.util.ConfigHelper;
import com.google.code.kaptcha.util.Configurable;

/**
 *
 * User: zll
 * Date: 5/27/14
 * Time: 5:59 PM
 */
public class ConfigHelperEx extends ConfigHelper {

    /**
     * 重写该方法，否则在Grails环境中 Class.forName(String)会抛出 ClassNotFoundException。
     */
    @Override
    public Object getClassInstance(String paramName, String paramValue, Object defaultInstance,  Config config) {
        Object instance;
        if ("".equals(paramValue) || paramValue == null)
        {
            instance = defaultInstance;
        }
        else
        {
            try
            {
                instance = Class.forName(paramValue, true, Thread.currentThread().getContextClassLoader()).newInstance();
            }
            catch (IllegalAccessException iae)
            {
                throw new ConfigException(paramName, paramValue, iae);
            }
            catch (ClassNotFoundException cnfe)
            {
                throw new ConfigException(paramName, paramValue, cnfe);
            }
            catch (InstantiationException ie)
            {
                throw new ConfigException(paramName, paramValue, ie);
            }
        }

        setConfigurable(instance, config);

        return instance;
    }

    private void setConfigurable(Object object, Config config)
    {
        if (object instanceof Configurable)
        {
            ((Configurable) object).setConfig(config);
        }
    }
}
