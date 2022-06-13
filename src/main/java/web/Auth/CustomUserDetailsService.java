package web.Auth;

import AlexTh.models.AutoUser;
import AlexTh.repository.AutoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


//@Component("customUserDetailsService")
public class CustomUserDetailsService /*implements UserDetailsService */{

//    @Autowired
//    private AutoUserRepository repo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AutoUser user = repo.findByUsername(username);
//        return user;
//    }

}
