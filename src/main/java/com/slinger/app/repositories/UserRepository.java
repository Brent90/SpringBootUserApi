package com.slinger.app.repositories;

import com.slinger.app.domian.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
