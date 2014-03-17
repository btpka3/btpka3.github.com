
package me.test.db.router;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
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
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

/**
 * 在 Spring Transaction AOP 之前，将路由数据源的key存设置一下。
 *
 * <p>
 * 数据源key的查找及处理流程如下：
 * <ol>
 * <li>查找数据源 key
 * <ol>
 * <li>如果事务方法上有注解 {@link DataSouceKey}，则计算该注解的SpEL表达式，将计算结果以String类型作为数据源key</li>
 * <li>如果 keyParamName 非空，且class文件保留有debug信息，则以事务方法的参数名为 keyParamName 的值作为数据源key</li>
 * <li>如果 using1stParamAsDefaultKey 为 true，则将事务方法的第一个参数的值作为数据源key</li>
 * </ol>
 * <li>
 * <li>如果找到了数据源key，则且但与之前启用事务的key不一致，则根据 throwExceptionWhenCrossDb 的值决定是抛出 {@link CrossDbTransNotSupportedException}
 * 异常还是无视。</li>
 * <li>如果还没找到数据源key，且allowNullKey=false则抛出 {@link DataSouceKeyNotFoundException} 异常。
 * 否则将null作为key，如果AbstractRoutingDataSource配置了defaultTargetDataSource，将使用默认数据源。</li>
 * <ol>
 * </p>
 */
public class DataSourceKeyAdvice implements MethodInterceptor, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(DataSourceKeyAdvice.class);

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    /** 通过被AOP的方法的参数名查找key，要求javac编译时保留有debug信息 */
    private String keyParamName = null;

    /** 是否将被AOP的方法的第一个参数作为key */
    private boolean using1stParamAsDefaultKey = false;

    /** 跨DB时是否抛出异常 */
    private boolean throwExceptionWhenCrossDb = true;

    /** 是否允许空key */
    private boolean allowNullKey = true;

    // internal used field
    private ExpressionParser expressionParser = new SpelExpressionParser();
    private Map<Method, String[]> paramNameCache = new ConcurrentHashMap<Method, String[]>();
    private ApplicationContext appCtx;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method targetMethod = invocation.getMethod();

        @SuppressWarnings("unchecked")
        Stack<String> keyStack = (Stack<String>) TransactionSynchronizationManager.getResource(RoutingDataSourceImpl.DATASOURCE_RSC_KEY);
        if (keyStack == null) {
            keyStack = new Stack<String>();
            TransactionSynchronizationManager.bindResource(RoutingDataSourceImpl.DATASOURCE_RSC_KEY, keyStack);
        }

        String key = lookUpKey(invocation);

        if (key != null && throwExceptionWhenCrossDb) {
            for (int i = 0; i < keyStack.size(); i++) {
                String iKey = keyStack.get(i);
                if (iKey != null && !iKey.equals(key)) {
                    throw new CrossDbTransNotSupportedException();
                }
            }
        }

        if (key == null && !allowNullKey) {
            throw new DataSouceKeyNotFoundException("Could not determin data source key for method : " + targetMethod);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("using '{}' as data source key for method : {}", key, targetMethod);
        }

        keyStack.push(key);

        try {
            return invocation.proceed();
        } finally {
            keyStack.pop();
        }
    }

    private String lookUpKey(MethodInvocation invocation) {

        String key = lookUpKeyByAnnotaion(invocation);
        if (key != null) {
            return key;
        }
        key = lookUpKeyByParamName(invocation);
        if (key != null) {
            return key;
        }
        key = lookUpKeyByFirstParam(invocation);
        if (key != null) {
            return key;
        }
        return null;
    }

    private String lookUpKeyByAnnotaion(MethodInvocation invocation) {

        Method targetMethod = invocation.getMethod();
        DataSourceKey dsKey = targetMethod.getAnnotation(DataSourceKey.class);
        if (dsKey == null) {
            return null;
        }
        String exprStr = dsKey.value();
        Assert.isTrue(exprStr != null && exprStr.length() > 0, "DataSouceKey's value is not specified for method : " + targetMethod);

        // 查询是否被注解，如果被注解，则查询到注解的值
        Expression exp = expressionParser.parseExpression(exprStr);
        StandardEvaluationContext sec = new StandardEvaluationContext();
        sec.setRootObject(invocation.getThis());
        sec.addPropertyAccessor(new BeanFactoryAccessor());
        sec.addPropertyAccessor(new MapAccessor());
        sec.addPropertyAccessor(new EnvironmentAccessor());
        sec.setBeanResolver(new BeanFactoryResolver(appCtx));
        sec.setTypeLocator(new StandardTypeLocator(this.getClass().getClassLoader()));

        // 注册参数(参考：org.springframework.cache.interceptor.LazyParamAwareEvaluationContext#loadArgsAsVariables())
        Object[] args = invocation.getArguments();
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
        return value == null ? null : value.toString();
    }

    private String lookUpKeyByParamName(MethodInvocation invocation) {

        if (keyParamName == null) {
            return null;
        }
        Method targetMethod = invocation.getMethod();

        String[] paramNames = parameterNameDiscoverer.getParameterNames(targetMethod);
        if (paramNames == null) {
            return null;
        }

        Object[] args = invocation.getArguments();
        Object keyObj = null;
        for (int i = 0; i < paramNames.length; i++) {
            if (keyParamName.equals(paramNames[i])) {
                keyObj = args[i];
                break;
            }
        }
        return keyObj == null ? null : keyObj.toString();
    }

    private String lookUpKeyByFirstParam(MethodInvocation invocation) {

        if (!using1stParamAsDefaultKey) {
            return null;
        }

        Object[] args = invocation.getArguments();
        if (args == null || args.length == 0) {
            return null;
        }

        return args[0] == null ? null : args[0].toString();
    }

    public String getKeyParamName() {

        return keyParamName;
    }

    public void setKeyParamName(String keyParamName) {

        this.keyParamName = keyParamName;
    }

    public boolean isUsing1stParamAsDefaultKey() {

        return using1stParamAsDefaultKey;
    }

    public void setUsing1stParamAsDefaultKey(boolean using1stParamAsDefaultKey) {

        this.using1stParamAsDefaultKey = using1stParamAsDefaultKey;
    }

    public boolean isAllowNullKey() {

        return allowNullKey;
    }

    public void setAllowNullKey(boolean allowNullKey) {

        this.allowNullKey = allowNullKey;
    }

    public boolean isThrowExceptionWhenCrossDb() {

        return throwExceptionWhenCrossDb;
    }

    public void setThrowExceptionWhenCrossDb(boolean throwExceptionWhenCrossDb) {

        this.throwExceptionWhenCrossDb = throwExceptionWhenCrossDb;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.appCtx = applicationContext;
    }

}
