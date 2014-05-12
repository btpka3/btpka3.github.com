
package me.test.db.router;

import java.util.Collections;
import java.util.Map;

public class MapDataSourceKeyMapper implements DataSourceKeyMapper {

    private Map<String, String> keyMap = Collections.emptyMap();

    @Override
    public String mapping(String dataSourceKey) {

        if (keyMap == null) {
            return null;
        }
        return keyMap.get(dataSourceKey);
    }

    public void setKeyMap(Map<String, String> keyMap) {

        this.keyMap = keyMap;
    }

}
