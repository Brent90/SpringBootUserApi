package com.slinger.app.repositories;

import com.slinger.app.domian.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
