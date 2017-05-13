package me.test.first.spring.boot.jersey.resource.file

import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@javax.inject.Singleton
@Component
@Path("/file")
@Api(
        value = "/file",
        description = "文件",
        tags = ["file"]
)
@Slf4j
class FileResource {

    FileResource() {
        println "================= new ${this.class.name}() : @ " + new Date()
    }

    // 通过表单上传文件
/*
curl -v \
    -X POST \
    -F age=31 \
    -F hobbies=hobby1 \
    -F hobbies=hobby2 \
    -F file1=@first-spring-boot-jersey/src/main/resources/file1.txt \
    -F "file2=@first-spring-boot-jersey/src/main/resources/file2.txt;type=text/html" \
    "http://localhost:8080/api/file" \
    --trace-ascii /dev/stdout

{
    "age":31,
    "hobbies":["hobby1","hobby2"],
    "file1Metadata":{
        "type":"form-data",
        "parameters":{"name":"file1","filename":"file1.txt"},
        "fileName":"file1.txt","size":-1,"name":"file1"},
    "file1":"file1-abc",
    "file2Metadata":{"type":"form-data",
        "parameters":{"name":"file2","filename":"file2.txt"},
        "fileName":"file2.txt","size":-1,"name":"file2"},
    "file2":"file2-xyz"}
 */

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "Nice!",
                    response = FilePostReq)
    ])
    Response postMultipartFormData(
            @BeanParam
                    FilePostReq req
    ) {
        def data = req

        log.debug("FileResource#postMultipartFormData : file1 : " + req.file1)
        log.debug("FileResource#postMultipartFormData : file2 : " + req.file2)
        return Response.status(Response.Status.OK).entity(data).build()
    }

/*
curl -v \
    -X POST \
    -H "Content-type: application/octet-stream" \
    --data-binary @first-spring-boot-jersey/src/main/resources/file1.txt \
    "http://localhost:8080/api/file/bin" \
    --trace-ascii /dev/stdout

file1-abc
 */
    // FIXME: 如果该方法没有 @Path， 则swagger生成的文档只有该方法的，没有前一个的。swagger 不支持 content negotiation?
    // 单个文件上传——二进制流
    @Path("/bin")
    @POST
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "Nice!",
                    response = String)
    ])
    Response postBinary(
            String reqBodyAsString
    ) {
        log.debug("--------- FileResource#postBinary()  reqBodyAsString : " + reqBodyAsString)
        return Response.status(Response.Status.OK).entity(reqBodyAsString).build()
    }

}

