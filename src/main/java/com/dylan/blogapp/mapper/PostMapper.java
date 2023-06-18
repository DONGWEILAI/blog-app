package com.dylan.blogapp.mapper;

import com.dylan.blogapp.dto.PostDTO;
import com.dylan.blogapp.entity.Post;

public class PostMapper {

    public static PostDTO toPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        postDTO.setTitle(post.getTitle());

        return postDTO;
    }

    public static Post toPost(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId() == null ? -1: postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());

        return post;
    }

}
