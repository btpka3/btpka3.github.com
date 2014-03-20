
package me.test.db.router;

import java.lang.reflect.Method;

public interface DataSourceKeyResolver {

    String resolveKey(Object thisObj, Method targetMethod, Object[] args);

}
