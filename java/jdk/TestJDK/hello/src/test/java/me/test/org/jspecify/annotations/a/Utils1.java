package me.test.org.jspecify.annotations.a;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/20
 */
public class Utils1 {

    public static String getName0(String name) {
        if ("a".equals(name)) {
            // FIXME: 该行应该黄色警告，因为违反了 父package上声明的 @NullMarked
            return null;
        }
        return "name";
    }

    public static @Nullable String getName1(@Nullable String name) {
        if ("a".equals(name)) {
            return null;
        }
        return "name";
    }

    public static @NonNull String getName2(@NonNull String name) {
        if ("a".equals(name)) {
            return null;
        }
        return "name";
    }
}
