package me.test.first.spring.boot.jersey.resource.user

import io.swagger.annotations.*
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@javax.inject.Singleton
@Component
@Path("/user")
@Api(
        value = "/user",
        description = "用户",
        tags = ["user"]
)
class UserResource {

    UserResource() {
        println "================= new ${this.class.name}() : @ " + new Date()
    }

/*
curl -v \
    -X POST \
    -H 'age: 31' \
    -H 'hobbies: hobby1' \
    -H 'hobbies: hobby2' \
    "http://localhost:8080/api/user/companyId1?name=zhao6&addrs=addr1&addrs=addr2"

{"age":31,"hobbies":["hobby1","hobby2"],"companyId":"companyId1","name":"zhao6","addrs":["addr1","addr2"]}
 */
    @Path("/{companyId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "Nice!",
                    response = UserPostReq)
    ])
    Response update(
            @BeanParam
            UserPostReq req
    ) {
        def data = req
        return Response.status(Response.Status.OK).entity(data).build()
    }


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
    Response list(

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

        // 注意：这里的是 Map 类型，而 @ApiResponse 中是 UserGetResp ，两者只要结构一直应当OK的。
        def data = [
            inHeader:[
                    age: age,
                    hobbies: hobbies,
            ],
            inQuery: [
                    name: name,
                    addrs:addrs
            ],
            inPath: [
                    companyId: companyId
            ]
        ]

        return Response.status(Response.Status.OK).entity(data).build()
    }



/*
curl -v \
    -X POST \
    -H "Accept: application/json" \
    -H "Content-Type: application/json" \
    -d '{"name":"zhang3","age": 23, "addr" : "addr1"}' \
    "http://localhost:8080/api/user/postJson"

{"inHeader":{"age":31,"hobbies":["hobby1","hobby2"]},"inQuery":{"name":"zhao6","addrs":["addr1","addr2"]},"inPath":{"companyId":"companyId1"}}
 */
    @Path("/postJson")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "查询JSON",
            notes = "你猜~~##")
    Object postJson(
        JsonReq1 r
    ) {
        def data = [
            clazz:r.getClass(),
            name: r.name,
            age: r.age,
                addr: r instanceof JsonReq2 ? ((JsonReq2)r).addr : "N/A"
        ]

        return Response.status(Response.Status.OK).entity(data).build()
    }

}

