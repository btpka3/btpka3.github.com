package me.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    private Logger logger = LoggerFactory.getLogger(MyController.class);

    // 示例：通过注解在方法入口处进行权限控制
    @RequestMapping("/appointment1")
    @PreAuthorize("hasRole('XROLE_READ_APPOINTMENT')") // 这些权限控制的注解也可以应用到接口上
    public String appointment1() {
        logger.debug("====== MyController#appointment1()");
        return "appointment";
    }

    @RequestMapping("/staff1")
    @PreAuthorize("hasRole('XROLE_READ_STAFF')")
    public String staff1() {
        logger.debug("====== MyController#staff1()");
        return "staff";
    }

    // 示例：通过编码方式进行权限控制
    @RequestMapping("/appointment2")
    public String appointment2() {

        logger.debug("====== MyController#appointment2()");

        SecurityContext secContext = SecurityContextHolder.getContext();
        Authentication auth = secContext.getAuthentication();

        // 有权限
        if (auth.getAuthorities().contains(MyAuthority.XROLE_READ_APPOINTMENT)) {
            return "appointment";
        }

        // 没有权限：可以根据业务需要进行相应的动作，而不必是抛出异常。
        String msg = null;
        throw new AccessDeniedException(msg);

    }

    @RequestMapping("/staff2")
    public String staff2() {
        logger.debug("====== MyController#staff2()");

        SecurityContext secContext = SecurityContextHolder.getContext();
        Authentication auth = secContext.getAuthentication();

        // 有权限
        if (auth.getAuthorities().contains(MyAuthority.XROLE_READ_STAFF)) {
            return "staff";
        }

        // 没有权限：可以根据业务需要进行相应的动作，而不必是抛出异常。
        String msg = null;
        throw new AccessDeniedException(msg);
    }

}
