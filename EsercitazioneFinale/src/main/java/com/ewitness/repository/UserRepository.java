package com.ewitness.repository;


import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ewitness.domain.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//	User findFirstByOrderByPostedOnDesc();
	
	User findByEmail(String email);

	List<User> findAll();

//	List<User> findAllByAuthorIdOrderByPostedOnDesc(Long id);

}
