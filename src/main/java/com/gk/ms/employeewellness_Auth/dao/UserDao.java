package com.gk.ms.employeewellness_Auth.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gk.ms.employeewellness_Auth.entity.User;


@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	User findByUsername(String username);
}
