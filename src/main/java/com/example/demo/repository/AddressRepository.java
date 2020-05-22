package com.example.demo.repository;

import com.example.demo.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository {
    Address getAddressFromAddressString(String addressStr);
}