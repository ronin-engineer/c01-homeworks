package com.roninhub.security.domain.post.dto;

import com.roninhub.security.domain.post.constant.PostType;
import com.roninhub.security.domain.post.entity.Post;
import com.roninhub.security.infrastructure.util.ObjectMapperUtil;
import lombok.Data;

@Data
public class PostResponse {
    private Long id;

    private String title;

    private String content;

    private String summary;

    private String avatarUrl;

    private String slug;

    private PostType type;

    public static PostResponse of(final Post post) {
        return ObjectMapperUtil.OBJECT_MAPPER.convertValue(post, PostResponse.class);
    }
}