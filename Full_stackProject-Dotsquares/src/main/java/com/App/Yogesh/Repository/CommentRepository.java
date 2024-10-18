package com.App.Yogesh.Repository;

import com.App.Yogesh.Models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
