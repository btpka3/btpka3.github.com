package me.test.first.spring.jdo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.first.spring.jdo.entity.User;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1
                || !("dsl".equalsIgnoreCase(args[0])
                || "jdo".equalsIgnoreCase(args[0]))) {
            System.err.println("Usage : java me.test.first.spring.jdo.Main [dsl|jdo]");
            System.exit(1);
        }

        System.out.println();
        System.out.println("==================== TEST START...");
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserDao userDao = (UserDao) appCtx.getBean("userDaoJdo");
        if ("dsl".equalsIgnoreCase(args[0])) {
            userDao = (UserDao) appCtx.getBean("userDaoDsl");
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));

            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 1 : list");
            List<User> list = userDao.list();
            System.out.println(list.size());
            for (User user : list) {
                System.out.println(user);
            }
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 2 : select");
            User p = userDao.select(1);
            System.out.println(p);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 3 : insert");
            p = new User();
            p.setAge(24);
            p.setBirthday(DateUtils.parseDateStrictly("1984-04-04",
                    "yyyy-MM-dd"));
            p.setImgData("444444".getBytes("UTF-16"));
            p.setMale(true);
            p.setName("zhao6");
            userDao.insert(p);
            System.out.println(p);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 4 : update");
            Map<String, Object> userMap = new HashMap<String, Object>();
            userMap.put("id", 2L);
            userMap.put("birthday",
                    DateUtils.parseDateStrictly("2222-02-02", "yyyy-MM-dd"));
            userDao.update(userMap);
            System.out.println("press enter to continue...");
            br.readLine();

            System.out.println();
            System.out.println("==================== TEST 5 : delete");
            userDao.delete(3L);
            System.out.println("press enter to continue...");
            br.readLine();

        } catch (Throwable e) {

            e.printStackTrace();
        } finally {
            appCtx.close();
        }
        System.out.println();
        System.out.println("==================== TEST OVER.");
    }

}
