package com.ach.stock.repository;

import com.ach.stock.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, String> {

    boolean existsByUserId(String userId);

    Optional<Users> findByUserId(String userId);

}
