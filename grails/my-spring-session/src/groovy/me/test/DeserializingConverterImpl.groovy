package me.test

import groovy.transform.CompileStatic
import org.springframework.core.convert.converter.Converter
import org.springframework.core.serializer.Deserializer
import org.springframework.core.serializer.support.SerializationFailedException
import org.springframework.util.Assert

/**
 * copy {@link org.springframework.core.serializer.support.SerializingConverter}
 */
@CompileStatic
class DeserializingConverterImpl implements Converter<byte[], Object> {


    private Deserializer<Object> deserializer;

    public DeserializingConverterImpl() {
        this.deserializer = new DeserializerImpl();
    }

    public DeserializingConverterImpl(Deserializer<Object> deserializer) {
        Assert.notNull(deserializer, "Deserializer must not be null");
        this.deserializer = deserializer;
    }


    @Override
    public Object convert(byte[] source) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(source);
        try {
            return this.deserializer.deserialize(byteStream);
        } catch (Throwable ex) {
            throw new SerializationFailedException("Failed to deserialize payload. " +
                    "Is the byte array a result of corresponding serialization for " +
                    this.deserializer.getClass().getSimpleName() + "?", ex);
        }
    }
}
