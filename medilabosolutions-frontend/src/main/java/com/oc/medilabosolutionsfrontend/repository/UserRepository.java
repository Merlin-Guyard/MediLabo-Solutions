package com.oc.medilabosolutionsfrontend.repository;

import com.oc.medilabosolutionsfrontend.Model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private User user = new User();

    public void changeUser(User newUser) {
        user = newUser;
    }

    public User getUser() {
                return user;
        }
}
