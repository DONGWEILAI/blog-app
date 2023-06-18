package com.dylan.blogapp.service;

import com.dylan.blogapp.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDTO createComment(long postId, CommentDTO commentDTO);
    CommentDTO getCommentById(long postId, long commentId);
    CommentDTO updateComment(Long postId, long commentId, CommentDTO commentDTO);
    void deleteCommentById(long postId, long commentId);
    List<CommentDTO> getAllCommentsByPostId(long postId);
}
