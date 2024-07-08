package io.github.btpka3.first.flink.api.stream;

import lombok.SneakyThrows;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;

/**
 * MyRichMapFunction 相比 MyMapFunction， 多实现了 RichFunction 接口，可以自定义实现 open/close 等方法。
 *
 * @author dangqian.zll
 * @date 2024/7/5
 * @see org.apache.flink.api.common.functions.RichFunction
 */
public class MyRichMapFunction extends RichMapFunction<String, Integer> {
    private transient ValueState<Tuple2<Long, Long>> sum;

    @SneakyThrows
    public Integer map(String value) {

        Tuple2<Long, Long> currentSum = sum.value();
        currentSum.f0 += value.length();
        sum.update(currentSum);
        //sum.clear();

        return Integer.parseInt(value);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void open(Configuration parameters) throws Exception {

        // 注册自定义 Accumulator
        getRuntimeContext().addAccumulator(MyAccumulator.DEFAULT_ACC_NAME, new MyAccumulator());

        // 注册自定义 State
        String stateName = "average";
        StateTtlConfig ttlConfig = StateTtlConfig
                .newBuilder(Time.seconds(1))
                .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                .build();
        ValueStateDescriptor<Tuple2<Long, Long>> descriptor = new ValueStateDescriptor<>(
                stateName,
                TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {
                }),
                Tuple2.of(0L, 0L)
        );
        descriptor.enableTimeToLive(ttlConfig);

        sum = getRuntimeContext().getState(descriptor);

        super.open(parameters);
    }
}