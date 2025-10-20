package me.test.org.jspecify.annotations;

import me.test.org.jspecify.annotations.a.Utils1;
import me.test.org.jspecify.annotations.b.Utils2;

/**
 *
 * @author dangqian.zll
 * @date 2025/10/20
 */
public class NullableTest {

    public void test11() {
        String str1 = Utils1.getName0(null);
        // FIXME: 不应该警告，因为 父package上声明的 @NullMarked
        System.out.println(str1.length());
    }

    public void test12() {
        String str1 = Utils1.getName1(null);
        // ✅ 应该警告, 因为明确 @Nullable 声明
        System.out.println(str1.length());
    }

    public void test13() {
        // ✅ 应该警告, 因为明确 @NonNull 声明
        String str1 = Utils1.getName2(null);
        System.out.println(str1.length());
    }

    public void test21() {
        // ✅ 不应该告警
        String str1 = Utils2.getName0(null);
        // FIXME 应该告警, 因为 @NullUnmarked 且 未明确声明 @Nullable/@NonNull
        System.out.println(str1.length());
    }


    public void test22() {
        String str1 = Utils2.getName1(null);
        // ✅ 应该警告, 因为明确 @Nullable 声明
        System.out.println(str1.length());
    }

    public void test23() {
        // ✅ 应该告警, 因为明确 @NonNull 声明
        String str1 = Utils2.getName2(null);
        System.out.println(str1.length());
    }

}
