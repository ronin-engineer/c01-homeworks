package com.roninhub.security.domain.post.service;

import com.roninhub.security.domain.post.constant.PostType;
import com.roninhub.security.domain.post.dto.PostResponse;
import com.roninhub.security.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<PostResponse> getPostByType(PostType postType) {
        return postRepository.getAllPostsByType(postType).stream().map(PostResponse::of).toList();
    }
}
