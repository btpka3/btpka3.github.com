


```text
ElasticsearchProperties
ElasticsearchAutoConfiguration
    @Bean org.elasticsearch.client.Client elasticsearchClient
ElasticsearchDataAutoConfiguration
    @Bean ElasticsearchTemplate elasticsearchTemplate
    @Bean ElasticsearchConverter elasticsearchConverter
    @Bean SimpleElasticsearchMappingContext mappingContext
    
@Query("{"bool" : {"must" : {"field" : {"name" : "?0"}}}}")
    该注解可以使用在 Repo 接口的方法上。
```