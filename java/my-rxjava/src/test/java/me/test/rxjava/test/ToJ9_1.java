package me.test.rxjava.test;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.flowables.GroupedFlowable;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 难点：
 * - 消息堆积，需要有类似时间序列大数据库
 *    最好有 Backpressure 机制（参考 RxJava2 Flowable、JDK9 中的Flow API ),
 * - 多线程安全，并发分布式统计。Map-Reduce, 可以使用类似 AKKA 的平台。
 * - 分布式缓存，用来统计累加数量 ( AtomicLong/ redis.String.INCR / 若分布式则使用类似 Redis 的 Sorted Set)。
 * - TOP1000, 先分组统计，+ Redis.SortedSet / 定长、只保留TOP1000 的有序 Queue - LinkedList
 *   or
 * <p>
 * 以下代码是 单JVM，实时的方式。
 */
public class ToJ9_1 {

    public enum ActionEnum {BUY, COLLECT, SHARE, SUBSCRIBE}

    public static class Msg {
        String userId;
        ActionEnum action;
        String itemId;
        Date time;
    }

    public static String yyyyMMddHH(Date date) {
        // TODO
        return null;
    }

    /*
     *  {
     *    "yyyyMMdd" : {
     *      ${action} : ${count}
     *    }
     *  }
     *  可以使用 redis.String.INCR 替代
     */
    static Map<String, Map<ActionEnum, AtomicLong>> dayCountMap = null;


    @Test
    public void test01() throws Exception {


        // 按天、按动作统计
        String statDay = "${yyyyMMdd}";
        //AtomicReference<FlowableEmitter> emitterRef = new AtomicReference<>();
        Flowable<Msg> byDayMsgFlow = Flowable.create((FlowableEmitter<Msg> emitter) -> {

            try {
                // 模拟游标读读取大量数据库, 只查询给定 日期(yyyyMMdd) 的记录。
                ResultSet rs = null;
                while (rs.next()) {

                    // 转换为 Msg，并派发到 Flowable
                    Msg msg = new Msg();
                    msg.userId = rs.getString(0);
                    // ...
                    emitter.onNext(msg);
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
            //emitterRef.set(emitter);
        }, BackpressureStrategy.BUFFER);


        // 按 action 分成 sub Flowable
        byDayMsgFlow.groupBy(msg -> msg.action)
                .subscribe(
                        (groupedFlow) -> statByHour(statDay, groupedFlow),
                        System.err::println,
                        () -> {
                        }
                );
    }

    private static void statByHour(String statDay, GroupedFlowable<ActionEnum, Msg> flow) {
        flow.subscribe(
                (msg) -> dayCountMap
                        .get(yyyyMMddHH(msg.time))
                        .get(msg.action)
                        .getAndAdd(1),
                System.err::println,
                () -> {
                    ActionEnum action = flow.getKey();
                    AtomicLong count = dayCountMap
                            .get(statDay)
                            .get(action);
                    System.out.println(" 统计结束 : " + statDay + ":" + action + " = " + count);
                }
        );
    }

}
