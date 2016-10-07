package my.grails3

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse
import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.Client
import org.elasticsearch.index.query.QueryBuilders
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.elasticsearch.action.index.IndexRequest.OpType;

class HelloController {

    def mongo

    def myCalcService

    def userDetailsService

    def myTestSrv


    @Value('${a.b.c}')
    Object ymlConf

    @Value('${x.y.z}')
    Object groovyConf

//    Object testConf


    Client elasticsearchClient

    // config 测试
    def conf() {
        render "==== ${new Date()}, ymlConf=${ymlConf}, groovyConf=${groovyConf}"
    }

    // gsp 测试
    def index() {
        println "=================== index : " + myTestSrv
        render(view: "index", model: [
                a: "aaa" + grailsApplication.mainContext.getBean(MyUserDetails),
                b: "bbb" + grailsApplication.mainContext.getBean(SecurityExpressionHandler)?.defaultRolePrefix + "---",
                c: "ccc" + userDetailsService?.class
        ])
    }

    // 服务调用测试
    def add() {
        render "myCalcService : " + myCalcService.add(1, 2) + ", " + System.currentTimeMillis()
    }

    // 测试插入mongodb
    def insert() {

        10.times {
            def u = new U(username: "zhang_" + it, age: 10 + it)
            u.save(flush: true)
        }
        render "insert OK " + System.currentTimeMillis()
    }

    // 测试查询mongodb
    def list() {

        def list = U.createCriteria().list {
            ge("age", 15)
        }
        render(list as JSON)
    }

    // 测试删除mongodb
    def clear() {
        U.list()*.delete()
        render("all deleted. ")
    }

    // 测试使用mongodb底层API
    def testMongo() {
        def c = mongo.getDB("zll").user.find()
        def rec
        if (c.hasNext()) {
            rec = c.next()
        }
        render "aaaaaaaaaa " + System.currentTimeMillis() + " === " + rec
    }

    // ----------------------------------------------------- spring security
    def setupUsers() {
        MyUser.list()*.delete()
        new MyUser(username: 'admin', password: 'admin', authorities: ['ADMIN']).save()
        new MyUser(username: 'user', password: 'user', authorities: ['USER']).save()
        render "user setup Ok."
    }

    // 测试登录后访问
    @Secured(["isAuthenticated()"])
    def sec() {
        render "can only view after logined"
    }

    // 测试登录后且有特定权限才能访问
    @Secured(["isAuthenticated() && hasAnyRole('ADMIN')"])
//    @Authorities("ADMIN") // 适用于只判断一个权限
    def admin() {
        render "admin only page"
    }


    String index = "zll";
    String type = "item1";

    def es() {
        recreateIndex(elasticsearchClient);
        doIndex(elasticsearchClient);
        String result = searchKeyWord(elasticsearchClient);
//        String result = "rrr"
        render(result)
    }

    // 重建索引
    private void recreateIndex(Client client) throws InterruptedException {
        // 检查索引是否存在
        if (client.admin().indices().prepareExists(index).execute().actionGet().isExists()) {
            // 删除索引 FIXME 超时判定？结果判定？
            DeleteIndexResponse deleteIndexResponse = client.admin()
                    .indices()
                    .delete(new DeleteIndexRequest(index))
                    .actionGet();
            System.out.println("delete index : " + deleteIndexResponse);
        }

        // 创建索引
        CreateIndexResponse createIndexResponse = client.admin()
                .indices()
                .prepareCreate(index)
                .execute()
                .actionGet();
        System.out.println("create index : " + createIndexResponse);


        String mappingJsonStr = "" +
                "{" +
                "    \"properties\": {" +
                "        \"_all\" : {" +
                "            \"type\":\"string\"," +
                "            \"index\": \"analyzed\"," +
                "            \"analyzer\": \"standard\"" +
                "        }," +
                "        \"title\" : {" +
                "            \"type\":\"string\"," +
                "            \"index\": \"analyzed\"," +
                "            \"analyzer\": \"standard\"" +
                "        }," +
                "        \"origin\" : {" +
                "            \"type\":\"string\"," +
                "            \"index\": \"analyzed\"," +
                "            \"analyzer\": \"standard\"" +
                "        }," +
                "        \"description\" : {" +
                "            \"type\":\"string\"," +
                "            \"index\": \"analyzed\"," +
                "            \"analyzer\": \"standard\"" +
                "        }," +
                "        \"sales_count\" : {" +
                "            \"type\":\"long\"" +
                "        }," +
                "        \"price\" : {" +
                "            \"type\":\"long\"" +
                "        }" +
                "    }" +
                "}"
        PutMappingResponse putMappingResponse = esClient.admin()
                .indices()
                .preparePutMapping(index)
                .setType(index)
                .setSource(mappingJsonStr)
                .execute()
                .actionGet();
        System.out.println("create mapping : " + putMappingResponse);
    }


    private void doIndex(final Client client) {

        Map s11 = new LinkedHashMap();
        s11.put("title", "MISSHA谜尚维他保湿裸妆金色BB霜50ml");
        s11.put("origin", "韩国");
        s11.put("description", "这不是面膜，快来买啊，亲");
        s11.put("sales_count", 748);
        s11.put("price", 9680);

        Map s12 = new LinkedHashMap();
        s12.put("title", "MISSHA谜尚双头眼影棒+眼线笔");
        s12.put("origin", "韩国");
        s12.put("description", "亲耐的，再不买就卖光光喽");
        s12.put("sales_count", 666);
        s12.put("price", 1080);

        Map s21 = new LinkedHashMap();
        s21.put("title", "谜尚Missha指甲油白色盖子");
        s21.put("origin", "日本");
        s21.put("description", "亲耐的，再不买就卖光光喽");
        s21.put("sales_count", 666);
        s21.put("price", 990);

        Map s22 = new LinkedHashMap();
        s22.put("title", "SKINFOOD思亲肤番茄西红柿面霜40g");
        s22.put("origin", "美国");
        s22.put("description", "跳楼价，最后一天啦");
        s22.put("sales_count", 777);
        s22.put("price", 8800);

        // 批量索引文件

        BulkResponse bulkResponse = client.prepareBulk()
                .add(client.prepareIndex(index, type).setId("11").setSource(s11).setOpType(OpType.INDEX).request())
                .add(client.prepareIndex(index, type).setId("12").setSource(s12).setOpType(OpType.INDEX).request())
                .add(client.prepareIndex(index, type).setId("21").setSource(s21).setOpType(OpType.INDEX).request())
                .add(client.prepareIndex(index, type).setId("22").setSource(s22).setOpType(OpType.INDEX).request())
                .execute()
                .actionGet();

        if (bulkResponse.hasFailures()) {
            System.err.println("index docs [ERROR] : " + bulkResponse.buildFailureMessage());
        } else {
            System.out.println("index docs : " + bulkResponse);
        }

    }

    // 关键词查询
    private String searchKeyWord(Client client) {

        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchQuery("_all", "双白"))
                .execute()
                .actionGet();

        System.out.println("searchKeyWord : " + response);
        return response.toString();
    }
}
