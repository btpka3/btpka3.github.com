package me.test.test;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface MyServ001 {

    Person get();

    /**
     * 测试用，将参数Bean原样返回。
     */
    Person echo(Person person);
    /**
     * 将所给的人的年龄分别+1
     *
     */
    List<Person> oneYearPassed(List<Person> persons);

    /**
     * 超过50岁就抛出异常
     *
     * @param p
     * @throws MyException001
     */
    void throwsMyException001(Person p) throws MyException001;
}
