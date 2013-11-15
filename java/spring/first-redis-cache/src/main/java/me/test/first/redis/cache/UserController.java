
package me.test.first.redis.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBiz userBiz;

    @RequestMapping(value = { "", "/" })
    public String list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gender", required = false) Boolean gender,
            Model model) {

        List<User> userList = userBiz.list(name, gender);
        model.addAttribute("userList", userList);

        return "user/list";
    }

    @RequestMapping(value = "/{id}", method = { RequestMethod.GET })
    public String selectById(@PathVariable Long id, Model model) {

        User user = userBiz.selectById(id);
        model.addAttribute("user", user);

        return "user/detail";

    }

    @RequestMapping(value = "/{id}", method = { RequestMethod.POST })
    public String update(
            @PathVariable Long id,
            final User user) {

        Assert.isTrue(id.equals(user.getId()), "用户ID不能变更");
        userBiz.update(user);

        // "redirect:", "redirect:." will redirect to /user
        return "redirect:{id}";
    }

}
