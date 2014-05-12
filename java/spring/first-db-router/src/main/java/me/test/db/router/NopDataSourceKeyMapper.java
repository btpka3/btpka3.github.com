
package me.test.db.router;

public class NopDataSourceKeyMapper implements DataSourceKeyMapper {

    @Override
    public String mapping(String dataSourceKey) {

        return dataSourceKey;
    }

}
