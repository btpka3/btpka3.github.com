import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author dangqian.zll
 * @date 2025/5/27
 * @see <a href="https://bugs.openjdk.org/browse/JDK-8210522">Improve filtering for classes with security sensitive fields</a>
 */
public class ClassLoaderReflectTest {

    /**
     * --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED
     * --illegal-access=permit  # JDK 17 不支持
     * jdk.internal.reflect.Reflection.fieldFilterMap # JDK 17 中 不再支持对 java.lang.ClassLoader 等类通过反射获取成员变量。
     */
    @Test
    @SneakyThrows
    public void test1() {
        ClassLoader c = ClassLoader.getPlatformClassLoader();
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[0], c);
        urlClassLoader.getURLs();

        Class<?> classLoaderClass = ClassLoader.class;
        Field parentField = classLoaderClass.getDeclaredField("parent");

        parentField.setAccessible(true);
        Object parentCl = parentField.get(urlClassLoader);

        Assertions.assertSame(c, parentCl);
        System.out.println("Done.");
    }
}
