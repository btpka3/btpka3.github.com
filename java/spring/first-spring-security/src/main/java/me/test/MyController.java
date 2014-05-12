
package me.test;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {

    private Logger logger = LoggerFactory.getLogger(MyController.class);

    // 示例：通过注解在方法入口处进行权限控制
    @RequestMapping("/appointment1")
    @PreAuthorize("hasRole('XROLE_READ_APPOINTMENT')")
    // 这些权限控制的注解也可以应用到接口上
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
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("XROLE_READ_APPOINTMENT"))) {
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
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("XROLE_READ_STAFF"))) {
            return "staff";
        }

        // 没有权限：可以根据业务需要进行相应的动作，而不必是抛出异常。
        String msg = null;
        throw new AccessDeniedException(msg);
    }

    // 示例：即使没有 XROLE_READ_STAFF 权限，也能通过有权限的入口调用相关代码。
    // 即：权限控制只发生类外部类调用当前类的时候，如果同一个类的方法之间相互调用则不会。
    @RequestMapping("/a")
    @PreAuthorize("isAuthenticated()")
    public String a() {

        b();
        logger.debug("====== MyController#a()");
        return "console";
    }

    @RequestMapping("/b")
    @PreAuthorize("hasRole('XROLE_READ_STAFF')")
    public String b() {

        logger.debug("====== MyController#b()");
        return "console";
    }

    // 示例：默认启用了SecurityContextHolderAwareRequestWrapper，
    @RequestMapping("/c")
    public String c(HttpServletRequest req) {

        logger.debug("====== MyController#c()");
        logger.debug(getStackTraceString());
        logger.debug("HttpServletRequest: class={}, remoteUser={}", req.getClass().toString(), req.getRemoteUser());
        if (req.getUserPrincipal() != null) {
            logger.debug("UserPrincipal: class={}, toString()={}", req.getUserPrincipal().getClass(), req.getUserPrincipal().toString());
        }
        logger.debug("isUserInRole(XROLE_READ_APPOINTMENT)= {}", req.isUserInRole("XROLE_READ_APPOINTMENT"));
        logger.debug("isUserInRole(XROLE_READ_STAFF)= {}", req.isUserInRole("XROLE_READ_STAFF"));
        return "console";
    }

    private String getStackTraceString() {

        StackTraceElement[] list = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : list) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
