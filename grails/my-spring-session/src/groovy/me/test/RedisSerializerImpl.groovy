package me.test

import groovy.transform.CompileStatic
import org.springframework.core.convert.converter.Converter
import org.springframework.core.serializer.support.SerializingConverter
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.SerializationException

/**
 * copy {@link org.springframework.data.redis.serializer.JdkSerializationRedisSerializer JdkSerializationRedisSerializer}
 */
@CompileStatic
class RedisSerializerImpl implements RedisSerializer<Object> {

    static final byte[] EMPTY_ARRAY = new byte[0];

    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverterImpl();

    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }


    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }
        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }
}
