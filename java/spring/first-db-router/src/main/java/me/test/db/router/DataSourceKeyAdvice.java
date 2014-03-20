
package me.test.db.router;

import java.lang.reflect.Method;
import java.util.Stack;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
public class DataSourceKeyAdvice implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(DataSourceKeyAdvice.class);

    private DataSourceKeyResolver keyResolver;
    private DataSourceKeyMapper keyMapper;

    /** 跨DB时是否抛出异常 */
    private boolean throwExceptionWhenCrossDb = true;

    /** 是否允许空key */
    private boolean allowNullKey = true;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        @SuppressWarnings("unchecked")
        Stack<String> keyStack = (Stack<String>) TransactionSynchronizationManager.getResource(RoutingDataSourceImpl.DATASOURCE_RSC_KEY);
        if (keyStack == null) {
            keyStack = new Stack<String>();
            TransactionSynchronizationManager.bindResource(RoutingDataSourceImpl.DATASOURCE_RSC_KEY, keyStack);
        }

        Method targetMethod = invocation.getMethod();
        String key = keyResolver.resolveKey(invocation.getThis(), targetMethod, invocation.getArguments());

        if (keyMapper != null) {
            key = keyMapper.mapping(key);
        }

        // 防止跨数据库进行事务
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

    public void setKeyResolver(DataSourceKeyResolver keyResolver) {

        this.keyResolver = keyResolver;
    }

    public void setKeyMapper(DataSourceKeyMapper keyMapper) {

        this.keyMapper = keyMapper;
    }

}
