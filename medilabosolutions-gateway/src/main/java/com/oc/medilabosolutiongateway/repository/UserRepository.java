package com.oc.medilabosolutiongateway.repository;

import com.oc.medilabosolutiongateway.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> userList= new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // User not found
    }
}