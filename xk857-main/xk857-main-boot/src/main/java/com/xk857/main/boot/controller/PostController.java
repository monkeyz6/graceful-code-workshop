package com.xk857.main.boot.controller;

import com.xk857.framework.common.Result;
import com.xk857.main.boot.annotation.WorkshopDemo;
import com.xk857.main.boot.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redisson/post")
@RequiredArgsConstructor
@Tag(name = "RTransaction演示", description = "点赞事务")
@WorkshopDemo(scene = "跨键原子契约", focus = "帖子点赞+用户喜欢")
public class PostController {

    private final PostLikeService postLikeService;

    @Operation(summary = "点赞")
    @PostMapping("/like")
    public Result<Boolean> like(@RequestParam String userId, @RequestParam String postId) {
        postLikeService.like(userId, postId);
        return Result.success(true);
    }

    @Operation(summary = "点赞数")
    @GetMapping("/likes")
    public Result<Integer> likes(@RequestParam String postId) {
        return Result.success(postLikeService.likeCount(postId));
    }

    @Operation(summary = "是否已点赞")
    @GetMapping("/liked")
    public Result<Boolean> liked(@RequestParam String userId, @RequestParam String postId) {
        return Result.success(postLikeService.userLiked(userId, postId));
    }
}

