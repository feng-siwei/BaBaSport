package com.feng.core.bean;

import java.sql.Date;

public class Test {
	private Integer id;
	private String name;
	private Date birthday;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Override
	public String toString() {
		return "Test [id=" + id + ", name=" + name + ", birthday=" + birthday + "]";
	}
	
}
