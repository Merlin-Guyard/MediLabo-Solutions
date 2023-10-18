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

        public Optional<User> getUserById(String id) {
            for (User user : userList) {
                if (user.getId().equals(id)) {
                    return Optional.of(user);
                }
            }
            return Optional.empty();
        }
        public List<User> getAllUser() {
            return userList;
        }
    }