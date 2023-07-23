package com.alphacrash.demographics.repository;

import com.alphacrash.demographics.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByConsumerId(Long consumerId);
}
