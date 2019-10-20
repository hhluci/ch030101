package com.bee.sample.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class WorkInfoFormGroup {

   public interface Update{}
   public interface Add{}
   
   @Null(groups= {Add.class})
	@NotNull(groups= {Update.class})
	private Long id;
	@Size(min=3,max=20)
	private String name;
	@Email
	private String email;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
