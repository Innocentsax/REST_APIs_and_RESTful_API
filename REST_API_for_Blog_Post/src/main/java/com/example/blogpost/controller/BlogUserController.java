package com.example.blogpost.controller;

import com.example.blogpost.dto.request.BlogUserCommentRequest;
import com.example.blogpost.dto.request.BlogUserLoginRequest;
import com.example.blogpost.dto.request.BlogUserSignupRequest;
import com.example.blogpost.service.BlogUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogUserController {
    private final BlogUserService blogUserService;

    @PostMapping("/signup/blogUser")
    public ResponseEntity<?> createBlogUser (@RequestBody @Valid BlogUserSignupRequest blogUserSignupRequest){
        var response = blogUserService.signupBlogUser(blogUserSignupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login/blogUser")
    public ResponseEntity<?> loginBlogUser (@RequestBody BlogUserLoginRequest blogUserLoginRequest, HttpServletRequest request){
        var response = blogUserService.loginBlogUser(blogUserLoginRequest, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createComment/{postId}/{blogUser}")
    public ResponseEntity<?> makeComment (@RequestBody @Valid BlogUserCommentRequest blogUserCommentRequest,
                                          @PathVariable("postId") Long postId, @PathVariable ("blogUser") Long blogUserId ){
        var response = blogUserService.makeComment(blogUserCommentRequest, postId, blogUserId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/likePost/{PostId}/{BlogUserId}")
    public ResponseEntity<?> likePost (@PathVariable("PostId") Long postId, @PathVariable("BlogUserId") Long blogUserId){
        var response = blogUserService.likePost(postId, blogUserId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteComment/{BlogUserId}/{CommentId}")
    public ResponseEntity<?> deleteComment (@PathVariable("BlogUserId")Long blogUserId, @PathVariable("CommentId") Long commentId){
        var response = blogUserService.deleteComment(blogUserId, commentId);
        return ResponseEntity.ok(response);
    }
}
