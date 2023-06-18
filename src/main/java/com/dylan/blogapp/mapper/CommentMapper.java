package com.dylan.blogapp.mapper;

import com.dylan.blogapp.dto.CommentDTO;
import com.dylan.blogapp.entity.Comment;

public class CommentMapper {

    public static CommentDTO toCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setContent(comment.getContent());

        return commentDTO;
    }

    public static Comment toComment(CommentDTO commentDTO){

        Comment comment = new Comment();
        comment.setId(commentDTO.getId() == null?-1: commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setContent(commentDTO.getContent());

        return comment;
    }

}
