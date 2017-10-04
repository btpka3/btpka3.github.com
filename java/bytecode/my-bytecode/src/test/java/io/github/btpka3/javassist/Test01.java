package io.github.btpka3.javassist;

import javassist.*;
import org.junit.*;

public class Test01 {

    @Test
    public void testFixedValue() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.makeClass("Point");
        cc.stopPruning(true);
        CtField field = cc.getDeclaredField("name");
//        field.setType();
        //      cc.addField();

        CtClass c1 = pool.makeClass("me.P");
        c1.stopPruning(true);
        c1.defrost();
        c1.setSuperclass(cc);

        cc.writeFile();
        c1.writeFile();
    }

    @Test
    public void a() throws Exception {
        ClassPool cp = ClassPool.getDefault();
        CtClass cc = cp.get("io.github.btpka3.javassist.Hello");
        CtMethod m = cc.getDeclaredMethod("say");
        m.insertBefore("{ System.out.println(\"----before\"); }");
        m.insertAfter("{ System.out.println(\"----after\"); }");
        Class c = cc.toClass();
        Hello h = (Hello) c.newInstance();
        h.say();
    }

}
