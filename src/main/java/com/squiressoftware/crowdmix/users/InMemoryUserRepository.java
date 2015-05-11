package com.squiressoftware.crowdmix.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository  implements UserRepository{

    private List<User> users = new ArrayList<>();
    
    @Override
    public User getOrCreateUser(String name) {
        Optional<User> existingUser = getUser(name);
        if(existingUser.isPresent()){
            return existingUser.get();
        }else
        {
            User newUser = new User(name);
            users.add(newUser);
            return newUser;
        }
    }

    @Override
    public Optional<User> getUser(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst();
    }
}
