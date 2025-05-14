package com.ach.stock.repository;

import com.ach.stock.dto.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Users, String> {

        Optional<Users> findByUserId(String userId);

}
