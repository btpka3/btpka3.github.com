
package me.test.db.router;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

public class ComposeDataSourceKeyResolver implements DataSourceKeyResolver {

    private List<DataSourceKeyResolver> resolverList = Collections.emptyList();

    @Override
    public String resolveKey(Object thisObj, Method targetMethod, Object[] args) {

        for (DataSourceKeyResolver resolver : resolverList) {
            String key = resolver.resolveKey(thisObj, targetMethod, args);
            if (key != null && key.length() > 0) {
                return key;
            }
        }

        return null;
    }

    public void setResolverList(List<DataSourceKeyResolver> resolverList) {

        Assert.notNull(resolverList, "resolverList can not be null.");

        this.resolverList = resolverList;
    }

}
