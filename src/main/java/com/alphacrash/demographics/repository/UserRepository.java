package com.alphacrash.demographics.repository;

import com.alphacrash.demographics.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
