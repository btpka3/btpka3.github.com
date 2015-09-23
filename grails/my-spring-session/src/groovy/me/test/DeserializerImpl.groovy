package me.test

import groovy.transform.CompileStatic
import org.springframework.core.ConfigurableObjectInputStream
import org.springframework.core.NestedIOException
import org.springframework.core.serializer.Deserializer

/**
 * copy {@link org.springframework.core.serializer.DefaultDeserializer DefaultDeserializer},
 * but using {@link ConfigurableObjectInputStream ConfigurableObjectInputStream}
 */
@CompileStatic
class DeserializerImpl implements Deserializer<Object> {

    @Override
    public Object deserialize(InputStream inputStream) throws IOException {
        ObjectInputStream objectInputStream = new ConfigurableObjectInputStream(inputStream, Thread.currentThread().getContextClassLoader());
        try {
            return objectInputStream.readObject();
        } catch (ClassNotFoundException ex) {
            throw new NestedIOException("Failed to deserialize object type", ex);
        }
    }
}
