
package me.test.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import me.test.service.User;
import me.test.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserAction {

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 查询给定医院的用户。如果参数hospitalId未指定，则查询所有医院。
     */
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
    public String list(
            @RequestParam(value = "hospitalId", required = false) Long hospitalId,
            Model model) {

        // 查询所有医院
        if (hospitalId == null) {
            List<User> userList1 = userService.selectAll(1L);
            List<User> userList2 = userService.selectAll(2L);

            List<User> userList = new ArrayList<User>(userList1);
            userList.addAll(userList2);
            model.addAttribute("userList", userList);
        } else {
            // 查询指定的医院（可能会由于医院不存在而出错）
            List<User> userList = userService.selectAll(hospitalId);
            model.addAttribute("userList", userList);
        }

        return "list";
    }

    /**
     * 新增用户。
     */
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    public String newUser(
            @RequestParam(value = "hospitalId") Long hospitalId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "remark", required = false) String remark,
            Model model,
            RedirectAttributes redirectAttributes) {

        Long userId = userService.insert(hospitalId, name, remark);
        // 相当于设置302跳转时URL上的参数
        redirectAttributes.addAttribute("hospitalId", hospitalId);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:detail.do";
    }

    /**
     * 用户详细。
     */
    @RequestMapping(value = "/detail", method = { RequestMethod.GET })
    public String userDetail(
            @RequestParam("hospitalId") Long hospitalId,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "remark", required = false) String remark,
            Model model) {

        User user = userService.selectById(hospitalId, userId);
        model.addAttribute("user", user);
        return "detail";
    }

    /**
     * 更新用户。
     */
    @RequestMapping(value = "/detail", method = { RequestMethod.PUT })
    public String updateUser(
            @RequestParam("hospitalId") Long hospitalId,
            @RequestParam("userId") Long userId,
            @RequestParam(value = "remark", required = false) String remark,
            Model model,
            RedirectAttributes redirectAttributes) {

        userService.updateById(hospitalId, userId, remark);
        return "redirect:list.do";
    }

    /**
     * 删除用户。
     */
    @RequestMapping(value = "/detail", method = { RequestMethod.DELETE })
    public String deleteUser(
            @RequestParam("hospitalId") Long hospitalId,
            @RequestParam("userId") Long userId,
            Model model,
            RedirectAttributes redirectAttributes) {

        userService.deleteById(hospitalId, userId);

        return "redirect:list.do";
    }

}
