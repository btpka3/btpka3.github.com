package me.test.first.spring.boot.jersey.resource.file

import groovy.transform.ToString
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import org.glassfish.jersey.media.multipart.FormDataContentDisposition
import org.glassfish.jersey.media.multipart.FormDataParam

/**
 *
 */
@ToString(includeNames = true)
@ApiModel
class FilePostReq {

    // FORM 中单值
    @ApiParam(value = "年龄")
    @ApiModelProperty(value = "年龄-model")
    @FormDataParam("age")
    int age

    // FORM 中多值
    @ApiParam(value = "偏好")
    @ApiModelProperty(value = "偏好-model")
    @FormDataParam("hobbies")
    List<String> hobbies

//    @ApiParam(value = "文件1元信息")
//    @ApiModelProperty(value = "文件1元信息-model")
    @FormDataParam("file1")
    FormDataContentDisposition file1Metadata

    @ApiParam(value = "文件1内容")
    @ApiModelProperty(value = "文件1内容-model")
    @FormDataParam("file1")
    String file1

    @ApiParam(value = "文件2元信息")
    @ApiModelProperty(value = "文件2元信息-model")
    @FormDataParam("file2")
    FormDataContentDisposition file2Metadata

    @ApiParam(value = "文件2内容")
    @ApiModelProperty(value = "文件2内容-model")
    @FormDataParam("file2")
    String file2
}
