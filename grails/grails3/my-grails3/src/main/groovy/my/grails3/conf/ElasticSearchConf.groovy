package my.grails3.conf

import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import redis.clients.jedis.JedisShardInfo
import org.elasticsearch.node.NodeBuilder

/**
 * 参考:
 *  https://www.elastic.co/guide/index.html
 *  https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/client.html
 *  https://github.com/spring-projects/spring-data-elasticsearch
 *
 */
@Configuration
class ElasticSearchConf {

    // https://github.com/grails/grails-core/issues/9806
    @Bean
    public SimpleElasticsearchMappingContext esMappingContext() {

        return new SimpleElasticsearchMappingContext();
    }


    @Bean
    String testConf(
            @Value('${a.b.c}')
                    Object ymlConf,
            @Value('${x.y.z}')
                    Object groovyConf) {
        String s = "========= testConf: ymlConf=$ymlConf, groovyConf=$groovyConf";
        println s
        return s
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(Client client) {
        return new ElasticsearchTemplate(client);
    }

//    @Bean
//    public TransportClient esClient1(@Value('${grails.logging.jul.usebridge}' )boolean a,
//            @Value('${qh.es.clusterName}')
//                    String clusterName,
//            @Value('${qh.es.clusterNodes}')
//                    String clusterNodes) {
//        println "=======esClient1 : $clusterName, $clusterNodes,  $a"
//
//        // http://stackoverflow.com/questions/15398861/elasticsearch-nodebuilder-vs-tranportclient
//        // NodeBuilder.nodeBuilder()
//        //        .local(true).node().client()
//
//        // https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/transport-client.html
//        Settings settings = Settings.settingsBuilder()
//                .put("client.transport.ignore_cluster_name", true)
//                .put("client.transport.nodes_sampler_interval", "5s")
//                .put("client.transport.ping_timeout", "5s")
//                .put("client.transport.sniff", true)
//                .put("cluster.name", clusterName)
//                .build();
//        TransportClient client = TransportClient.builder()
//                .settings(settings)
//                .build();
//
//        //client.addTransportAddress(...)
//        return client;
//    }

//    // 注意:这里返回的是FactoryBean,但是实际引用的时候,会变成TransportClient
//    @Bean
//    public TransportClientFactoryBean esClient(
//            @Value('${qh.es.clusterName}')
//                    String clusterName,
//            @Value('${qh.es.clusterNodes}')
//                    String clusterNodes) {
//        println "=======esClient : $clusterName, $clusterNodes"
//
//        TransportClientFactoryBean factoryBean = new TransportClientFactoryBean();
//        factoryBean.setClientIgnoreClusterName(true);
//        factoryBean.setClientNodesSamplerInterval("5s");
//        factoryBean.setClientPingTimeout("5s");
//        factoryBean.setClientTransportSniff(true);
//        factoryBean.setClusterName(clusterName);
//        factoryBean.setClusterNodes(clusterNodes);
//        return factoryBean;
//    }

}
