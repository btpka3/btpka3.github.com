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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.elasticsearch.action.index.IndexRequest.OpType;


@FeignClient("newsletter")
class NewsletterService {

    def mongo

    def myCalcService

    def userDetailsService

    def myTestSrv

}
