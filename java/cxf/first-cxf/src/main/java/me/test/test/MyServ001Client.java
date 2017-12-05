package me.test.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.commons.io.FileUtils;

public class MyServ001Client {
    public static void main(String[] args) throws IOException {
        URL wsdlURL = new URL("http://localhost:9000/FirstCXF/myServ001?wsdl");
        QName SERVICE_NAME = new QName("http://test.sample.me/",
                "MyServ001ImplService");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        MyServ001 ws = service.getPort(MyServ001.class);
        Person p = ws.get();
        System.out.println("-----client-get()-----");
        System.out.println("" + p);


        Person liang = new Person();
        Person ying = new Person();

        liang.setName("诸葛亮");
        liang.setAge(52);
        liang.setImageData(FileUtils.readFileToByteArray(new File(
                Person.class.getResource("ZhuGeLiang.jpg").getFile())));
        List<String> ability = new ArrayList<String>();
        ability.add("观星");
        ability.add("空城");
        liang.setAbility(ability);
        Map<String, String> extraInfo = new HashMap<String, String>();
        extraInfo.put("观星", "改变最多5张牌的顺序");
        extraInfo.put("空城", "无手牌时不能成为杀和决斗的对象");
        liang.setExtraInfo(extraInfo);
        liang.setPartner(ying);

        ying.setName("黄月英");
        ying.setAge(48);
        ying.setImageData(FileUtils.readFileToByteArray(new File(
                Person.class.getResource("HuangYueYing.jpg").getFile())));
        ability = new ArrayList<String>();
        ability.add("集智");
        ability.add("奇才");
        ying.setAbility(ability);
        extraInfo = new HashMap<String, String>();
        extraInfo.put("集智", "每使用一张非延时类锦囊，额外多摸一张牌");
        extraInfo.put("奇才", "锦囊无距离限制");
        ying.setExtraInfo(extraInfo);
        //ying.setPartner(liang);

        // echo()
        System.out.println("-----client-echo()-----");
        Person echoResult = ws.echo(liang);
        System.out.println("" + echoResult);

//        // oneYearPassed()
//        System.out.println("-----client-oneYearPassed()-----");
//        List<Person> persons = new ArrayList<Person>();
//        List<Person> result = ws.oneYearPassed(persons);
//        System.out.println("" + result);
//
//        //
//        System.out.println("-----client-oneYearPassed()-----");
//        try {
//            System.out.println("-----ying:");
//            ws.throwsMyException001(ying);
//            System.out.println("-----OK!");
//        } catch (MyException001 e) {
//            System.out.println("-----NG!");
//            e.printStackTrace();
//        }
//        try {
//            System.out.println("-----liang:");
//            ws.throwsMyException001(liang);
//            System.out.println("-----OK!");
//        } catch (MyException001 e) {
//            System.out.println("-----NG!");
//            e.printStackTrace();
//        }
    }
}
