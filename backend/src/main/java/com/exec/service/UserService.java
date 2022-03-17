package com.exec.service;
import com.exec.repository.UserRepository;
import com.exec.model.User;
import java.util.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.exec.model.Admin;
@Lazy
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AdminService adminService;
    public UserService(UserRepository userRepository, AdminService adminService) {
        this.userRepository = userRepository;
        this.adminService = adminService;
    }

    public void addUser(User user) {
        userRepository.insert(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }
}
