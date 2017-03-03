package me.test.first.spring.boot.swagger.resource

import io.swagger.annotations.*
import me.test.first.spring.boot.swagger.model.Item
import me.test.first.spring.boot.swagger.model.TestGetResp
import me.test.first.spring.boot.swagger.model.TestPostJsonReq
import me.test.first.spring.boot.swagger.model.UniResp
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

//@SwaggerDefinition(
//        tags = [
//                @Tag(name="test",description = "本地测试")
//        ]
//)
@Api( // 用在类上，用于设置默认值
        tags = "test",
        consumes = MediaType.APPLICATION_ATOM_XML_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "https"
)
@RestController
@RequestMapping(
        value = "/test/{oid}",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
class TestResource  {

//    @Autowired
//    void setApi(Docket petApi){
//
//    }

//    @ApiOperation(
//            value = "测试提交表单1",
//            nickname = "testPostForm1",
//            notes = "测试提交表单1notes"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @RequestMapping(
//            value = "/testPostForm1",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    )
//    ResponseEntity<Void> testPostForm1(
//            //@RequestBody
//                //  @ModelAttribute("myForm")  // FIMXE NOT WORK
//                    MyFormData myForm // FIMXE NOT WORK
//    ){
//
//    }


    @ApiOperation(
            value = "测试GET请求",
            nickname = "testGet",
            notes = "测试GET请求notes",
            httpMethod = "GET"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果",
                    responseHeaders = [
                            @ResponseHeader(
                                    name = "userName",
                                    description = "testGet-resp-用户名",
                                    response = String
                            ),
                            @ResponseHeader(
                                    name = "userNames",
                                    description = "testGet-resp-用户名s",
                                    response = String,
                                    responseContainer = "List"
                            )
                    ]
            )
    ])
    @RequestMapping(
            value = "/testGet/{userId}",
            method = RequestMethod.GET
    )
    ResponseEntity<TestGetResp> testGet(


            // 测试 RestController 类级别的 @RequestMapping 的 path 上的变量
            @ApiParam(value = "testGet-req-组织ID")
            @PathVariable("oid")
                    String oid,


            // 测试 action 方法级别的 @RequestMapping 的 path 上的变量
            @ApiParam(value = "testGet-req-用户ID")
            @PathVariable("userId")
                    String userId,


            // 测试 request 上的单值 header
            @ApiParam("testGet-req-用户名")
            @RequestHeader("userName")
                    String userName,

            // 测试 request 上的多值 header
            @ApiParam(
                    value = "testGet-req-用户名s",
                    allowMultiple = true
            )
            @RequestHeader(
                    value = "userNames",
                    required = false
            )
                    List<String> userNames,

//            // FIXME: cookie 不起作用
//            // https://github.com/springfox/springfox/issues/783
//            @ApiParam("SESSION ID")
//            @CookieValue("SID")
//            String sid,

            // 测试 URL 上的单值 query 参数
            @ApiParam(
                    value = "testGet-req-当前页码",
                    allowableValues = "range[0,infinity]"
            )
            @RequestParam(name = "curPage", defaultValue = "0")
                    Integer curPage,

            // 测试 URL 上的多值 query 参数
            @ApiParam(
                    value = "testGet-req-地址列表",
                    allowMultiple = true
            )
            @RequestParam(name = "addresses")
                    List<String> addresses,

            // 测试 URL 上的日期类型的 query 参数
            @ApiParam(
                    value = "testGet-req-开始日期"
            )
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @RequestParam(name = "startDate", required = false)
                    Date startDate
    ) {

        // 测试 response 中 JSON 格式的 body
        TestGetResp resp = new TestGetResp(
                oid: oid,
                userId: userId,
                curPage: curPage,
                addresses: addresses,
                startDate: startDate,
                itemList:      [
                        new Item(title:"item-1",price:100),
                        new Item(title:"item-2",price:200)
                ]
        )


        MultiValueMap<String, String> respHeaders = new LinkedMultiValueMap<>()
        // 测试 response 中单值 header
        respHeaders.set("userName", userName)
        // 测试 response 中多值 header
        respHeaders.put("userNames", userNames)

        ResponseEntity<TestGetResp> respEntity = new ResponseEntity<>(resp, respHeaders, HttpStatus.OK)
        return respEntity
    }


    @ApiOperation(
            value = "测试提交表单",
            nickname = "testPostForm",
            notes = "测试提交表单notes"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @RequestMapping(
            value = "/testPostForm",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    ResponseEntity<String> testPostForm(

            @ApiParam(value = "用户名1", required = true)
            @ModelAttribute("name")
                    String name,

            @ApiParam(
                    value = "用户身高",
                    allowableValues = "range[1,infinity]",
                    required = false
            )
            @ModelAttribute("height")
                    Integer height,


            @ApiParam(value = "喜好", required = true)
            @RequestParam
                    String[] hobbies,

            // https://github.com/springfox/springfox/issues/680
            // 如果 @RequestMapping.consumes 是表单，且POST，@RequestParam 将会变成 "formData"
            @ApiParam(value = "生日", required = true)
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    Date birthday
    ) {
        String respData = "${name}-${height}-${Arrays.toString(hobbies)}-${birthday.time}"
        ResponseEntity<String> respEntity = new ResponseEntity<>(respData, HttpStatus.OK)
        return respEntity
    }


    @ApiOperation(
            value = "测试多文件上传",
            nickname = "testFileUpload",
            notes = "测试多文件上传notes"
    )
    @RequestMapping(
            value = "/testFileUpload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    String testFileUpload(
            @ApiParam(
                    value = "上传文件1",
                    required = true
            )
            @RequestPart("file1")
                    MultipartFile file1,

            @ApiParam(
                    value = "上传文件2",
                    required = false
            )
            @RequestPart("file2")
                    MultipartFile file2
    ) {
        return new String(file1.getBytes())+"-"+new String(file2.getBytes())
    }


    @ApiOperation(
            value = "测试上传字节流",
            nickname = "testPostBin",
            notes = "测试上传字节流notes"
    )
    @RequestMapping(
            value = "/testPostBin",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    String testPostBin(
            @ApiParam(
                    value = "上传文件1",
                    required = true
            )
            @RequestBody
                    byte[] byteArr
    ) {
        return new String(byteArr )
    }


    @ApiOperation(
            value = "测试提交JSON数据",
            nickname = "testPostJson",
            notes = "测试提交JSON数据notes"
    )
    @RequestMapping(
            value = "/testPostJson",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    TestPostJsonReq testPostJson(
            @ApiParam(
                    value = "测试数据",
                    required = true
            )
            @RequestBody
                    TestPostJsonReq testPostJsonReq
    ) {
        return testPostJsonReq
    }

    @ApiOperation(
            value = "测试通过Java泛型返回JSON",
            nickname = "testReturnGeneric",
            notes = "测试通过Java泛型返回JSON-notes"
    )
    @RequestMapping(
            value = "/testReturnGeneric",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    UniResp<List<Item>> testReturnGeneric(  ) {
        return new UniResp<Item>(
                code:"SUCCESS",
                msg:"hi~",
                raw:false,
                data:[
                        new Item( title:"i-1",   price: 111 ),
                        new Item( title:"i-2",   price: 222 )
                ]
        )
    }

}