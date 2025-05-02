package app.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.main.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUserName(String user_name);
	
	Optional<User> findByEmail(String email);

}
