package me.test.first.spring.rs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import me.test.first.spring.rs.entity.User;
import me.test.first.spring.rs.http.Range;
import me.test.first.spring.rs.http.SortBy;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UrlPathHelper;

/*
 * 创建资源：
 * POST 作用于集合资源，
 * 比如向 http://test.me/articles/ 使用post新增加一篇文章，则由服务端在该“目录”，
 * 创建该资源，然后将该资源的URL返回。
 * 也即：用户请求的URL是个目录，而返回的新的URL由服务器端确定。
 *
 * 注意：URL模板必须全部一致，比如：
 * 对于GET请求你使用的URL路径模板如果是 "/student/{studentNum}" 的话，
 * 对于POST请求你也应该使用相同的路径模板，而不能使用 "/student/{name}"，
 * 否则，在URL处理时会出错。比如若GET请求 "/student/041110108.xml" 的时候，
 * 期待的studentNum="041110108"，但是实际会是 studentNum="041110108.xml"。
 * 大家可以带上源代码，DEBUG一下，请在以下两个地方打断点：
 * AnnotationMethodHandlerAdapter#extractHandlerMethodUriTemplates()
 * AbstractUrlHandlerMapping#exposeUriTemplateVariables()
 *
 * PUT 作用于单个资源
 * 比如向 http://test.me/articles/Hello+World 则，用户请求的URL就是会被创建，（不是目录，而是实实在在的资源）
 */
// http://wenku.baidu.com/view/8f8f2025ccbff121dd36832e.html
// http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
// http://www.oracle.com/technetwork/articles/javase/index-137171.html
// http://www.javahotchocolate.com/tutorials/restful.html
// http://www.nbtconsulting.com/rest-web-service-demo/demo-school-outline.html
// http://www.jpalace.org/docs/spring/rest.html
// http://en.wikipedia.org/wiki/Representational_State_Transfer
// http://stackoverflow.com/questions/1601992/spring-3-json-with-mvc
// http://code.google.com/p/spring-finance-manager/
@Controller
@RequestMapping("/user")
public class UserController {
    public final static int maxStudents = 10;

    private Map<Long, User> userMap = new HashMap<Long, User>();

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private MessageSource messageSource = null;

    public UserController() {
        User user;

        byte[] avatar;
        try {
            avatar = IOUtils.toByteArray(appCtx.getResource("classpath:avatar.png").getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 1; i <= 105; i++) {
            user = new User();
            user.setId(Long.valueOf((long) i));
            user.setName("zhang3_" + i);
            user.setGender(true);
            user.setAvatar(avatar);
            user.setBirthday(DateTime.now().withDate(1985, 6, 1).withTime(0, 0, 0, 0).plusDays(i - 1).toDate());
            user.setEmail("zhang3@gmail.com");
            user.setIdCardNo("110101201301012418");
            userMap.put(user.getId(), user);
        }

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<User> list(@RequestHeader("Range") Range range, @RequestParam SortBy sortBy) {
        return null;
    }

    /**
     *
     * <table>
     * <tr>
     * <th>状态码</th>
     * <th>含义</th>
     * <th>消息</th>
     * </tr>
     * <tr>
     * <td>200</td>
     * <td>成功</td>
     * <td>消息</td>
     * </tr>
     * </table>
     *
     *
     */

    // 获取单个用户信息
    /**
     * <table>
     * <tr>
     * <th>状态码</th>
     * <th>含义</th>
     * <th>消息</th>
     * </tr>
     * <tr>
     * <td>200</td>
     * <td>成功</td>
     * <td>消息</td>
     * </tr>
     * </table>
     *
     *
     * @param id
     * @param request
     * @param response
     * @param req
     * @param principal
     * @param locale
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable("id") Long id) {

        // 如果请求的path上id的值无法转换为long型，会出发TypeMismathcException而返回400.
        // 没有合适的消息是否合适？
        return null;
    }

    /**
     * 构建学生信息新的绝对URL。
     *
     * @param request
     *            Http request
     * @param studentNum
     *            学生编号
     * @return 学生信息新的绝对URL
     */
    public static URI buildLocation(HttpServletRequest request, String subPath) {
        StringBuffer reqUrl = request.getRequestURL();
        UrlPathHelper urlHelper = new UrlPathHelper();
        String contextPath = urlHelper.getContextPath(request);
        String servletPath = urlHelper.getServletPath(request);
        String rootPath = contextPath + servletPath;
        int idx = reqUrl.indexOf(rootPath);
        reqUrl.setLength(idx + rootPath.length());
        reqUrl.append(subPath);
        // url.indexOf(urlHelper)
        reqUrl.append("/{studentNum}");

        // UrlPathHelper
        // urlHelper.getContextPath(req)= /first-spring-rs
        // urlHelper.getServletPath(req) = /app
        // urlHelper.getPathWithinServletMapping(req) = /student/3.xml

        // Virtual Host
        // urlHelper.getContextPath(request) = ""
        // urlHelper.getServletPath(request) = /app
        // urlHelper.getPathWithinServletMapping(request)=/student/3.xml

        URI newUri = null;
        try {
            newUri = new URI(reqUrl.toString());
        } catch (URISyntaxException e) {
            RuntimeException exp = new RuntimeException(e);
            throw exp;
        }
        return newUri;
    }

    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.add("field 1", "value 1");
        form.add("field 2", "value 2");
        form.add("field 2", "value 3");
        form.add("height", "1.8011");
        URI url = template.postForLocation(
                "http://localhost:8080/first-spring-rs/app/student/3", form);
        System.out.println(url);
    }
}
