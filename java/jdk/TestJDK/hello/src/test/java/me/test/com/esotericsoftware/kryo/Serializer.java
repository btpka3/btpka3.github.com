package me.test.com.esotericsoftware.kryo;

import java.util.Map;

/**
 * @author dangqian.zll
 * @date 2024/3/26
 */
public interface Serializer {
    Map<String, Object> deserialize(byte[] data);

    byte[] serialize(Map<String, Object> ctx);
    // byte[] serialize(byte[] bytes,int start,Map<String, Object> ctx)  ;
}
