
package me.test.action;

import java.util.Arrays;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.sql.DataSource;

import me.test.service.UpdateRecord;
import me.test.service.UserService;

import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestAction {

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "dataSource1")
    private DataSource dataSource1;

    @Resource(name = "dataSource2")
    private DataSource dataSource2;

    @Resource(name = "dbPop1")
    private DatabasePopulator dbPop1;

    @Resource(name = "dbPop2")
    private DatabasePopulator dbPop2;

    @RequestMapping(value = "/reset")
    public String rest() {

        DatabasePopulatorUtils.execute(dbPop1, dataSource1);
        DatabasePopulatorUtils.execute(dbPop2, dataSource2);

        return "redirect:/user/list.do";
    }

    // --------------------------------------------------- 由于DataSourceTransactionManager并为对 AbstractRoutingDataSource
    // 编写特别的代码，不会造成切换数据库，因而会造成错误。

    @RequestMapping(value = "/requiredTransSucceed")
    public String requiredTransSucceed() {
/*
        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRED, 1L, 1L, "--000", true), // 期待结果：更新成功
                new UpdateRecord(1, Propagation.REQUIRED, 2L, 1L, "--111", true), // 期待结果：更新成功
                new UpdateRecord(2, Propagation.REQUIRED, 1L, 2L, "--222", true), // 期待结果：更新成功
                new UpdateRecord(3, Propagation.REQUIRED, 2L, 2L, "--333", true)) // 期待结果：更新成功
        ));
*/
        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRED, 1L, 1L, "--000", true), // 期待结果：更新成功
                new UpdateRecord(1, Propagation.REQUIRED, 3L, 4L, "--444", true), // 期待结果：更新成功
                new UpdateRecord(2, Propagation.REQUIRED, 1L, 2L, "--222", true), // 期待结果：更新成功
                new UpdateRecord(3, Propagation.REQUIRED, 3L, 5L, "--555", true)) // 期待结果：更新成功
        ));

        return "redirect:/user/list.do";
    }

    @RequestMapping(value = "/requiredTransFailedAt0")
    public String requiredTransFailedAt0() {

        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRED, 1L, 1L, "--000", false),// 期待结果：更新失败
                new UpdateRecord(1, Propagation.REQUIRED, 2L, 1L, "--111", true), // 期待结果：更新成功
                new UpdateRecord(2, Propagation.REQUIRED, 1L, 2L, "--222", true), // 期待结果：更新失败
                new UpdateRecord(3, Propagation.REQUIRED, 2L, 2L, "--333", true)) // 期待结果：更新成功
        ));
        return "redirect:/user/list.do";
    }

    @RequestMapping(value = "/requiredTransFailedAt1")
    public String requiredTransFailedAt1() {

        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRED, 1L, 1L, "--000", true), // 期待结果：更新成功
                new UpdateRecord(1, Propagation.REQUIRED, 2L, 1L, "--111", false),// 期待结果：更新失败
                new UpdateRecord(2, Propagation.REQUIRED, 1L, 2L, "--222", true), // 期待结果：更新成功
                new UpdateRecord(3, Propagation.REQUIRED, 2L, 2L, "--333", true)) // 期待结果：更新失败
        ));
        return "redirect:/user/list.do";
    }

    // -------------------------------------------------- 以下由于是新开事务，因此都成功

    @RequestMapping(value = "/requiresNewTransSucceed")
    public String requiresNewTransSucceed() {

        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRES_NEW, 1L, 1L, "--000", true), // 期待结果：更新成功
                new UpdateRecord(1, Propagation.REQUIRES_NEW, 2L, 1L, "--111", true), // 期待结果：更新成功
                new UpdateRecord(2, Propagation.REQUIRES_NEW, 1L, 2L, "--222", true), // 期待结果：更新成功
                new UpdateRecord(3, Propagation.REQUIRES_NEW, 2L, 2L, "--333", true)) // 期待结果：更新成功
        ));
        return "redirect:/user/list.do";
    }

    @RequestMapping(value = "/requiresNewTransFailedAt0")
    public String requiresNewTransFailedAt0() {

        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRES_NEW, 1L, 1L, "--000", false),// 期待结果：更新失败
                new UpdateRecord(1, Propagation.REQUIRES_NEW, 2L, 1L, "--111", true), // 期待结果：更新成功
                new UpdateRecord(2, Propagation.REQUIRES_NEW, 1L, 2L, "--222", true), // 期待结果：更新成功
                new UpdateRecord(3, Propagation.REQUIRES_NEW, 2L, 2L, "--333", true)) // 期待结果：更新成功
        ));
        return "redirect:/user/list.do";
    }

    @RequestMapping(value = "/requiresNewTransFailedAt1")
    public String requiresNewTransFailedAt1() {

        userService.multiUpdate(new LinkedList<UpdateRecord>(Arrays.asList(
                new UpdateRecord(0, Propagation.REQUIRES_NEW, 1L, 1L, "--000", true), // 期待结果：更新失败，由于异常抛出
                new UpdateRecord(1, Propagation.REQUIRES_NEW, 2L, 1L, "--111", false),// 期待结果：更新失败
                new UpdateRecord(2, Propagation.REQUIRES_NEW, 1L, 2L, "--222", true), // 期待结果：更新成功
                new UpdateRecord(3, Propagation.REQUIRES_NEW, 2L, 2L, "--333", true)) // 期待结果：更新成功
        ));
        return "redirect:/user/list.do";
    }

}
