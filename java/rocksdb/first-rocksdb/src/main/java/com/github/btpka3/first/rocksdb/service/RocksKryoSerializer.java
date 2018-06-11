package com.github.btpka3.first.rocksdb.service;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.util.Assert;

public class RocksKryoSerializer<T> implements RocksSerializer<T> {


    private Kryo kryo;
    private Class<T> type;
    //    PojoCodec pojoCodec;

    public RocksKryoSerializer(
            Kryo kryo,
            Class<T> type
    ) {
        this.kryo = kryo;
        this.type = type;
    }

    @Override
    public T deserialize(byte[] bytes) {
        return kryo.readObject(new Input(bytes), type);
    }

    @Override
    public byte[] serialize(Object o) {

        Assert.isTrue(o == null || type.isAssignableFrom(o.getClass()), () ->
                "expected to " + type + ", actual is " + (o == null ? null : o.getClass()));
        Output output = new Output(1024);
        kryo.writeObject(output, o);
        return output.toBytes();
    }


}
