package me.test;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.*;
import org.apache.http.client.config.*;
import org.apache.http.config.*;
import org.apache.http.conn.util.*;
import org.apache.http.cookie.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * 需要先分析 http请求,在模拟http请求的数据,使用编程的方式进行登录. 适合:
 * Http请求中没有加密数据,没有复杂JS,ActiveX控件计算数据的情形.
 * 
 * 参考:
 * http://hc.apache.org/httpcomponents-client-4.5.x/tutorial/html/statemgmt.html#d5e499
 */
@Configuration
public class ZXZQ1 {

    /** 分类 */
    public static enum Category {

        /** 汽车房产 */
        CAR_HOUSE("1001"),
        /** 手机数码 */
        MOBILE("1002"),
        /** 电脑办公 */
        PC("1003"),
        /** 电脑办公 */
        APPLIANCE("1004"),
        /** 女神良品 */
        WOMEN("1005"),
        /** 男士专区 */
        MEN("1006");
        // TODO 未完待续

        private Category(String code) {
            this.code = code;
        }

        private String code;

        public String getCode() {
            return code;
        }
    }

    private static Logger logger = LoggerFactory.getLogger(ZXZQ1.class);
    private static AnnotationConfigApplicationContext ctx = null;

    // 服务器返回的 数据
    /*
     *  {
     *      userId : xxx,
     *      category: {
     *          ${Category} : {
     *              1: [{         // 第一页的数据
     *                  name    : "",       // 商品名称
     *                  price   : 0,        // 商品价格
     *                  itemId  : "",       // 商品ID （较短，下单用）
     *                  itemCode: ""        // 商品代码
     *              },{}]
     *          }
     *      
     *      }
     *  }
     */
    private static Map<String, Object> data = new LinkedHashMap<String, Object>();

    static {

        Map<Category, Map<Integer, Object>> categoryData = new LinkedHashMap<Category, Map<Integer, Object>>();

        for (Category c : Category.values()) {
            categoryData.put(c, new LinkedHashMap<Integer, Object>());
        }

        data.put("category", categoryData);
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

        String userName = "xx";
        String pwd = "xx";
        String toBuyItemId = "xx";

        ctx = new AnnotationConfigApplicationContext();
        ctx.register(ZXZQ1.class);
        ctx.refresh();

        // 测试1： 获取特定分类的商品列表
        getItemsByCategory(Category.MEN, 1);
        printItem(Category.MEN);

        // 测试2： 登录
        isLogined();
        index();
        login(userName, pwd);
        isLogined();
        //
        //        // 测试3： 单品直接下单：
        //        // seq,     id,     code,      price, name
        //        //   1,   1160,   342825,    7769.00, (第122期)佳能 EOS 70D 套机
        //        //  29,   1161,   256386,     189.00, (第706期)丽芙 LifeVC 无线蓝牙天籁耳机
        //        getItemRemainCount(toBuyItemId);
        //        buy(toBuyItemId, 1);
        //

        // 测试4： 通过购物车批量下单
        // seq,     id,     code,      price, name
        //  10,   1165,   237474,      99.00, (第1134期)小米手环
        //  11,   1219,   300325,     149.00, (第234期)zippo打火机黑冰150zl

        getItemRemainCount("1165");
        addToCartCookie("1165", 1);
        syncCartCookieToServer();

        getItemRemainCount("1219");
        addToCartCookie("1219", 2);
        syncCartCookieToServer();

        listCart();
        submitCart();
        payCart();

        // 测试5： 检查下单结果
        getUserBoughtList(0, (String) data.get("userId"), null, null, null);

    }

    // 主页
    @SuppressWarnings("unchecked")
    public static void index() {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        String url = "http://www.qmduobao.com/";
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode(), "ERROR, http 状态码是 " + resp.getStatusCode());

        // 获取 cookie: "JSESSIONID"
        CookieStore cookieStore = ctx.getBean(CookieStore.class);
        logger.debug("访问主页后，获得的cookie如下：" + cookieStore.getCookies());
    }

    // 登录
    @SuppressWarnings("unchecked")
    public static void login(String userName, String pwd) throws JsonParseException, JsonMappingException, IOException {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        String url = "http://www.qmduobao.com/login/login.html";

        // param
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("userName", userName);
        params.add("pwd", pwd);

        // login
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<MultiValueMap<String, String>>(params,
                headers);

        ResponseEntity<String> resp = restTemplate.postForEntity(url, reqEntity, String.class);
        String respStr = resp.getBody();
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode(), "登录失败,http 状态码是 " + resp.getStatusCode());
        Assert.isTrue(!"userError".equals(respStr), "用户名不存在");
        Assert.isTrue(!"pwdError".equals(respStr), "密码错误");

        // String -> JSON
        ObjectMapper objectMapper = ctx.getBean(ObjectMapper.class);
        Map<String, ?> jsonObj = objectMapper.readValue(respStr, Map.class);
        String userId = jsonObj.get("userId").toString();
        data.put("userId", userId);
        logger.debug("登录成功,用户ID = " + userId);

        CookieStore cookieStore = ctx.getBean(CookieStore.class);

        // 模拟JS设置相关cookie
        BasicClientCookie cookie = new BasicClientCookie("loginType", "0");
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        cookie = new BasicClientCookie("loginUser", (String) jsonObj.get("phone"));
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        cookie = new BasicClientCookie("userName", (String) jsonObj.get("phone"));
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        userId = jsonObj.get("userId").toString();
        cookie = new BasicClientCookie("userId", jsonObj.get("userId").toString());
        cookie.setDomain(".qmduobao.com");
        cookie.setPath("/");
        cookie.setAttribute(ClientCookie.PATH_ATTR, "/");
        cookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
        cookieStore.addCookie(cookie);

        logger.debug("手动追加部分cookie ： " + cookieStore.getCookies());
    }

    // 主页 : 最新揭晓 （Ajax）
    @SuppressWarnings("unchecked")
    public static void resultToBePublished() {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        String url = "http://www.qmduobao.com/lottery/lotteryproductutilList.html";
        // TODO 
    }

    /**
     * 特定商品分类下的商品ID
     * 
     * http://www.qmduobao.com/list/hot20/1002/p_1.html
     * 
     * @param category
     * @param pageNo
     *            页码，从1开始
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    public static void getItemsByCategory(
            Category category,
            int pageNo) throws UnsupportedEncodingException {

        pageNo = pageNo <= 0 ? 1 : pageNo;
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/list/hot20/{categoryCode}/p_{pageNo}.html")
                .build()
                .expand(category.getCode(), pageNo)
                .encode("UTF-8")
                .toUri();

        ResponseEntity<String> resp = restTemplate.getForEntity(uri, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());

        Document doc = Jsoup.parse(resp.getBody());

        Elements ulGoodsList = doc.select("#ulGoodsList > div[goodsid]");

        List pageDataList = new ArrayList(ulGoodsList.size());

        for (int i = 0; i < ulGoodsList.size(); i++) {

            Element itemDom = ulGoodsList.get(i);
            Map item = new LinkedHashMap();

            item.put("name", itemDom.select("li.soon-list-name > a").text());
            item.put("price", itemDom.select(".gray6").first().text().replace("价值：￥", ""));
            item.put("itemId", itemDom.attr("goodsid"));
            item.put("itemCode", itemDom.attr("codeid"));
            pageDataList.add(item);
        }

        Map<Integer, Object> curCategoryData = (Map<Integer, Object>) ((Map<Category, Object>) data.get("category"))
                .get(category);
        curCategoryData.put(pageNo, pageDataList);

        // 页次 : "1/2"
        String pages = doc.select("#pagination > li.total_page > i").first().text();
        int totalPage = Integer.parseInt(pages.split("/")[1]);

        if (pageNo < totalPage) {
            getItemsByCategory(category, pageNo + 1);
        }
    }

    private static void printItem(Category category) {
        logger.debug("目前获取的商品列表如下：（分类：{}）", category);
        logger.debug(String.format("%4s, %6s, %8s, %10s, %s",
                "seq",
                "id",
                "code",
                "price",
                "name"));

        Map<Integer, Object> curCategoryData = (Map<Integer, Object>) ((Map<Category, Object>) data.get("category"))
                .get(category);

        int i = 0;
        for (Object pageData : curCategoryData.values()) {
            for (Object itemData : ((List) pageData)) {
                logger.debug(String.format("%4s, %6s, %8s, %10s, %s",
                        i,
                        ((Map) itemData).get("itemId"),
                        ((Map) itemData).get("itemCode"),
                        ((Map) itemData).get("price"),
                        ((Map) itemData).get("name")));
                i++;
            }
        }

    }

    // 是否已经登录
    // http://www.qmduobao.com/user/UserBuyList.html
    @SuppressWarnings("unchecked")
    public static boolean isLogined() throws JsonParseException, JsonMappingException, IOException {

        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/user/UserBuyList.html")
                .build()
                .encode("UTF-8")
                .toUri();

        ResponseEntity<String> resp = restTemplate.getForEntity(uri, String.class);

        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());
        String respStr = resp.getBody();

        // 包含则代表已经登录
        boolean logined = respStr.contains("夺宝记录");

        logger.debug("登录检查结果 = {}", logined ? "已登录" : "未登录");
        return logined;

    }

    // 获取已登录用户的夺宝列表
    @SuppressWarnings("unchecked")
    public static void getUserBoughtList(
            int pageNo, // 从0开始
            String userId,
            String selectTime,
            String startDate,
            String endDate)
            throws JsonParseException,
            JsonMappingException,
            IOException {

        selectTime = selectTime == null ? "0" : selectTime;

        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/user/userBuyListAjaxPage.action")
                .queryParam("pageNo", pageNo < 0 ? 0 : pageNo)
                .queryParam("userId", userId)
                .queryParam("selectTime", selectTime)
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .build()
                .encode("UTF-8")
                .toUri();

        logger.debug("最新夺宝记录如下：");

        // get
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());
        String respStr = resp.getBody();
        logger.debug(respStr);

    }

    // 查询特定宝贝的剩余数量（Ajax）
    // http://www.qmduobao.com/list/isStatus.action?id=1137
    @SuppressWarnings("unchecked")
    public static int getItemRemainCount(String itemId) throws JsonParseException, JsonMappingException, IOException {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/list/isStatus.action")
                .queryParam("id", itemId)
                .build()
                .encode("UTF-8")
                .toUri();

        // get
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);

        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());
        String respStr = resp.getBody();
        int cremainCount = Integer.parseInt(respStr);
        logger.debug("商品 {} 剩余份数： {} ", itemId, cremainCount);
        return cremainCount;
    }

    // 主页：立即全民夺宝（Ajax）
    // http://www.qmduobao.com/mycart/getShopResult.action?id=1137&moneyCount=1
    @SuppressWarnings("unchecked")
    public static void buy(
            String itemId,
            int moneyCount) throws JsonParseException, JsonMappingException, IOException {

        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/mycart/getShopResult.action?")
                .queryParam("id", itemId)
                .queryParam("moneyCount", moneyCount <= 0 ? 1 : moneyCount)
                .build()
                .encode("UTF-8")
                .toUri();

        // get
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());

        String respStr = resp.getBody();
        logger.debug("对商品 {} 下单结果为 ： {} ", itemId, respStr);
    }

    public static void clearCartCookie() {
        CookieStore cookieStore = ctx.getBean(CookieStore.class);

        // 找到既有的 "products" cookie
        List<Cookie> cookies = cookieStore.getCookies();
        BasicClientCookie productsCookie = null;
        for (Cookie c : cookies) {
            if ("products".equals(c.getName())) {
                productsCookie = (BasicClientCookie) c;
                break;
            }
        }
        if (productsCookie != null) {
            cookies.remove(productsCookie);
        }
    }

    public static void listCart() throws JsonParseException, JsonMappingException, IOException {

        List<Map> products = getCartFromCookie();

        logger.debug("目前购物车列表下：");
        logger.debug(String.format("%4s, %6s, %4s",
                "seq",
                "id",
                "num"));

        int i = 0;
        for (Map item : products) {
            logger.debug(String.format("%4s, %6s, %4s",
                    i,
                    item.get("pId"),
                    item.get("num")));
            i++;
        }
    }

    // 加入购物车（Ajax）
    // http://www.qmduobao.com/mycart/getShopResult.action?id=1137&moneyCount=1

    // COOKIE :
    // products=%5B%7B%22pId%22%3A1137%2C%22num%22%3A2%7D%2C%7B%22pId%22%3A1092%2C%22num%22%3A1%7D%2C%7B%22pId%22%3A1112%2C%22num%22%3A1%7D%5D
    // products=[{"pId":1137,"num":2},{"pId":1092,"num":1},{"pId":1112,"num":1}]
    @SuppressWarnings("unchecked")
    public static void addToCartCookie(
            String itemId,
            int num) throws JsonParseException, JsonMappingException, IOException {

        CookieStore cookieStore = ctx.getBean(CookieStore.class);

        // 找到既有的 "products" cookie
        List<Cookie> cookies = cookieStore.getCookies();
        BasicClientCookie productsCookie = null;
        for (Cookie c : cookies) {
            if ("products".equals(c.getName())) {
                productsCookie = (BasicClientCookie) c;
                break;
            }
        }
        if (productsCookie == null) {
            productsCookie = new BasicClientCookie("products", URLEncoder.encode("[]", "UTF-8"));
            productsCookie.setDomain(".qmduobao.com");
            productsCookie.setPath("/");
            productsCookie.setAttribute(ClientCookie.PATH_ATTR, "/");
            productsCookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".qmduobao.com");
            cookieStore.addCookie(productsCookie);
        }

        String jsonStr = productsCookie.getValue();
        jsonStr = URLDecoder.decode(jsonStr, "UTF-8");

        ObjectMapper objectMapper = ctx.getBean(ObjectMapper.class);
        List products = objectMapper.readValue(jsonStr, List.class);
        logger.debug(" ===== " + products);

        // 找到既有的商品
        Map product = null;
        for (Object p : products) {
            if (itemId.equals(((Map) p).get("pId"))) {
                product = (Map) p;
                break;
            }
        }

        if (product == null) {
            product = new LinkedHashMap();
            products.add(product);
            product.put("pId", itemId);
            product.put("num", 0);
        }

        product.put("num", ((Integer) product.get("num")) + num);

        // list -> json -> url encode -> cookie
        ObjectMapper objectMapper1 = ctx.getBean(ObjectMapper.class);
        String newCookieValue = objectMapper1.writeValueAsString(products);
        String newCookieValueEncoded = URLEncoder.encode(newCookieValue, "UTF-8");
        productsCookie.setValue(newCookieValueEncoded);
        logger.debug(" ### " + newCookieValue + ", encoded as : " + newCookieValueEncoded);
    }

    public static void syncCartCookieToServer() throws UnsupportedEncodingException {

        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/mycart/buyProductClick.html")
                .queryParam("date", System.currentTimeMillis())
                .build()
                .encode("UTF-8")
                .toUri();
        logger.debug("---  " + uri);

        // get
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());

        String respStr = resp.getBody();
        logger.debug("同步购物车， 结果为 ： {} ", respStr);
    }

    // 提交购物车
    public static void submitCart() throws JsonParseException, JsonMappingException, IOException {
        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        List<Map> products = getCartFromCookie();
        Assert.isTrue(products.size() > 0, "购物车为空，无法下单");
        logger.debug("提交购物车");

        StringBuilder buf = new StringBuilder();
        int money = 0;
        for (Map item : products) {
            buf.append(item.get("pId"));
            buf.append(",");

            money += (Integer) item.get("num");
        }
        buf.setLength(buf.length() - 1);
        String ids = buf.toString();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("hidLogined", data.get("userId").toString());
        params.add("hidCartState", "1");
        params.add("userPayType", "");
        params.add("hidTotalMoney", Integer.toString(money));
        params.add("id", ids);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/mycart/payment.html")
                .queryParam("date", System.currentTimeMillis())
                .build()
                .encode("UTF-8")
                .toUri();

        ResponseEntity<String> resp = restTemplate.postForEntity(uri, params, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());

    }

    // 支付购物车 (Ajax)
    public static void payCart() throws JsonParseException, JsonMappingException, IOException {

        List<Map> products = getCartFromCookie();
        Assert.isTrue(products.size() > 0, "购物车为空，无法下单");
        logger.debug("支付购物车");

        StringBuilder buf = new StringBuilder();
        int money = 0;
        for (Map item : products) {
            buf.append(item.get("pId"));
            buf.append(",");

            money += (Integer) item.get("num");
        }
        buf.setLength(buf.length() - 1);
        String ids = buf.toString();

        RestTemplate restTemplate = ctx.getBean(RestTemplate.class);

        URI uri = UriComponentsBuilder
                .fromHttpUrl("http://www.qmduobao.com/mycart/goPay.action")
                .queryParam("integral", "0")
                .queryParam("moneyCount", money)
                .queryParam("hidUseBalance", "1")
                .queryParam("bankMoney", "0")
                .queryParam("id", ids)
                .build()
                .encode("UTF-8")
                .toUri();

        // get
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Requested-With", "XMLHttpRequest");
        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
        ResponseEntity<String> resp = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);
        Assert.isTrue(HttpStatus.OK == resp.getStatusCode());
    }

    private static List getCartFromCookie() throws JsonParseException, JsonMappingException, IOException {

        CookieStore cookieStore = ctx.getBean(CookieStore.class);

        // 找到既有的 "products" cookie
        List<Cookie> cookies = cookieStore.getCookies();
        BasicClientCookie productsCookie = null;
        for (Cookie c : cookies) {
            if ("products".equals(c.getName())) {
                productsCookie = (BasicClientCookie) c;
                break;
            }
        }

        if (productsCookie == null) {
            return Collections.emptyList();
        }

        String jsonStr = URLDecoder.decode(productsCookie.getValue(), "UTF-8");
        ObjectMapper objectMapper = ctx.getBean(ObjectMapper.class);
        List products = objectMapper.readValue(jsonStr, List.class);
        return products;
    }

    // ------------------------------------------------------- Spring Bean 配置区域

    @Bean
    @Scope("prototype")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Scope("singleton")
    public CookieStore cookieStore() {
        return new BasicCookieStore();
    }

    @Bean
    @Scope("singleton")
    public HttpClient httpClient() {
        PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();

        Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider> create()
                .register(CookieSpecs.DEFAULT, new DefaultCookieSpecProvider(publicSuffixMatcher))
                .register(CookieSpecs.STANDARD, new RFC6265CookieSpecProvider(publicSuffixMatcher)).build();

        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.DEFAULT).build();

        return HttpClients.custom()
                .setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:44.0) Gecko/20100101 Firefox/44.0")
                .setDefaultCookieSpecRegistry(r).setDefaultCookieStore(cookieStore())
                .setDefaultRequestConfig(globalConfig).build();
    }

    @Bean
    @Scope("singleton")
    public HttpComponentsClientHttpRequestFactory requestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    @Scope("prototype")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory());
        return restTemplate;
    }
}
