package com.example.springbootapi.repository;

import org.springframework.data.jpa.repository.*;
import com.example.springbootapi.model.Customer;
import java.util.List;

/**
 *
 * @author Paulo Henrique cayres
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    List<Customer> findByEmailAndPassword(String email, String password);
    
    Customer findById(Long id);

}

