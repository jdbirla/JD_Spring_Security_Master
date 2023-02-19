package com.jd.springbootsecurity;

import com.jd.springbootsecurity.model.MyUserDetails;
import com.jd.springbootsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by jd birla on 18-02-2023 at 06:28
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> byUserName = userRepository.findByUserName(username);
        byUserName.orElseThrow(() -> new UsernameNotFoundException("user not found : "+username));
        return byUserName.map(MyUserDetails::new).get();

    }
}
