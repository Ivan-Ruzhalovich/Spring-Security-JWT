package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.entity.Product;
import com.example.springsecurityjwt.models.ProductRequest;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.repository.ProductRepository;
import com.example.springsecurityjwt.services.adminService.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@AllArgsConstructor
@RestController
public class AdminAndUsersController {

    private final ProductRepository productRepository;
    private final AdminServiceImpl adminService;

    @GetMapping("/public/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/admin/newproduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.getName());
        return new ResponseEntity<>(productRepository.save(product),HttpStatus.OK);
    }
    @GetMapping("/user/test")
    private ResponseEntity<Object> userTest(){
        return new ResponseEntity<>("Пользователи могут здесь находиться",HttpStatus.OK);
    }

    @GetMapping("/useradmin/test")
    private ResponseEntity<Object> useradminTest(){
        return new ResponseEntity<>("Пользователи и админы могут здесь находиться",HttpStatus.OK);
    }

    @GetMapping("/admin/test")
    private ResponseEntity<Object> adminTest(){
        return new ResponseEntity<>("Только админы могут здесь находиться",HttpStatus.OK);
    }

    @PostMapping("/admin/lock")
    private ResponseEntity<Object> adminLock(@RequestBody RegistrationAndSingInRequestModel registrationAndSingInRequestModel){
        return new ResponseEntity<>(adminService.lock(registrationAndSingInRequestModel),HttpStatus.OK);
    }

    @PostMapping("/admin/unlock")
    private ResponseEntity<Object> adminUnlock(@RequestBody RegistrationAndSingInRequestModel registrationAndSingInRequestModel){
        return new ResponseEntity<>(adminService.unlock(registrationAndSingInRequestModel),HttpStatus.OK);
    }
}
