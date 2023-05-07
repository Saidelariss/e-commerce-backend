package ecom.backendecommerce.security;

import ecom.backendecommerce.entities.UserInfo;
import ecom.backendecommerce.repositories.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component

public class UserInfoDetailsService implements UserDetailsService {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByUsername(username);

        return userInfo.map(UserInfoUserDetails::new) // userInfo -> new UserInfoUserDetails(userInfo)
                .orElseThrow(() -> new UsernameNotFoundException("user not found : " + username));
    }
}
