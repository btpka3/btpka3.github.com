package me.test.com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/3/26
 */
public class Kryo4Serializer implements Serializer {

    // 统计出来的数据字典大小平均值
    private static final int BUFFER_SIZE = 24576;

    public static ThreadLocal<Kryo> kryo = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(Object.class);
        kryo.register(java.util.HashMap.class);
        //kryo.setDefaultSerializer(new com.esotericsoftware.kryo.SerializerFactory.ReflectionSerializerFactory(FieldSerializer.class));
        kryo.setRegistrationRequired(false);
        //kryo.setAutoReset(false);
//        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        return kryo;
    });

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> deserialize(byte[] data) {
        if (data == null) {
            throw new IllegalArgumentException("The Input bytes must not be null");
        }
        Input input = new Input(data);
        Map<String, Object> result = (Map<String, Object>) kryo.get().readClassAndObject(input);
        input.close();
        return result;
    }

    @Override
    public byte[] serialize(Map<String, Object> ctx) {
        if (ctx == null) {
            throw new IllegalArgumentException("null ctx");
        }

        Output output = new Output(BUFFER_SIZE, -1);
        kryo.get().writeClassAndObject(output, ctx);
        output.flush();
        byte[] result = output.toBytes();
        output.close();
        return result;
    }

}