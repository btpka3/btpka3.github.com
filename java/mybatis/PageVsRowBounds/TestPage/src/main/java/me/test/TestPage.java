package me.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tc.his.api.model.Person;
import com.tc.his.provider.service.PersonService;

public class TestPage {

    public static void main(String[] args) {
        ApplicationContext ctx = null;

        int count = 1000;

        if (args.length > 0 && args[0].equalsIgnoreCase("MySql")) {
            ctx = new ClassPathXmlApplicationContext("applicationContext-MySql.xml");
        } else if (args.length > 0 && args[0].equalsIgnoreCase("PostgreSql")) {
            ctx = new ClassPathXmlApplicationContext("applicationContext-PostgreSql.xml");
        } else {
            System.out.println("Usage : java me.test.TestPage mysql|postgre [count].");
            return;
        }
        if (args.length > 1) {
            try {
                count = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Could not pars number, using default[" + count + "]");
            }
        }

        PersonService personService = (PersonService) ctx.getBean("personService");

        Runtime rt = Runtime.getRuntime();
        long total = rt.totalMemory();
        long free = rt.freeMemory();
        long used1 = total - free;
        long beginTime = System.currentTimeMillis();

        System.out.println("=================== start " + count + " rec Page test for "
                + args[0]);
        System.out.println("Start millisecond : " + beginTime);
        System.out.println("Start memroy : " + used1 + "/" + total);

        List<Person> list = personService.getList(100, count);
        if (list == null) {
            throw new RuntimeException("list is null");
        } else if (list.size() != count) {
            throw new RuntimeException("expect list.size() == " + count + ", but actually is  " + list.size());
        }

        long endTime = System.currentTimeMillis();
        total = rt.totalMemory();
        free = rt.freeMemory();
        long used2 = total - free;
        System.out.println("=================== Done. ");
        System.out.println("End millisecond : " + endTime);
        System.out.println("End memroy : " + used2 + "/" + total);
        System.out.println("Cost time : " + (endTime - beginTime));
        System.out.println("Added memory : " + (used2 - used1));
    }
}
