package me.test.first.spring.rs;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.first.spring.rs.entity.User;
import me.test.first.spring.rs.exception.BusinessException;
import me.test.first.spring.rs.http.Range;
import me.test.first.spring.rs.http.SortBy;
import me.test.first.spring.rs.http.SortBy.Item;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public final static int maxStudents = 10;

    private Map<Long, User> userMap = new HashMap<Long, User>();

    @Autowired
    private ApplicationContext appCtx;

    @Autowired
    private MessageSource messageSource = null;

    @Autowired
    private UrlPathHelper urlPathHelper = null;

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
            userMap.put(user.getId(), user);
        }

    }

    // 模拟数据库进行查询、排序
    @SuppressWarnings("rawtypes")
    private List<User> query(final Map<String, Object> queryBean, final SortBy sortBy) {

        List<User> resultList = new ArrayList<User>();
        if (queryBean == null) {
            resultList.addAll(userMap.values());
        } else {

            for (String key : queryBean.keySet()) {
                if (!"name".equals(key)) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "Not supported query parameter for \"" + key
                            + "\"");
                }
            }

            Iterator<User> it = userMap.values().iterator();
            while (it.hasNext()) {

                User user = it.next();
                if (user.getName() != null && user.getName().contains((String) queryBean.get("name"))) {
                    resultList.add(user);
                }
            }
        }

        // sort
        if (sortBy != null) {

            Collections.sort(resultList, new Comparator<User>() {

                @Override
                public int compare(User user1, User user2) {
                    List<Item> items = sortBy.getItems();
                    for (Item item : items) {
                        try {
                            String attr = item.getAttribute();

                            Comparable v1 = null;
                            Comparable v2 = null;
                            if ("avatar".equals(attr)) {
                                byte[] v = (byte[]) PropertyUtils.getMappedProperty(user1, attr);
                                if (v != null) {
                                    v1 = Base64.encodeBase64String(v);
                                }
                                v = (byte[]) PropertyUtils.getMappedProperty(user2, attr);
                                if (v != null) {
                                    v2 = Base64.encodeBase64String(v);
                                }
                            } else {
                                v1 = (Comparable) PropertyUtils.getMappedProperty(user1, attr);
                                v2 = (Comparable) PropertyUtils.getMappedProperty(user2, attr);
                            }

                            // null as max value

                            if (v1 != null) {
                                if (v2 == null) {
                                    return -1;
                                } else {
                                    if (v1.compareTo(v2) != 0) {
                                        return v1.compareTo(v2);
                                    }
                                }
                            } else {
                                if (v2 != null) {
                                    return 1;
                                }
                            }

                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return 0;
                }

            });
        }

        return resultList;
    }

    /**
     *
     * <ul>
     * 会返回的状态码：
     * <li>
     * {@link org.springframework.http.HttpStatus#BAD_REQUEST
     * HttpStatus.BAD_REQUEST }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#OK
     * HttpStatus.OK }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#PARTIAL_CONTENT
     * HttpStatus.PARTIAL_CONTENT }</li>
     * </ul>
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<User> list(@RequestBody Map<String, Object> queryBean,
            @RequestParam SortBy sortBy, @RequestHeader("Range") Range range, HttpServletResponse resp) {

        logger.debug(" queryBean = " + queryBean + ", SortBy = " + sortBy);

        List<User> resultList = query(queryBean, sortBy);
        int start = 0;
        int end = 10;
        if (range != null) {
            start = range.getStart();
            end = range.getEnd();
            if (start + end > resultList.size()) {
                end = resultList.size();
            }
        }

        if (start == 0 && end == resultList.size()) {
            resp.setStatus(HttpStatus.OK.value());
        } else {
            resp.setStatus(HttpStatus.PARTIAL_CONTENT.value());
        }

        return resultList.subList(start, end);
    }

    /**
     *
     * 新建一个用户。
     *
     * 注意：新建用户时，一般是通过文件服务异步将头像等文件先传上去，再在表单中进行预览。
     * 再提交表单时，就只有包含刚刚上传的文件ID即可。
     *
     * 会返回的状态码： <li>
     * {@link org.springframework.http.HttpStatus#CREATED
     * HttpStatus.CREATED }</li> </ul>
     *
     * 注意：异步处理时，应当返回 {@link org.springframework.http.HttpStatus#Accepted
     * HttpStatus.Accepted } 状态码。
     *
     */
    @RequestMapping(method = RequestMethod.POST)
    public void post(@RequestBody User user, HttpServletRequest req, HttpServletResponse resp) {
        if (user == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "user info could not be null");
        }
        if (user.getId() != null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST,
                    "user id is generated at server, could not be specified by client");
        }

        Long newId = 0L;
        for (User u : userMap.values()) {
            newId = u.getId() > newId ? u.getId() : newId;
        }
        newId++;
        user.setId(newId);

        String contextPath = urlPathHelper.getContextPath(req);
        String servletPath = urlPathHelper.getServletPath(req);
        resp.setHeader("Location", contextPath + servletPath + "/user/" + newId);

        resp.setStatus(HttpStatus.CREATED.value());
    }

    /**
     * 获取单个用户信息。
     * <ul>
     * 会返回的状态码：
     * <li>{@link org.springframework.http.HttpStatus#NOT_FOUND
     * HttpStatus.NOT_FOUND }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#BAD_REQUEST
     * HttpStatus.BAD_REQUEST }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#NO_CONTENT
     * HttpStatus.NO_CONTENT }</li>
     * </ul>
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id") String idStr, HttpServletResponse resp) {
        Long id = null;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user with id =" + id + " not exists");
        }

        if (!userMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user with id =" + id + " not exists");
        }

        resp.setStatus(HttpStatus.OK.value());

        // 如果请求的path上id的值无法转换为long型，会出发TypeMismathcException而返回400.
        // 没有合适的消息是否合适？
        return userMap.get(id);
    }

    /**
     * 更新用户信息。
     * 但是不允许设定新ID。
     *
     * <ul>
     * 会返回的状态码：
     * <li>{@link org.springframework.http.HttpStatus#NOT_FOUND
     * HttpStatus.NOT_FOUND }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#BAD_REQUEST
     * HttpStatus.BAD_REQUEST }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#NO_CONTENT
     * HttpStatus.NO_CONTENT }</li>
     * </ul>
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void put(@PathVariable("id") String idStr, @RequestBody Map<String, Object> updatingInfo,
            HttpServletResponse resp) {

        Long id = null;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user with id =" + id + " not exists");
        }
        if (!userMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user with id =" + id + " not exists");
        }

        User user = userMap.get(id);

        if (updatingInfo == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Updating user info can not be null");
        }

        if (!id.equals(updatingInfo.get("id"))) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Can not chage user id");
        }

        Object value = null;
        if (updatingInfo.containsKey("name")) {
            value = updatingInfo.get("name");
            user.setName(value == null ? null : value.toString());
        }

        if (updatingInfo.containsKey("gender")) {
            value = updatingInfo.get("gender");
            if (value == null) {
                user.setGender(null);
            } else {
                if (!(value instanceof Boolean)) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "gender must be of type boolean");
                }
                user.setGender((Boolean) value);
            }
        }

        if (updatingInfo.containsKey("birthday")) {
            value = updatingInfo.get("birthday");
            if (value == null) {
                user.setGender(null);
            } else {
                if (!(value instanceof String)) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "birthday must be of type date");
                }
                try {

                    Date birthday = ISODateTimeFormat.dateParser().parseDateTime((String) value).toDate();
                    // Date birthday = DatatypeConverter.parseDate((String)
                    // value).getTime();
                    user.setBirthday(birthday);

                } catch (Exception e) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "birthday must be of type date");
                }
            }
        }

        if (updatingInfo.containsKey("height")) {
            value = updatingInfo.get("height");
            if (value == null) {
                user.setHeight(null);
            } else {
                Integer height = null;
                if (value instanceof String) {
                    try {
                        height = Integer.valueOf((String) value);
                    } catch (NumberFormatException e) {
                        throw new BusinessException(HttpStatus.BAD_REQUEST, "height must be positive integer ", e);
                    }
                } else if (value instanceof Integer) {
                    height = (Integer) value;
                } else {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "height must be positive integer ");
                }

                if (height < 0) {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "height must be positive integer ");
                }
                user.setHeight(height);

            }
        }

        if (updatingInfo.containsKey("avatar")) {
            value = updatingInfo.get("avatar");
            if (value == null) {
                user.setAvatar(null);
            } else {
                byte[] avatar = null;
                if (value instanceof String) {
                    if (!Base64.isBase64((String) value)) {
                        throw new BusinessException(HttpStatus.BAD_REQUEST, "avatar must be image encoded in base64");
                    }
                    avatar = Base64.decodeBase64((String) value);
                } else {
                    throw new BusinessException(HttpStatus.BAD_REQUEST, "avatar must be image encoded in base64");
                }

                user.setAvatar(avatar);
            }
        }

        resp.setStatus(HttpStatus.NO_CONTENT.value());

    }

    /**
     * 删除指定的用户。
     *
     * <ul>
     * 会返回的状态码：
     * <li>
     * {@link org.springframework.http.HttpStatus#NOT_FOUND
     * HttpStatus.NOT_FOUND }</li>
     * <li>
     * {@link org.springframework.http.HttpStatus#NO_CONTENT
     * HttpStatus.NO_CONTENT }</li>
     * </ul>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable("id") String idStr, HttpServletResponse resp) {
        Long id = null;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user with id =" + id + " not exists");
        }
        if (!userMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "user with id =" + id + " not exists");

        }
        userMap.remove(id);
        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }

}
