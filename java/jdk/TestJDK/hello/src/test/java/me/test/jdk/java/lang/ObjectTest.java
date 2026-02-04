package me.test.jdk.java.lang;

public class ObjectTest {

    public static void main(String[] args) {
        testClone1();
    }

    public static void testClone1() {
        Aaa a1 = new Aaa();
        a1.setName("zhange3");
        System.out.println("a1 = " + a1);
        Aaa a2 = a1.clone();
        System.out.println("a2 = " + a2);
        System.out.println(a1 == a2);
        System.out.println(a1.equals(a2));
    }

    public static class Aaa {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Aaa{" + "name='" + name + '\'' + '}';
        }

        @Override
        public Aaa clone() {
            Aaa dest = new Aaa();
            dest.setName(this.getName());
            return dest;
        }
    }
}
