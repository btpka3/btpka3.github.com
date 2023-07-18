package me.test.first.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.common.remote.PayloadRegistry;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/**
 * @author dangqian.zll
 * @date 2023/7/18
 */
public class ConfigService01 {

//    String SERVER_ADDR = "127.0.0.1:8848";
    String SERVER_ADDR = "http://nacos.default.svc.cluster.local:8848";


    @Test
    @SneakyThrows
    public void getConfig01() {
        init();
        String dataId = "gong9.mw.tddl.conf";
        String group = "gong9-mw";
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, SERVER_ADDR);
        ConfigService configService = NacosFactory.createConfigService(properties);

        String str = configService.getConfig(dataId, group, 3 * 1000);
        System.out.println("str = " + str);
        Assertions.assertNotNull(str);
    }

    public void init() {
        PayloadRegistry.init();
        System.out.println("PayloadRegistry.init() : done.");
    }
}
