package org.revo.Service;

import org.revo.Domain.User;

import java.util.Optional;

/**
 * Created by ashraf on 18/01/17.
 */
public interface UserService {
    User save(User user);

    void update(User user) throws Exception;

    User current();

    User user(Long id);

    Optional<User> findByEmail(String email);

    void active(Long id);

    long count();
}