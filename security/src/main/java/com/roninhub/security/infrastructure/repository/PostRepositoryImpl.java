package com.roninhub.security.infrastructure.repository;

import com.roninhub.security.domain.post.constant.PostType;
import com.roninhub.security.domain.post.entity.Post;
import com.roninhub.security.domain.post.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {
    // Fake data
    public static final Map<Long, Post> POST_DATA = Map.of(
            1L, new Post(1L, "title1", "content1", "summary1", "avatarUrl1", "slug1", PostType.FREE),
            2L, new Post(2L, "title2", "content2", "summary2", "avatarUrl2", "slug2", PostType.FREE),
            3L, new Post(3L, "title3", "content3", "summary3", "avatarUrl3", "slug3", PostType.PREMIUM),
            4L, new Post(4L, "title4", "content4", "summary4", "avatarUrl4", "slug4", PostType.PREMIUM),
            5L, new Post(5L, "title5", "content5", "summary5", "avatarUrl5", "slug5", PostType.PREMIUM)
    );

    @Override
    public List<Post> getAllPostsByType(PostType type) {
        return POST_DATA.values().stream().filter(post -> post.getType().equals(type)).toList();
    }
}
