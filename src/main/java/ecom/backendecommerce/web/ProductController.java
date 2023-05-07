package ecom.backendecommerce.web;

import ecom.backendecommerce.entities.Product;
import ecom.backendecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController

public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/products/all")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

}
