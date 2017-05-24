package me.test.first.spring.boot.feign.api

import groovy.transform.ToString
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 *
 */
@ApiModel
@ToString(includeNames = true)
class UserGetResp {

    InHeader inHeader
    InQuery inQuery
    InPath inPath

    @ToString(includeNames = true)
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

    @ToString(includeNames = true)
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

    @ToString(includeNames = true)
    static class InPath {

        @ApiModelProperty(
                value = "公司ID"
        )
        String companyId
    }

}
