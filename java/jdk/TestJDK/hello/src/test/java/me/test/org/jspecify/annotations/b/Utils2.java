package me.test.org.jspecify.annotations.b;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/20
 */
@NullUnmarked
public class Utils2 {

    public static String getName0(String name) {
        if ("a".equals(name)) {
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
