package com.dylan.blogapp.controller;

import com.dylan.blogapp.dto.PostDTO;
import com.dylan.blogapp.dto.PostResponseDTO;
import com.dylan.blogapp.service.PostService;
import com.dylan.blogapp.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PostResponseDTO> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir){

        PostResponseDTO postResponseDTO = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponseDTO>(postResponseDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable long id,
                                              @Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.updatePost(postDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable long id){
        postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
