package com.jd.springbootsecurity;

import com.jd.springbootsecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by jd birla on 18-02-2023 at 06:44
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
