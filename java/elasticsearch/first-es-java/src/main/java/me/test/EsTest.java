package me.test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest.OpType;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class EsTest {

    private static final String index = "lizi";
    private static final String type = "item";

    @SuppressWarnings({ "resource" })
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        // 创建Es的Client对象

        Client client = null;
        try {
            Settings settings = ImmutableSettings.settingsBuilder()
                    .put("client.transport.ignore_cluster_name", true)
                    .put("client.transport.sniff", true)
                    .build();

            client = new TransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

            recreateIndex(client);
            doIndex(client);

            // 搜索全部
            searchAll(client); // 第一次是无法立即查出结果的，毕竟不是真正的实时系统
            Thread.sleep(1000 * 1);
            searchAll(client); // 一秒钟之后就可以查询到了

            // 关键词查询
            searchKeyWord(client);

            // 数值型范围过滤
            searchRange(client);

            // 排序
            searchOrdered(client);

            // 高亮
            searchHightlight(client);

        } catch (Exception e) {
            System.out.println("00000 " + e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    // 重建索引
    private static void recreateIndex(Client client) throws InterruptedException, ExecutionException {
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
    }

    // 索引要搜索的文档
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void doIndex(final Client client) {

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

    // 查询所有
    private static void searchAll(Client client) {

        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchAllQuery())
                .setExplain(true)
                .execute()
                .actionGet();

        System.out.println("searchAll : " + response);
    }

    // 关键词查询
    private static void searchKeyWord(Client client) {

        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchQuery("_all", "双白"))
                .execute()
                .actionGet();

        System.out.println("searchKeyWord : " + response);
    }

    // 数值型范围过滤
    private static void searchRange(Client client) {

        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.filteredQuery(
                        QueryBuilders.matchAllQuery(),
                        FilterBuilders.rangeFilter("price").gte(1200)))
                .execute()
                .actionGet();

        System.out.println("searchRange: " + response);
    }

    // 排序
    private static void searchOrdered(Client client) {

        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchAllQuery())
                .addSort(SortBuilders.fieldSort("sales_count").order(SortOrder.DESC))
                .addSort(SortBuilders.fieldSort("price"))
                .execute()
                .actionGet();

        System.out.println("searchOrdered: " + response);
    }

    // 高亮
    private static void searchHightlight(Client client) {

        SearchResponse response = client.prepareSearch(index)
                .setQuery(QueryBuilders.matchQuery("_all", "双白日"))
                .addHighlightedField("title")
                .addHighlightedField("origin")
                .execute()
                .actionGet();

        System.out.println("searchOrdered: " + response);
    }

}
