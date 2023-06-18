package com.dylan.blogapp.service.impl;

import com.dylan.blogapp.dto.PostDTO;
import com.dylan.blogapp.dto.PostResponseDTO;
import com.dylan.blogapp.entity.Post;
import com.dylan.blogapp.exception.ResourceNotFoundException;
import com.dylan.blogapp.mapper.PostMapper;
import com.dylan.blogapp.repository.PostRepository;
import com.dylan.blogapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = PostMapper.toPost(postDTO);
        return PostMapper.toPostDTO(postRepository.save(post));
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return PostMapper.toPostDTO(post);
    }

    @Override
    public PostResponseDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> allPostPages = postRepository.findAll(pageable);
        List<Post> allPosts = allPostPages.getContent();
        System.out.println(allPosts.get(0).getComments());
        List<PostDTO> allPostDTOs = allPosts
                .stream()
                .map(PostMapper::toPostDTO)
                .collect(Collectors.toList());

        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO.setContent(allPostDTOs);
        postResponseDTO.setPageNo(allPostPages.getNumber());
        postResponseDTO.setPageSize(allPostPages.getSize());
        postResponseDTO.setTotalElements(allPostPages.getTotalElements());
        postResponseDTO.setTotalPages(allPostPages.getTotalPages());
        postResponseDTO.setLast(allPostPages.isLast());
        postResponseDTO.setFirst(allPostPages.isFirst());

        return postResponseDTO;
    }

    @Override
    public PostDTO updatePost(PostDTO postDto, long id) {
        checkPostExist(id);
        Post post = PostMapper.toPost(postDto);
        post.setId(id);
        return PostMapper.toPostDTO(postRepository.save(post));
    }

    @Override
    public void deletePostById(long id) {
        checkPostExist(id);
        postRepository.deleteById(id);
    }

    private void checkPostExist(long id){
        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }


}
