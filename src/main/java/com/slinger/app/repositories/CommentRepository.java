package com.slinger.app.repositories;

import com.slinger.app.domian.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
