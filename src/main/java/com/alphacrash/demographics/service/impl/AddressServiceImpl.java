package com.alphacrash.demographics.service.impl;

import com.alphacrash.demographics.entity.Address;
import com.alphacrash.demographics.entity.Consumer;
import com.alphacrash.demographics.exception.CannotCreateException;
import com.alphacrash.demographics.exception.ResourceNotFoundException;
import com.alphacrash.demographics.payload.AddressDTO;
import com.alphacrash.demographics.repository.AddressRepository;
import com.alphacrash.demographics.repository.ConsumerRepository;
import com.alphacrash.demographics.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ConsumerRepository consumerRepository;

    @Override
    public List<Address> getAllAddressesByConsumerId(Long consumerId) {
        return addressRepository.findByConsumerId(consumerId);
    }

    @Override
    public Address getAddressByType(Long consumerId, String addressType) {
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer", "id", consumerId));
        Long addressId = null;
        if (addressType.equals("Mail")) {
            if (Objects.isNull(consumer.getMailAddressId())) {
                throw new ResourceNotFoundException("Consumer", "id", consumerId);
            }
            addressId = consumer.getMailAddressId();
        }
        if (addressType.equals("Home")) {
            if (Objects.isNull(consumer.getPrimaryAddressId())) {
                throw new ResourceNotFoundException("Consumer", "id", consumerId);
            }
            addressId = consumer.getPrimaryAddressId();
        }
        Long finalAddressId = addressId;
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", finalAddressId));
    }

    @Override
    public Address createAddress(AddressDTO addressDTO, Long consumerId) {
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer", "id", consumerId));
        if (addressDTO.getAddressType().equals("Mail") && Objects.nonNull(consumer.getMailAddressId())) {
            throw new CannotCreateException("Consumer already has a mail address");
        }
        if (addressDTO.getAddressType().equals("Home") && Objects.nonNull(consumer.getPrimaryAddressId())) {
            throw new CannotCreateException("Consumer already has a home address");
        }
        if (addressDTO.getAddressType().equals("Mail") || addressDTO.getAddressType().equals("Home")) {
            Address address = mapAddressDTOToAddress(addressDTO);
            address.setConsumerId(consumer.getId());
            Address persistedAddress = addressRepository.save(address);
            if (addressDTO.getAddressType().equals("Mail")) {
                consumer.setMailAddressId(persistedAddress.getId());
            } else if (addressDTO.getAddressType().equals("Home")) {
                consumer.setPrimaryAddressId(persistedAddress.getId());
            }
            consumerRepository.save(consumer);
            return persistedAddress;
        } else {
            throw new CannotCreateException("Address type must be Mail or Home");
        }
    }

    private Address mapAddressDTOToAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setStreet(addressDTO.getStreet());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setAddressType(addressDTO.getAddressType());
        return address;
    }

    @Override
    public Address updateAddress(AddressDTO address, Long consumerId) {
        Consumer consumer = consumerRepository.findById(consumerId)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer", "id", consumerId));
        Long addressId = null;
        if (address.getAddressType().equals("Mail")) {
            if (Objects.isNull(consumer.getMailAddressId())) {
                throw new CannotCreateException("Consumer has no mail address");
            }
            addressId = consumer.getMailAddressId();
        }
        if (address.getAddressType().equals("Home")) {
            if (Objects.isNull(consumer.getPrimaryAddressId())) {
                throw new CannotCreateException("Consumer has no home address");
            }
            addressId = consumer.getPrimaryAddressId();
        }
        Long finalAddressId = addressId;
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", finalAddressId));
        existingAddress.setStreet(address.getStreet());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setZipCode(address.getZipCode());
        existingAddress.setAddressType(address.getAddressType());
        return addressRepository.save(existingAddress);
    }

    @Override
    public void deleteAddress(Long consumerId, String addressType) {
        if (addressType.equals("Mail") || addressType.equals("Home")) {
            Consumer consumer = consumerRepository.findById(consumerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Consumer", "id", consumerId));
            Long addressId = null;
            if (addressType.equals("Mail")) {
                addressId = consumer.getMailAddressId();
                consumer.setMailAddressId(null);
            } else if (addressType.equals("Home")) {
                addressId = consumer.getPrimaryAddressId();
                consumer.setPrimaryAddressId(null);
            }
            if (Objects.isNull(addressId)) {
                throw new ResourceNotFoundException("Address", "addressType", addressType);
            }
            consumerRepository.save(consumer);
            addressRepository.deleteById(addressId);
        } else {
            throw new ResourceNotFoundException("Address", "addressType", addressType);
        }
    }
}