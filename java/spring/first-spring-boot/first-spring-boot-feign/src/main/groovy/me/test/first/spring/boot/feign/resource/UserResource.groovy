package me.test.first.spring.boot.feign.resource

import feign.Feign
import feign.Logger
import feign.httpclient.ApacheHttpClient
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import feign.jaxrs.JAXRSContract
import feign.slf4j.Slf4jLogger
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.swagger.annotations.*
import me.test.first.spring.boot.feign.api.UserApi
import me.test.first.spring.boot.feign.api.UserGetResp
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Invocation
import javax.ws.rs.client.WebTarget
import javax.ws.rs.core.MediaType

// 所有注解，必须完完全全与 UserApi 中的一致方可运行

@Api(
        value = "/user",
        description = "用户",
        tags = ["user"]
)
@Path("/user")
@javax.inject.Singleton
@Component
@Slf4j
class UserResource implements UserApi {





/*
curl -v \
    -X GET \
    -H 'age: 31' \
    -H 'hobbies: hobby1' \
    -H 'hobbies: hobby2' \
    "http://localhost:8080/api/user/companyId1?name=zhao6&addrs=addr1&addrs=addr2"

{"inHeader":{"age":31,"hobbies":["hobby1","hobby2"]},"inQuery":{"name":"zhao6","addrs":["addr1","addr2"]},"inPath":{"companyId":"companyId1"}}
 */
    @Path("/{companyId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "查询用户列表",
            notes = "你猜~~")
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "Nice!",
                    response = UserGetResp)
    ])
    UserGetResp list(

            // HEADER 中单值
            @ApiParam(value = "年龄")
            @HeaderParam("age")
                    int age,

            // HEADER 中多值
            @ApiParam(value = "偏好")
            @HeaderParam("hobbies")
                    List<String> hobbies,


            @PathParam("companyId")
            String companyId,

            // Query 中单值
            @ApiParam(
                value = "姓名",
                allowableValues='zhang3,li4,wang5'
            )
            @DefaultValue("zhang3")
            @QueryParam("name")
                    String name,

            // Query 中多值
            @ApiParam(value = "地址")
            @QueryParam("addrs")
                   List< String> addrs

    ) {

        UserGetResp data = [
            inHeader:[
                    age: age,
                    hobbies: hobbies,
            ] as UserGetResp.InHeader,
            inQuery: [
                    name: name,
                    addrs:addrs
            ] as UserGetResp.InQuery,
            inPath: [
                    companyId: companyId
            ] as UserGetResp.InPath
        ] as UserGetResp

        return data
    }

    // curl -v http://localhost:8080/api/user/test1
    // {"inHeader":{"age":32,"hobbies":["hobby11,hobby12"]},"inQuery":{"name":"wang55","addrs":["addr11","addr12"]},"inPath":{"companyId":"111"}}
    @Path("/test1")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String test1(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080")
                .path("api/user/111")
                .queryParam("name","wang55")
                .queryParam("addrs","addr11")
                .queryParam("addrs","addr12")

        Invocation.Builder builder = target.request(MediaType.APPLICATION_JSON_TYPE)
                .header("age", "32")
                .header("hobbies", "hobby11")
                .header("hobbies", "hobby12")


        UserGetResp userGetResp =   builder .get(UserGetResp)
        log.debug("-----------"+userGetResp)

        String str = builder.get(String)
        log.debug("-----------"+str)
        return str
    }

    // curl -v http://localhost:8080/api/user/test2
    //
    @Path("/test2")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    String test2(){

        UserApi userApi = Feign.builder()
                .contract(new JAXRSContract())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())

                // 不能使用 UserResource 替代 UserApi, 因为前者还实现了 GroovyObject 接口
                .target(UserApi.class, "http://localhost:8080/api");

        UserGetResp userGetResp =  userApi.list(
            33,
            ["hobby21","hobby22"],
            "comKingsilk",
            "aLiang",
            ["addr21","addr22"]
        )

        return JsonOutput.toJson(userGetResp)
    }
}

