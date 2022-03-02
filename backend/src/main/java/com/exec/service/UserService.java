package com.exec.service;
import com.exec.repository.UserRepository;
import com.exec.model.User;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.insert(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
