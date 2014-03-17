
package me.test.service;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long hospitalId;
    private String name;
    private String remark;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getHospitalId() {

        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {

        this.hospitalId = hospitalId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getRemark() {

        return remark;
    }

    public void setRemark(String remark) {

        this.remark = remark;
    }

}
