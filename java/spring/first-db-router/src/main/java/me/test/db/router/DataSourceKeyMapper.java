
package me.test.db.router;

/**
 * 将数据源路由key映射为另外一个数据源路由Key。
 * 可用来避免多家医院共用同一个数据库时，DataSrouceKeyAdvice根据数据库路由key检测跨数据库事务时抛出异常
 * （即实际是没有跨库）。
 */
public interface DataSourceKeyMapper {

    String mapping(String dataSourceKey);
}
