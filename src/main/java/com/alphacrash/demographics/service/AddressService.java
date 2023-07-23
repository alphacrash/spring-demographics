package com.alphacrash.demographics.service;

import com.alphacrash.demographics.entity.Address;
import com.alphacrash.demographics.payload.AddressDTO;

import java.util.List;

public interface AddressService {

    List<Address> getAllAddressesByConsumerId(Long consumerId);

    Address getAddressByType(Long consumerId, String addressType);

    Address createAddress(AddressDTO addressDTO, Long consumerId);

    Address updateAddress(AddressDTO addressDTO, Long consumerId);

    void deleteAddress(Long consumerId, String addressType);
}