package me.test.first.spring.dubbo.provider;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author dangqian.zll
 * @date 2023/5/12
 */
@DubboService(timeout = 5000)
public class DemoDubboServiceImpl extends DemoDubboServiceBaseImpl {
}
