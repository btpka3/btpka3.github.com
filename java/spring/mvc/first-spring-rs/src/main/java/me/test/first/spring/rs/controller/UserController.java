package me.test.first.spring.rs.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.test.first.spring.rs.entity.ListWrapper;
import me.test.first.spring.rs.entity.User;
import me.test.first.spring.rs.exception.BusinessException;
import me.test.first.spring.rs.http.ContentRange;
import me.test.first.spring.rs.http.Range;
import me.test.first.spring.rs.http.SortBy;
import me.test.first.spring.rs.http.SortBy.Item;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.LastModified;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
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

/**
 *
 * <table border=1 cellspacing=0 cellpadding=0 >
 * <tr>
 * <th>URL</th>
 * <th>HTTP方法</th>
 * <th>作用</th>
 * </tr>
 * <tr>
 * <td>/user</td>
 * <td>GET</td>
 * <td>查询用户列表</td>
 * </tr>
 * <tr>
 * <td>/user</td>
 * <td>POST</td>
 * <td>新增用户</td>
 * </tr>
 * <tr>
 * <td>/user/{id}</td>
 * <td>HEAD</td>
 * <td>检查资源是否可用</td>
 * </tr>
 * <tr>
 * <td>/user/{id}</td>
 * <td>GET</td>
 * <td>查询指定ID的用户信息</td>
 * </tr>
 * <tr>
 * <td>/user/{id}</td>
 * <td>PUT</td>
 * <td>更新指定ID的用户信息</td>
 * </tr>
 * <tr>
 * <td>/user/{id}</td>
 * <td>DELETE</td>
 * <td>删除指定ID的用户信息</td>
 * </tr>
 * </table>
 *
 */
@Controller
@RequestMapping("/user")
public class UserController implements LastModified {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Map<Long, User> userMap = new LinkedHashMap<Long, User>();

    /** 每条的最后修改时间 */
    private final Map<Long, Long> lastModifiedMap = new LinkedHashMap<Long, Long>();

    /** 所有记录的最后修改时间，相当于整个表的最后修改时间 */
    // when using DB, this should be `SELECT MAX(VERSION) FROM T_USER`
    private long lastModified = 0;

    // 未指定分页，或分页后的数据最多能显示的最大数量
    private int maxRecords = 15;

    @Autowired
    private UrlPathHelper urlPathHelper = null;

    @Autowired
    private FileController fileController = null;

    @PostConstruct
    public void init() {
        lastModified = System.currentTimeMillis();
        for (User user : genTestData(35)) {
            userMap.put(user.getId(), user);
            lastModifiedMap.put(user.getId(), lastModified);
        }
    }

    // 模拟数据库进行查询、排序
    private List<User> query(final String name, final SortBy sortBy) {

        List<User> resultList = new ArrayList<User>();
        if (name == null || name.trim().length() == 0) {
            resultList.addAll(userMap.values());
        } else {

            Iterator<User> it = userMap.values().iterator();
            while (it.hasNext()) {

                User user = it.next();
                if (user.getName() != null && user.getName().contains(name)) {
                    resultList.add(user);
                }
            }
        }

        // sort
        if (sortBy != null) {

            Collections.sort(resultList, new UserComparator(sortBy));
        }

        return resultList;
    }

    /**
     *
     * <ul>
     * 会返回的状态码：400, 200, 206
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
    public ListWrapper list(
            @RequestHeader(value = "Range", required = false) Range range,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sortBy", required = false) SortBy sortBy,
            WebRequest req,
            HttpServletResponse resp) {

        logger.debug("SortBy = " + sortBy);

        if (req.checkNotModified(lastModified)) {
            return null;
        }

        List<User> resultList = query(name, sortBy);

        List<User> rtnList = resultList;

        if (range == null) {
            resp.setStatus(HttpStatus.OK.value());
        } else {
            Integer start = range.getStart();
            Integer end = range.getEnd();

            if (start > resultList.size() || (end != null && end > resultList.size())) {
                resp.setStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value());
                resp.setHeader("Content-Range",
                        new ContentRange(0, resultList.size() - 1, resultList.size()).toString());
                return null;
            }

            if (end >= resultList.size()) {
                end = resultList.size() - 1;
            }
            rtnList = resultList.subList(start, end + 1);
            resp.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            resp.setHeader("Content-Range", new ContentRange(start, end, resultList.size()).toString());
        }

        if (rtnList.size() > maxRecords) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "too much records, please using paging.");
        }

        ListWrapper data = new ListWrapper();
        data.getData().addAll(rtnList);
        return data;
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
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "user info could not be null");
        }
        if (user.getId() != null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(),
                    "user id is generated at server, could not be specified by client");
        }

        Long newId = 0L;
        for (User u : userMap.values()) {
            newId = u.getId() > newId ? u.getId() : newId;
        }
        newId++;
        user.setId(newId);

        // String uri = urlPathHelper.getRequestUri(req)+"/user/"+newId;
        String uri = UriComponentsBuilder.newInstance().path("{contextPath}{servletPath}/user/{id}")
                .build()
                .expand(urlPathHelper.getContextPath(req),
                        urlPathHelper.getServletPath(req),
                        newId)
                .encode()
                .toUriString();
        resp.setHeader("Location", uri);

        resp.setStatus(HttpStatus.CREATED.value());

        userMap.put(newId, user);
        lastModified = System.currentTimeMillis();
        lastModifiedMap.put(newId, lastModified);

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
    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public void head(@PathVariable("id") String idStr, HttpServletResponse resp) {
        Long id = null;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "user with id =" + id + " not exists");
        }

        if (!userMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "user with id =" + id + " not exists");
        }

        resp.setStatus(HttpStatus.NO_CONTENT.value());
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
     * {@link org.springframework.http.HttpStatus#OK
     * HttpStatus.OK }</li>
     * </ul>
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User get(@PathVariable("id") String idStr, WebRequest req, HttpServletResponse resp) {

        head(idStr, resp);
        Long id = Long.valueOf(idStr);
        if (req.checkNotModified(lastModifiedMap.get(id))) {
            return null;
        }
        resp.setStatus(HttpStatus.OK.value());

        // 如果请求的path上id的值无法转换为long型，会出发TypeMismathcException而返回400.
        // 没有合适的消息是否合适？

        return userMap.get(id);
    }

    /**
     * 更新用户信息。
     * 但是不允许设定新ID。
     * PUT是完整更新，而不是部分更新。
     * TODO 如果有乐观锁（比如时间戳）机制，则需要先判断更新前的锁是否一致，更新完成后还要再更新乐观锁的值。
     * FIXME 或者使用If-Match、If-Modified-Since?
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
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void put(@PathVariable("id") String idStr, @RequestBody User newUser,
            HttpServletResponse resp) {

        head(idStr, resp);

        Long id = Long.valueOf(idStr);

        if (!id.equals(newUser.getId())) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "Can not chage user id");
        }

        if (newUser.getHeight() != null && newUser.getHeight() < 0) {
            throw new BusinessException(HttpStatus.BAD_REQUEST.value(), "height must be positive integer ");
        }

        if (newUser.getAvatarId() != null) {
            // 检查要设置的头像资源是否存在
            fileController.head(newUser.getAvatarId().toString(), resp);
        }

        // 更新
        User user = userMap.get(id);
        try {
            PropertyUtils.copyProperties(user, newUser);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

        resp.setStatus(HttpStatus.NO_CONTENT.value());
        lastModified = System.currentTimeMillis();
        lastModifiedMap.put(id, lastModified);

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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String idStr, HttpServletResponse resp) {
        Long id = null;
        try {
            id = Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "user with id =" + id + " not exists");
        }
        if (!userMap.containsKey(id)) {
            throw new BusinessException(HttpStatus.NOT_FOUND.value(), "user with id =" + id + " not exists");

        }
        userMap.remove(id);
        lastModified = System.currentTimeMillis();
        lastModifiedMap.remove(id);
        resp.setStatus(HttpStatus.NO_CONTENT.value());
    }

    @Override
    public long getLastModified(HttpServletRequest request) {

        // 可以忽略URL参数和HTTP Header
        // 当它们不同时，是否发送 If-Match、If-Modified-Since 应由浏览器缓存决定。
        String mappingUri = urlPathHelper.getPathWithinServletMapping(request);
        if ("/user".equals(mappingUri)) {
            return lastModified;
        }

        UriTemplate uriTemplate = new UriTemplate("/user/{id}");
        if (uriTemplate.matches(mappingUri)) {
            Map<String, String> pathVarMap = uriTemplate.match(mappingUri);
            try {
                Long id = Long.valueOf(pathVarMap.get("id"));
                if (!lastModifiedMap.containsKey(id)) {
                    return -1;
                }
                Long time = lastModifiedMap.get(id);
                if (time == null) {
                    return -1;
                }
                return time;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
        logger.warn("request last modifed time for path \"" + mappingUri + "\", not supported, will return -1.");
        return -1;
    }

    public static List<User> genTestData(int count) {

        List<User> list = new ArrayList<User>(count);
        for (int i = 1; i <= 35; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("zhang3_" + i);

            // Boolean v = (i / 10) % 3 == 0 ? Boolean.TRUE : (i / 10) % 3 == 1
            // ? false : null;

            if (i % 10 == 0) {
                user.setGender(null);
                user.setHeight(null);
            } else {
                user.setGender(i % 3 == 1 ? null : i % 3 == 2 ? Boolean.TRUE : Boolean.FALSE);
                int j = i % 10;
                user.setHeight(j % 3 != 0 ? Integer.valueOf(180 + j) : null);

            }
            user.setAvatarId(1L);

            user.setBirthday(DateTime.now().withDate(1985, 6, 1).withTime(0, 0, 0, 0).plusDays((int) (i - 1)).toDate());
            list.add(user);
        }
        return list;
    }

    public static void main(String[] args) {

        List<User> l = genTestData(35);
        for (User u : l) {
            System.out.printf("id=%s, height=%d, gender=%s %n", u.getId(), u.getHeight(), u.getGender());
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        final SortBy sortBy = SortBy.valueOf("+height,-gender");
        Collections.sort(l, new UserComparator(sortBy));
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        for (User u : l) {
            System.out.printf("id=%s, height=%d, gender=%s %n", u.getId(), u.getHeight(), u.getGender());
        }

    }
}

class UserComparator implements Comparator<User> {
    private SortBy sortBy;
    public UserComparator(SortBy sortBy) {
        this.sortBy = sortBy;
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public int compare(User user1, User user2) {
        List<Item> items = sortBy.getItems();
        for (Item item : items) {
            try {
                String attr = item.getAttribute();

                Comparable v1 = null;
                Comparable v2 = null;
                if ("avatar".equals(attr)) {
                    byte[] v = (byte[]) PropertyUtils.getProperty(user1, attr);
                    if (v != null) {
                        v1 = Base64.encodeBase64String(v);
                    }
                    v = (byte[]) PropertyUtils.getProperty(user2, attr);
                    if (v != null) {
                        v2 = Base64.encodeBase64String(v);
                    }
                } else {
                    v1 = (Comparable) PropertyUtils.getProperty(user1, attr);
                    v2 = (Comparable) PropertyUtils.getProperty(user2, attr);
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

}
