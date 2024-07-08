package io.github.btpka3.first.flink.module;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.module.hive.HiveModule;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/7/4
 */
public class HiveModuleTest {
    @Test
    public void test01(){
        EnvironmentSettings settings = EnvironmentSettings.inStreamingMode();
        TableEnvironment tableEnv = TableEnvironment.create(settings);
        tableEnv.executeSql("SHOW MODULES").print();
        tableEnv.listModules();
        tableEnv.listFullModules();
        tableEnv.loadModule("hive", new HiveModule());
        tableEnv.useModules("hive", "core");
        tableEnv.unloadModule("hive");
    }
}
