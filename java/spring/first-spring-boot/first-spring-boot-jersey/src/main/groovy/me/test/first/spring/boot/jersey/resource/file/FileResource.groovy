package me.test.first.spring.boot.jersey.resource.file

import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.apache.commons.io.IOUtils
import org.apache.commons.io.input.BoundedInputStream
import org.glassfish.jersey.media.multipart.FormDataParam
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

// multipart/form-data
// application/octet-stream
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

/*
 上传文件限制大小：
  @FormDataParam("file") InputStream inputStream
  然后用 Commons-io, BoundedInputStream 包装限定一下。
  注意：
    BoundedInputStream 在超过最大大小时，不会抛出异常，而是直接关闭流。
    因此，可以 配合 CountingInputStream，或通过别的方式 知道已读取流的字节数。
    而上传大小应当至少多一个字节，以便判断到底是超了，还是刚刚好。

Spring配置文件配置项: spring.http.multipart.max-file-size
只对 Spring 的 DispatcherServlet 有用。
    MultipartProperties
    -> MultipartAutoConfiguration
    -> MultipartConfigElement
    -> DispatcherServletRegistrationConfiguration
*/

    private void limitSize(
            @FormDataParam("file")
                    InputStream inputStream
    ) {
        long maxSize = 1024 * 1024 * 10;
        File tmpFile = new File("/tmp/randomFile");

        long copedSize = IOUtils.copy(
                new BoundedInputStream(inputStream, maxSize + 1),
                new FileOutputStream(tmpFile)
        )
        if (copedSize > maxSize) {
            tmpFile.delete();
            throw new RuntimeException("exceed max size: " + maxSize);
        }
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

