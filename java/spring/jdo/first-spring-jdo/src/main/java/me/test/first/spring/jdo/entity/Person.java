package me.test.first.spring.jdo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.FetchGroup;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
@FetchGroup(name="imgData", members={@Persistent(name="imgData")})
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	@Column(name = "ID", jdbcType = "BIGINT")
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private Long id = null;

	@Persistent
	@Column(name = "NAME", jdbcType = "VARCHAR")
	private String name = null;

	@Persistent
	@Column(name = "AGE", jdbcType = "INTEGER")
	private Integer age = null;

	@Persistent
	@Column(name = "MALE", jdbcType = "BOOLEAN")
	private Boolean male = false;

	@Persistent
	@Column(name = "BIRTHDAY", jdbcType = "DATE")
	private Date birthday = null;

	// NOTICE: JDO not support Inputream or Blob as field
	// http://db.apache.org/jdo/field_types.html
	// http://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html
	@Column(name = "IMG_DATA", jdbcType = "VARBINARY")
	private byte[] imgData = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Boolean getMale() {
		return male;
	}

	public void setMale(Boolean male) {
		this.male = male;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public byte[] getImgData() {
		return imgData;
	}

	public void setImgData(byte[] imgData) {
		this.imgData = imgData;
	}

}
