
package me.test.service;

import java.util.Queue;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("testTransService")
public class TestTransServiceImpl implements TestTransService {

    @Resource(name = "userService")
    private UserService userService;

    // 如果使用的开发工具是STS，则可以则查看root-context.xml时看到AOP到的个数，也可以在被AOP的方法上看到相应的标志
    @Override
    @Transactional
    public void multiUpdateRequiredTrans(Long hospitalId, UpdateRecord curRec, Queue<UpdateRecord> leftRecs) {

        userService.multiUpdate(hospitalId, curRec, leftRecs);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void multiUpdateRequiresNewTrans(Long hospitalId, UpdateRecord curRec, Queue<UpdateRecord> leftRecs) {

        userService.multiUpdate(hospitalId, curRec, leftRecs);
    }

}
