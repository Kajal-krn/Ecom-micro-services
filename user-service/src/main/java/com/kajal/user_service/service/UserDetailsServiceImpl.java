package com.kajal.user_service.service;

import com.kajal.user_service.entity.User;
import com.kajal.user_service.entity.UserInfo;
import com.kajal.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return new UserInfo(user.getId(), user.getEmail(), user.getUserName(), user.getPassword(), user.getRoles());
        }

        throw new UsernameNotFoundException("User not found with username : " + username);
    }
}
