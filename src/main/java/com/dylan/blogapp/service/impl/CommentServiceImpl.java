package com.dylan.blogapp.service.impl;

import com.dylan.blogapp.dto.CommentDTO;
import com.dylan.blogapp.entity.Comment;
import com.dylan.blogapp.entity.Post;
import com.dylan.blogapp.exception.BlogAPIException;
import com.dylan.blogapp.exception.ResourceNotFoundException;
import com.dylan.blogapp.mapper.CommentMapper;
import com.dylan.blogapp.repository.CommentRepository;
import com.dylan.blogapp.repository.PostRepository;
import com.dylan.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDTO createComment(long postId, CommentDTO commentDTO) {
        Comment comment = CommentMapper.toComment(commentDTO);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return CommentMapper.toCommentDTO(newComment);
    }

    @Override
    public CommentDTO getCommentById(long postId, long commentId) {
        checkBlogAPI(postId, commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        return CommentMapper.toCommentDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments
                .stream()
                .map(CommentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO updateComment(Long postId, long commentId, CommentDTO commentDTO) {
        checkBlogAPI(postId, commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        comment.setContent(commentDTO.getContent());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        return CommentMapper.toCommentDTO(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(long postId, long commentId){
        checkBlogAPI(postId, commentId);
        commentRepository.deleteById(commentId);
    }

    private void checkBlogAPI(Long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
        if(!(comment.getPost().getId() == post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
    }

}
