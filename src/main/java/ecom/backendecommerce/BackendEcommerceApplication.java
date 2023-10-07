package ecom.backendecommerce;

import ecom.backendecommerce.entities.Product;
import ecom.backendecommerce.entities.UserInfo;
import ecom.backendecommerce.repositories.ProductRepository;
import ecom.backendecommerce.repositories.UserInfoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;




@SpringBootApplication
public class BackendEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendEcommerceApplication.class, args);
	}

//	@Bean
	public CommandLineRunner commandLineRunner(ProductRepository productRepository){
		return args -> {
			List<Product> products = List.of(new Product(null,"Computer",1400.0,true),
											 new Product(null,"Printer",3000.0,false),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
					                   	     new Product(null,"Computer",1800.0,true),
					                   	     new Product(null,"Smart Phone",1800.0,true),
											 new Product(null,"Laptop",4000.0,false));

			products.forEach(p->{
				productRepository.save(p);
			});
		};
	}

//	@Bean
	public CommandLineRunner commandLineRunner2(UserInfoRepository userInfoRepository,PasswordEncoder passwordEncoder){
		return args -> {

			UserInfo user1 = new UserInfo(UUID.randomUUID().toString(),"said","1234","ROLE_USER");
			UserInfo admin = new UserInfo(UUID.randomUUID().toString(),"adminApp","1234","ROLE_ADMIN");
			user1.setPassword(passwordEncoder.encode(user1.getPassword()));
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
			userInfoRepository.save(user1);
			userInfoRepository.save(admin);
		};
	}


		


}
