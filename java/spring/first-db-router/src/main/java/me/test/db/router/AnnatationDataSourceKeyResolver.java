
package me.test.db.router;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.context.expression.MapAccessor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.util.Assert;

/**
 * 根据方法上，或参数上的 ${link DataSourceKey @DataSourceKey} 注解的设定读取数据源路由Key。
 */
public class AnnatationDataSourceKeyResolver implements DataSourceKeyResolver, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(AnnatationDataSourceKeyResolver.class);
    private ApplicationContext appCtx;
    private ExpressionParser expressionParser = new SpelExpressionParser();
    private Map<Method, String[]> paramNameCache = new ConcurrentHashMap<Method, String[]>();
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();


    @Override
    public String resolveKey(Object thisObj, Method targetMethod, Object[] args) {

        // 通过被@DataSourceKey注解的方法参数计算数据源Key
        String key = null;
        int usingCountOnMethodParam = 0;
        Annotation[][] annotations = targetMethod.getParameterAnnotations();
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                if (annotations[i][j] instanceof DataSourceKey) {
                    usingCountOnMethodParam++;
                    Assert.isTrue(usingCountOnMethodParam <= 1,
                            "@DataSouceKey is found more than one on parameters of method : " + targetMethod);

                    DataSourceKey paramDsKey = (DataSourceKey) annotations[i][j];
                    Assert.isTrue(paramDsKey.value() == null || paramDsKey.value().length() == 0,
                            "@DataSouceKey can not set 'value' when using on method parameter. method : " + targetMethod);

                    if (paramDsKey.prefix().length() == 0) {
                        key = args[i] == null ? null : args[i].toString();
                    } else {
                        key = paramDsKey.prefix() + args[i].toString();
                    }
                }
            }
        }

        DataSourceKey methodDsKey = targetMethod.getAnnotation(DataSourceKey.class);
        Assert.isTrue(!(methodDsKey != null && usingCountOnMethodParam == 1),
                "@DataSouceKey can not using both on method and paramets. method : " + targetMethod);

        if (key != null && key.length() > 0) {
            logger.debug("using '{}' as datasource key from method @DataSouceKey ", key);
            return key;
        }

        // 通过被@DataSourceKey注解的方法计算数据源Key
        if (methodDsKey == null) {
            return null;
        }
        String exprStr = methodDsKey.value();
        Assert.isTrue(exprStr != null && exprStr.length() > 0, "@DataSouceKey's value is not specified. method : " + targetMethod);
        Assert.isTrue(methodDsKey.prefix().length() == 0, "@DataSouceKey can not set 'prefix' when using on method. method : " + targetMethod);

        // 查询是否被注解，如果被注解，则查询到注解的值
        Expression exp = expressionParser.parseExpression(exprStr);
        StandardEvaluationContext sec = new StandardEvaluationContext();
        sec.setRootObject(thisObj);
        sec.addPropertyAccessor(new BeanFactoryAccessor());
        sec.addPropertyAccessor(new MapAccessor());
        sec.addPropertyAccessor(new EnvironmentAccessor());
        sec.setBeanResolver(new BeanFactoryResolver(appCtx));
        sec.setTypeLocator(new StandardTypeLocator(this.getClass().getClassLoader()));

        // 注册参数(参考：org.springframework.cache.interceptor.LazyParamAwareEvaluationContext#loadArgsAsVariables())
        for (int i = 0; i < args.length; i++) {
            sec.setVariable("a" + i, args[i]);
            sec.setVariable("p" + i, args[i]);
        }

        String[] parameterNames = paramNameCache.get(targetMethod);
        if (parameterNames == null) {
            parameterNames = parameterNameDiscoverer.getParameterNames(targetMethod);
            paramNameCache.put(targetMethod, parameterNames);
        }
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                sec.setVariable(parameterNames[i], args[i]);
            }
        }

        Object value = exp.getValue(sec);
        if (value == null) {
            return null;
        }
        String strValue = value.toString();
        logger.debug("using '{}' as datasource key from parameter @DataSouceKey ", strValue);
        return strValue;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.appCtx = applicationContext;
    }
}
