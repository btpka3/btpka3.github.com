package me.test.jdk.javax.script;

import javax.script.*;
import java.io.*;
import java.util.*;


/**
 * 为了方便处理，对 RSA.js 进行了修改，修改后的内容为 RSA-new.js, 请 diff 对比差异。
 */
public class TestJs {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        // 加载 RSA.js
        String jsFilePath = TestJs.class.getResource("./new-RSA.js").getFile();
        File jsFile = new File(jsFilePath);
        System.out.println(jsFile.exists());
        FileReader jsReader = new FileReader(jsFile);


        Map window = new HashMap();
        engine.put("window", window);
        Object o1 = engine.eval(jsReader);

        System.out.println("RSAUtils:" + engine.get("RSAUtils"));
        System.out.println("window.RSAUtils:" + engine.get("window.RSAUtils"));


        // 加载 执行脚本

        String exponent = "010001";
        String modulus = "00ba3d972efea794358c211077993f867f0bb61cac53a1e5ab37518459da74c8f93ef648bcd42ebad48de5ad7ede2df77f65111c1f29277a27f17a6494b5e271e5e5c35e4c8c347d34df86a32579483fc26bdebe149c47cc3901ecbe0abc395fc2918b7f5ef8876c647f516bc7568e352f0217224a358bdbd489106e9258597577";
        String pwd = "933125";

        engine.put("exponent", exponent);
        engine.put("modulus", modulus);
        engine.put("pwd", pwd);


        Object o2 = engine.eval("" +
                "var kp = window.RSAUtils.getKeyPair(exponent, '', modulus);" +
                "var encPwd = window.RSAUtils.encryptedString(kp, pwd)");

        String expectEncPwd = "621dbdeb41dc4d2bce32133b63e5bf8313476066e24d239e71483be454fd5ccf038caa844a9a3eaf7fb012fc36896e519d463aef966492c5cf3fb6127524d24551f0634857547182defa08c84067f6c58e686422f9ba3aac7e1bbfecdfde0c335e0fbed226303d2bf3e6c5e6cbbde6481d49dcc7a50272f23ab5b2c74d40a225";
        String actualEncPwd = (String) engine.get("encPwd");


        System.out.println(expectEncPwd.equals(actualEncPwd) ? "OK" : "ERROR");


//        String testFilePath = TestJs.class.getResource("./new-test.js").getFile();
//        File testFile = new File(testFilePath);
//        System.out.println(testFile.exists());
//        FileReader testReader = new FileReader(testFile);
//        Object o2 = engine.eval(testReader);
//        System.out.println("c:" + engine.get("c"));


    }
}
