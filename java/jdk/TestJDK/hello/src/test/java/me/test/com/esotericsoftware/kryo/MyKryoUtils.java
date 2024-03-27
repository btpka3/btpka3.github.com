package me.test.com.esotericsoftware.kryo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://github.com/EsotericSoftware/kryo/wiki/Migration-to-v5">kryo : Migration to v5</a>
 */
public class MyKryoUtils {

    private static List<String> serializerNameList = Arrays.asList(
            "kryo",
            "slimming"
    );

    public static Map<String, Object> deserialize(byte[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException();
        }
        byte header = data[0];
        int index = header & 0x07;
        if (index > serializerNameList.size()) {
            throw new IllegalStateException("unsupported serialization type. index: " + index + ". only "
                    + serializerNameList + " are supported.");
        }
        String serializerName = serializerNameList.get(index);
        Serializer serializer = MySerializerFactory.getSerializer(serializerName);
        byte[] body = new byte[data.length - 1];
        System.arraycopy(data, 1, body, 0, data.length - 1);
        Map<String, Object> ctx = serializer.deserialize(body);
        //Mtee3DispatchMsgUtil.afterDeserialize(ctx);
        return ctx;
    }

    protected static class MySerializerFactory {

        private static Map<String, Serializer> serializers = new HashMap<String, Serializer>();

        static {
            serializers.put("kryo", new Kryo4Serializer());
        }

        public static Serializer getSerializer(String name) {
            Serializer serializer = serializers.get(name);
            if (serializer == null) {
                throw new IllegalStateException("unregistered serializer name: " + name);
            }
            return serializer;
        }
    }
}
