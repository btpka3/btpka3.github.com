
package me.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private Map<Long, User> users;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final List<CodeBean> countryCodeList = Arrays.asList(
            new CodeBean("1", "中国"),
            new CodeBean("2", "美国")
            );

    public UserController() {

        users = new HashMap<Long, User>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("zhang3");
        user1.setAge(22);
        user1.setRecMail(true);
        user1.setHobbies(Arrays.asList("1", "3"));
        user1.setGender("1");
        user1.setCountry("1");

        users.put(user1.getId(), user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("li4");
        user2.setAge(23);
        user2.setRecMail(false);
        user2.setHobbies(Arrays.asList("1", "2"));
        user2.setGender("2");
        user2.setCountry("2");
        users.put(user2.getId(), user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setName("wang5");
        user3.setAge(24);
        user3.setRecMail(false);
        user3.setHobbies(Arrays.asList("2", "3"));
        user3.setGender("2");
        user3.setCountry("1");
        users.put(user3.getId(), user3);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        logger.debug("list is invoked.");

        model.addAttribute("userList", users.values());
        return "user/list";
    }

    // 准备更新的表单画面
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("id") Long id, Model model) {

        logger.debug("edit && GET is invoked.");

        User user = users.get(id);
        model.addAttribute("u", user);

        return "user/edit";
    }

    // 准备更新
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid @ModelAttribute("u") User user, Errors errors, Model model) {

        logger.debug("edit && POST is invoked.");

        // 类型转换，bean validation
        if (errors.hasErrors()) {
            logger.debug("edit && POST errors.hasErrors(). " + errors);

            return "user/edit";
        }

        // 业务逻辑校验
        if (user.getAge() != null && user.getAge() < 18) {
            logger.debug("edit && POST biz check error.");

            errors.rejectValue("age", "myErrorCode");
            return "user/edit";
        }

        users.put(user.getId(), user);
        return "redirect:list.do";
    }

    // 该方法会在每个 @RequestMapping 方法调用前调用
    @ModelAttribute(value = "countryCodeList")
    public List<CodeBean> getCountryCodeList() {

        logger.debug("getCountryCodeList() invoked.");
        return countryCodeList;
    }

}
