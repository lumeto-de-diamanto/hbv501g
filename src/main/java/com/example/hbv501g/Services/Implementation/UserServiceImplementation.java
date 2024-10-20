package com.example.hbv501g.Services.Implementation;

import com.example.hbv501g.Persistence.Entities.User;
import com.example.hbv501g.Persistence.Repositories.UserRepository;
import com.example.hbv501g.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //@Override
    //public User findById(long ID) {
    //    return userRepository.findById(ID);
    //}

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User login(User user) {
        User doesExist = findByUsername(user.getUsername());
        System.out.println("User fetched: " + doesExist);
        if (doesExist != null) {
            if (doesExist.getPassword().equals(user.getPassword())) {
                //Set user as logged in
                doesExist.setLoggedIn(true);
                userRepository.save(doesExist);
                return doesExist;
            }
        }
        return null;
    }
/*
    @Override
    public void updateByPassword(String username, String password) {
        userRepository.updateByPassword(username, password);
    }*/

    @Override
    public void setLoggedIn(User user, boolean status) {
        user.setLoggedIn(status);
        userRepository.save(user);
    }
}
