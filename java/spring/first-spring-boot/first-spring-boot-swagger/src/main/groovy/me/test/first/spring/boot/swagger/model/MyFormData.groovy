package me.test.first.spring.boot.swagger.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

//@JsonIgnoreProperties(["metaClass"])
//@CompileStatic

@ApiModel
class MyFormData extends AbstractForm {

    @ApiModelProperty(
            value = "MyFormData-名称"
    )
    private String name

    @ApiModelProperty(
            value = "MyFormData-高度",
            allowableValues = "range[1,infinity]",
            required = false
    )
    private Integer height

    @ApiModelProperty(
            value = "MyFormData-生日"
    )
    private Date birthday

    @ApiModelProperty(
            value = "MyFormData-爱好s"
    )
    private List<String> hobbies

    String getName() {
        return this.map.get("name")
    }

    void setName(String name) {
        this.map.put("name", name)
    }

    Integer getHeight() {
        return this.map.get("height")
    }

    void setHeight(Integer height) {
        this.map.put("height", height)
    }

    Date getBirthday() {
        return this.map.get("birthday")
    }

    void setBirthday(Date birthday) {
        this.map.put("birthday", birthday)
    }

    List<String> getHobbies() {
        return this.map.get("hobbies")
    }

    void setHobbies(List<String> hobbies) {
        this.map.put("hobbies", hobbies)
    }
}
