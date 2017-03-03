package me.test.first.spring.boot.swagger.model

import io.swagger.annotations.ApiModelProperty

import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class UniResp<T> {


    @XmlElement(nillable = false)
    @ApiModelProperty(value = "响应的状态码，仅当 'SUCCESS' 时代表成功")
    String code

    @ApiModelProperty(value = "响应的消息，可能是对用户友好的消息，也可能是错误堆栈消息")
    String msg

    @ApiModelProperty(value = "用以指明响应的消息是否对用户有效，true-不友好")
    Boolean raw

    @ApiModelProperty(value = "业务数据")
    T data


}
