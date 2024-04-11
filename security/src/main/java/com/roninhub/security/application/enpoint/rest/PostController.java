package com.roninhub.security.application.enpoint.rest;

import com.roninhub.security.application.dto.response.ResponseDto;
import com.roninhub.security.application.service.ResponseFactory;
import com.roninhub.security.domain.post.constant.PostType;
import com.roninhub.security.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ResponseFactory responseFactory;

    @GetMapping("/premium")
    public ResponseDto getAllPostsPremium() {
        return responseFactory.response(postService.getPostByType(PostType.PREMIUM));
    }

    @GetMapping("/free")
    public ResponseDto getAllPostsFree() {
        return responseFactory.response(postService.getPostByType(PostType.FREE));
    }
}
