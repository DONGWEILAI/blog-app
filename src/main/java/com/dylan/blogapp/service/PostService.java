package com.dylan.blogapp.service;

import com.dylan.blogapp.dto.PostDTO;
import com.dylan.blogapp.dto.PostResponseDTO;

public interface PostService {
    //PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO createPost(PostDTO postDTO);
    PostDTO getPostById(long id);
    PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO updatePost(PostDTO postDto, long id);
    void deletePostById(long id);

}
