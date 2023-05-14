package ecom.backendecommerce.web;

import ecom.backendecommerce.dto.AuthRequest;
import ecom.backendecommerce.dto.AuthResponse;
import ecom.backendecommerce.entities.Product;
import ecom.backendecommerce.repositories.ProductRepository;
import ecom.backendecommerce.security.UserInfoDetailsService;
import ecom.backendecommerce.security.UserInfoUserDetails;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")

public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserInfoDetailsService userInfoDetailsService;



    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/products/all")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Integer id){
        Optional<Product> product = productRepository.findById(id);
        if(product==null){
            throw new RuntimeException("Product with id "+id+ " not found");
        }
        return product.get();
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticationUser(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));

        if(authentication.isAuthenticated()){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            UserInfoUserDetails user = (UserInfoUserDetails) userInfoDetailsService.loadUserByUsername(authRequest.getUsername());
           // return "User is authenticated";

            AuthResponse authResponse = new AuthResponse(user.getUsername(),user.getPassword(),null);
            List<String> roles = new ArrayList<>();
            for(GrantedAuthority authority : user.getAuthorities()){
                String role= authority.toString();
                roles.add(role);

            }
            authResponse.setRoles(roles);


            //return new AuthRequest(authRequest.getUsername(),authRequest.getPassword());
            return authResponse;
        }
        else {
            System.out.println("Bad Credentials");
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}
