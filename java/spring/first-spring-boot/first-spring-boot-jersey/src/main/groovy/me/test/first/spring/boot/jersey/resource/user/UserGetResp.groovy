package me.test.first.spring.boot.jersey.resource.user

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * Created by zll on 11/05/2017.
 */
@ApiModel
class UserGetResp {

    InHeader inHeader
    InQuery inQuery
    InPath inPath

    static class InHeader {
        @ApiModelProperty(
                value = "年龄"
        )
        int age

        @ApiModelProperty(
                value = "喜好"
        )
        List<String> hobbies
    }

    static class InQuery {

        @ApiModelProperty(
                value = "姓名"
        )
        String name

        @ApiModelProperty(
                value = "地址"
        )
        List<String> addrs
    }

    static class InPath {

        @ApiModelProperty(
                value = "公司ID"
        )
        String companyId
    }

}
