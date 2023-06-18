package com.dylan.blogapp.controller;

import com.dylan.blogapp.dto.CommentDTO;
import com.dylan.blogapp.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/posts/{postId}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable long postId,
                                                    @PathVariable long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable long postId,
                                                    @PathVariable long commentId,
                                                    @Valid @RequestBody CommentDTO commentDTO){
        CommentDTO newCommentDTO = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(newCommentDTO, HttpStatus.OK);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable long postId,
                                                    @PathVariable long commentId){
        CommentDTO newCommentDTO = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(newCommentDTO, HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPostId(@PathVariable long postId){
        List<CommentDTO> allCommentsByPostId = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(allCommentsByPostId, HttpStatus.OK);
    }


}
