package com.notification.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.notification.ex.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	public List<User> findByUserName(String userName);
	public User findByUserNameAndPassword(String userName, String password);
	public User findByEmail(String Email);
}
