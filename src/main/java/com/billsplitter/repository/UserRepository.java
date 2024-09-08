package com.billsplitter.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billsplitter.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	List<User> findByMobileAndPassword(Long mobile, String password);

}
