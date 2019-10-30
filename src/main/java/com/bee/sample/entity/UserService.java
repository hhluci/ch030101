package com.bee.sample.entity;

public interface UserService {

	User getUserById(String id);
	void updateUser(String id, String name);
}
