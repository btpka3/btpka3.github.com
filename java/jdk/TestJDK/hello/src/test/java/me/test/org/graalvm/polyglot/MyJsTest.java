package me.test.org.graalvm.polyglot;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author dangqian.zll
 * @date 2025/2/27
 */
public class MyJsTest {

    @Test
    public void test01() {
        try (Context context = Context.create()) {
            context.eval("js", "console.log('Hello from GraalJS!')");
        }
    }

    @Test
    public void test02() {
        MyUser user = new MyUser();
        user.setName("zhang3");
        user.setAge(11);
        user.setHobby("aaa");
        System.out.println("user1 = " + user);

        try (Context context = Context.newBuilder()
                .allowHostAccess(HostAccess.ALL)
                .allowHostClassLookup(className -> true)
                .build()
        ) {
            Value value = context.eval("js", "(function myFun(user){" +
                    // ✅ public 字段直接修改
                    "user.hobby='bbb';" +
                    // ✅ 通过setter修改
                    "user.setName(user.getName()+'_'+user.getAge());" +
                    // ❌ 私有字段不可修改
                    "user.age=22;" +

                    "var MyUserClass = Java.type('me.test.js.MyJsTest$MyUser');" +
                    "var father = new MyUserClass();" +
                    "father.setName('li4');" +
                    "user.setFather(father);" +

                    "var mother = new (Java.type('me.test.js.MyJsTest$MyUser'))();" +
                    "mother.setName('wang5');" +
                    "user.setMother(mother);" +

                    "return 33;" +
                    "})");


            Value returnValue = value.execute(user);
            System.out.println("user2 = " + user);
            Assertions.assertEquals("bbb", user.getHobby());
            Assertions.assertEquals("zhang3_11", user.getName());
            Assertions.assertEquals(11, user.getAge());
            Assertions.assertNotNull(user.getFather());
            Assertions.assertEquals("li4", user.getFather().getName());
            Assertions.assertEquals(33, returnValue.asInt());
        }
    }

    public static class MyUser {
        private String name;
        private int age;
        public String hobby;
        private MyUser father;
        private MyUser mother;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public MyUser getFather() {
            return father;
        }

        public void setFather(MyUser father) {
            this.father = father;
        }

        public MyUser getMother() {
            return mother;
        }

        public void setMother(MyUser mother) {
            this.mother = mother;
        }

        @Override
        public String toString() {
            return "MyUser{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", hobby='" + hobby + '\'' +
                    ", father=" + father +
                    ", mother=" + mother +
                    '}';
        }
    }
}
