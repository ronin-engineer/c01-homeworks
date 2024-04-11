package com.roninhub.security.domain.post.entity;

import com.roninhub.security.domain.post.constant.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {
    private Long id;

    private String title;

    private String content;

    private String summary;

    private String avatarUrl;

    private String slug;

    private PostType type;
}