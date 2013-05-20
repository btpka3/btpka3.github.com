package me.test.first.spring.jdo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.first.spring.jdo.entity.User;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Main {

    private Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println();
        System.out.println("==================== TEST START...");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));
            FileSystemXmlApplicationContext appCtxt = new FileSystemXmlApplicationContext(
                    "applicationContext.xml");

            System.out.println("press enter to continue...");
            br.readLine();

            UserDao personDAO = (UserDao) appCtxt.getBean("personDAO");

            System.out.println();
            System.out.println("==================== TEST 1 : list");
            List<User> list = personDAO.list();
            System.out.println(list.size());
            for (User person : list) {
                printPerson(person);
            }
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 2 : select");
            Person p = personDAO.select("1");
            System.out.println(p);
            printPerson(p);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 3 : insert");
            p = new Person();
            p.setAge(24);
            p.setBirthday(DateUtils.parseDateStrictly("1984-04-04",
                    "yyyy-MM-dd"));
            p.setImgData("444444".getBytes("UTF-16"));
            p.setMale(true);
            p.setName("zhao6");
            personDAO.insert(p);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 4 : update");
            Map<String, Object> person = new HashMap<String, Object>();
            person.put("id", 2L);
            person.put("birthday",
                    DateUtils.parseDateStrictly("2222-02-02", "yyyy-MM-dd"));
            personDAO.update(person);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 5 : delete");
            personDAO.delete(3L);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 6 : DSL Query");
            list = personDAO.dslQuery();
            System.out.println(list.size());
            for (Person p1 : list) {
                printPerson(p1);
            }
            System.out.println("press enter to continue...");
            br.readLine();

        } catch (Throwable e) {

            e.printStackTrace();
        }
        System.out.println();
        System.out.println("==================== TEST OVER.");
    }

    public static void printPerson(User p) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String bithStr = p.getBirthday() == null ? "" : sdf.format(p
                .getBirthday());
        String imgStr = null;
        try {
            if (p.getImgData() != null) {
                imgStr = new String(p.getImgData(), "UTF-16");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.printf(
                "ID:%d, NAME:%s, AGE:%d, isMale?%s, Birthday:%s, IMG:%s\n",
                p.getId(), p.getName(), p.getAge(), p.getMale(), bithStr,
                imgStr);
    }
}
