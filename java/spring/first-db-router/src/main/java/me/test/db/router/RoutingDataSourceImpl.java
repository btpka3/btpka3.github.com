
package me.test.db.router;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSourceImpl extends AbstractRoutingDataSource {

    private static final String PREFIX = RoutingDataSourceImpl.class.getName() + ".";
    public static final String DATASOURCE_RSC_KEY = PREFIX + "DATASOURCE_RSC_KEY";

    private static final Logger logger = LoggerFactory.getLogger(RoutingDataSourceImpl.class);

    @Override
    protected Object determineCurrentLookupKey() {

        @SuppressWarnings("unchecked")
        Stack<String> keyStack = (Stack<String>) TransactionSynchronizationManager.getResource(DATASOURCE_RSC_KEY);

        if (keyStack == null || keyStack.empty()) {
            return null;
        }
        String dataSourceKey = keyStack.peek();
        if (logger.isTraceEnabled()) {
            logger.trace("using dataSource by key : {}", dataSourceKey);
        }
        return dataSourceKey;
    }

}
