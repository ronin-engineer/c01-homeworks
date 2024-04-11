package com.roninhub.security.domain.post.repository;

import com.roninhub.security.domain.post.constant.PostType;
import com.roninhub.security.domain.post.entity.Post;

import java.util.List;

public interface PostRepository {
    List<Post> getAllPostsByType(PostType type);
}
