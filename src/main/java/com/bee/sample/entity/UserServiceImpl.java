package com.bee.sample.entity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional   //可以作用于类和方法
public class UserServiceImpl implements UserService{
	@Override
	public User getUserById(String id) {
	//从此处开启事务
		User user = new User();
		user.setId("11");
		user.setName("service注解");
		return user;
	//自动提交事务
	}

	@Override
	public void updateUser(String id, String name) {
		//从此处开启事务
		//自动提交事务
	}

	
}
