package com.squiressoftware.crowdmix.users;

import java.util.Optional;

public interface UserRepository {
    User getOrCreateUser(String name);
    Optional<User> getUser(String name);
}
