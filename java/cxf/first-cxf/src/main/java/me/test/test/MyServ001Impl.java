package me.test.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.io.FileUtils;

@WebService(endpointInterface = "me.sample.test.MyServ001")
public class MyServ001Impl implements MyServ001 {

    public Person get() {
        Person person = new Person();


        person.setName("xxx");
        person.setAge(99);
        try {
            person.setImageData(FileUtils.readFileToByteArray(new File(
                    Person.class.getResource("ZhuGeLiang.jpg").getFile())));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<String> ability = new ArrayList<String>();
        ability.add("AA");
        ability.add("BB");
        person.setAbility(ability);
        Map<String, String> extraInfo = new HashMap<String, String>();
        extraInfo.put("001", "aaa");
        extraInfo.put("002", "bbb");
        person.setExtraInfo(extraInfo);
        //person.setPartner(person);
        System.out.println("====get()=====");
        System.out.println("" + person);
        return person;
    }
    public Person echo(Person person) {
        System.out.println("====echo()=====");
        System.out.println("" + person);

        return person;
    }

    public List<Person> oneYearPassed(List<Person> persons) {
        System.out.println("====oneYearPassed()=====");
        System.out.println("" + persons);
        for (Person p : persons) {
            if (p == null) {
                continue;
            }
            p.setAge(p.getAge() + 1);
            Person partner = p.getPartner();
            if (partner == null) {
                continue;
            }
            partner.setAge(partner.getAge() + 1);
        }
        return persons;
    }

    public void throwsMyException001(Person p) throws MyException001 {
        if (p.getAge() > 50) {
            throw new MyException001();
        }
    }

}
