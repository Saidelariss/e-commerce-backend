package ecom.backendecommerce.repositories;

import ecom.backendecommerce.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    Optional<UserInfo> findByUsername(String username);
}
