package com.varshith.fin_circle.repository;

import com.varshith.fin_circle.entity.User;
import com.varshith.fin_circle.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
