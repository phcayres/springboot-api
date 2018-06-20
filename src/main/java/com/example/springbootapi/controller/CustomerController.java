package com.example.springbootapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.example.springbootapi.model.Customer;
import com.example.springbootapi.repository.CustomerRepository;

/**
 *
 * @author Paulo Henrique Cayres
 */
@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;


    @RequestMapping(value = "/api/v1/Customer", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }    

    @RequestMapping(value = "/api/v1/Customer/{customerId}", method = RequestMethod.GET)
    @ResponseBody
    public Customer getAllCustomers(@PathVariable Long customerId) {
        return customerRepository.findById(customerId);
    }    
    
    @RequestMapping(value = "/api/v1/Customer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        customer.setCreation(new java.util.Date()); // creation date
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.OK).body("sucess");
    }    

    @RequestMapping(value = "/api/v1/Customer/{customerId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {

        try{
            Customer temp = customerRepository.findById(customerId);
            temp.setName(customer.getName());
            temp.setEmail(customer.getEmail());
            temp.setAddress(customer.getAddress());
            customerRepository.save(temp);
            return ResponseEntity.status(HttpStatus.OK).body("sucess");
        }
        catch(Exception e){}
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'error': 'Customer not found.'}");
        
    }    

    @RequestMapping(value = "/api/v1/Customer/{customerId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {

        try{
            customerRepository.delete(customerRepository.findById(customerId));
            return ResponseEntity.status(HttpStatus.OK).body("sucess");
        }
        catch(Exception e){}
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'error': 'Customer not found.'}");
        
    }    
   
    
    @RequestMapping(value = "/api/v1/Customer/auth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> authCustomer(@RequestParam("email") String email, @RequestParam("password") String password) {
        
        if(customerRepository.findByEmailAndPassword(email, password).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'error': 'User and Password not found.'}");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body("sucess");
        }
        
    }
    
    
    
}
