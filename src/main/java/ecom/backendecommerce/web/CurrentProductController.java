package ecom.backendecommerce.web;

import ecom.backendecommerce.entities.PageProduct;
import ecom.backendecommerce.entities.Product;
import ecom.backendecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class CurrentProductController {
    @Autowired
    private ProductRepository productRepository2;

    @GetMapping("/products")
    public PageProduct getAllProducts(@RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "")  String keyword){
        if(keyword.isEmpty()){
            Pageable pageable =  PageRequest.of(page,size);
            Page<Product> pageResponse = productRepository2.findAll(pageable);
            PageProduct pageProduct = PageProduct.map(pageResponse);
            return pageProduct;

        }
        else {
            List<Product> productsKeyword = productRepository2.findByNameContainsIgnoreCase(keyword);
//            Pageable pageable = PageRequest.of(page, size);
//            Page<Product> pageDeProduits = new PageImpl<>(productsKeyword, pageable, productsKeyword.size());
            if(page*size<productsKeyword.size()){
                List<Product> products = productsKeyword.subList(page * size, Math.min(size + page * size, productsKeyword.size()));
                PageProduct pageProduct = new PageProduct(products,page,size,null);
                if(productsKeyword.size()%size==0) pageProduct.setTotalPages(productsKeyword.size()/size);
                else pageProduct.setTotalPages(productsKeyword.size()/size+1);
                return  pageProduct;

            }
            else return new PageProduct();


        }

    }

    @PostMapping("/product")
    public boolean addProduct(@RequestBody Product p){
        productRepository2.save(p);
        return true;
    }

    @DeleteMapping("/product/{id}")
    public boolean deleteProduct(@PathVariable Integer id) throws Exception {
        Optional<Product> product = productRepository2.findById(id);
        if(product.isPresent()) {
            productRepository2.delete(product.get());
            return true;
        }
        else if(product.isEmpty()) throw new Exception("Ce produit n'existe pas");
        return false;
    }

    @PatchMapping("/product/{id}")
    public boolean setPromotion(@PathVariable Integer id) throws Exception {
        Optional<Product> product = productRepository2.findById(id);
        if(product.isPresent()) {
            product.get().setPromotion(!product.get().getPromotion());
            productRepository2.save(product.get());
        }
        else if(product.isEmpty()) throw new Exception("Ce produit n'existe pas");
        return false;
    }

    @PostMapping("/product")
    public Product updateProduct(@RequestBody Product pr) throws Exception {
        Optional<Product> p = productRepository2.findById(pr.getId());
        if(p.isPresent()){
            Product product = p.get();
            product.setName(pr.getName());
            product.setPrice(pr.getPrice());
            product.setPromotion(pr.getPromotion());
            Product savedProduct = productRepository2.save(product);
            return savedProduct;
        }
        else throw new Exception("le produit n'existe pas");
    }


//    @GetMapping("/products")
//    public Page<Product> getProductByKeyword(@RequestParam String keyword,@RequestParam int size,
//                                             @RequestParam int page) {
//    }

}

