package com.medilabosolutionsbackend.repository;

import com.medilabosolutionsbackend.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
        List<User> userList = new ArrayList<>();

        public void addUser(User user) {
            userList.add(user);
        }

        public List<User> getAllUser() {
            return userList;
        }


    public Optional<User> getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}