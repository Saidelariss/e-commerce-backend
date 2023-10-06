package ecom.backendecommerce.web;

import ecom.backendecommerce.entities.Product;
import ecom.backendecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin("*")

public class CurrentProductController {
    @Autowired
    private ProductRepository productRepository2;

    @GetMapping("/products")
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "7") int size,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "")  String keyword){
        if(keyword.isEmpty()){
            Pageable pageable =  PageRequest.of(page,size);
            Page<Product> pageResponse = productRepository2.findAll(pageable);
            return pageResponse;
        }
        else {
            List<Product> productsKeyword = productRepository2.findByNameContainsIgnoreCase(keyword);
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> pageDeProduits = new PageImpl<>(productsKeyword, pageable, productsKeyword.size());
            return pageDeProduits;

        }

    }

    @PostMapping("/product")
    public void addProduct(@RequestBody Product p){
        productRepository2.save(p);
    }

//    @GetMapping("/products")
//    public Page<Product> getProductByKeyword(@RequestParam String keyword,@RequestParam int size,
//                                             @RequestParam int page) {
//    }

}
