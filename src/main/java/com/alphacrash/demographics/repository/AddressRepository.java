package com.alphacrash.demographics.repository;

import com.alphacrash.demographics.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
