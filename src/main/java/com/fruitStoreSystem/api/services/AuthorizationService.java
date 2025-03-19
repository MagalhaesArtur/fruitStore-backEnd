package com.fruitStoreSystem.api.services;


import com.fruitStoreSystem.api.domain.user.User;
import com.fruitStoreSystem.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  repository.findByLogin(username);
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        return (User) repository.findByLogin(username);
    }


    public User createUser(User data){
        repository.save(data);
        return data;
    }
}
