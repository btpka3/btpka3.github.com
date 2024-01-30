package me.test.org.apache.commons.collections4.list;

import org.apache.commons.collections4.list.TreeList;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2024/1/30
 */
public class TreeListTest {

    @Test
    public void x() {
        // 按照插入的顺序
        TreeList<String> s = new TreeList<>();
        s.add("xxx");
        s.add("ccc");
        s.add("ddd");
        s.add("eee");
        s.add("aaa");
        s.add("bbb");
        for (String str : s) {
            System.out.println(str);
        }
    }
}
