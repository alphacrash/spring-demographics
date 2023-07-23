package com.alphacrash.demographics.repository;

import com.alphacrash.demographics.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
}
