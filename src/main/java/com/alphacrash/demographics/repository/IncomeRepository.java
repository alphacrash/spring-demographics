package com.alphacrash.demographics.repository;

import com.alphacrash.demographics.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
