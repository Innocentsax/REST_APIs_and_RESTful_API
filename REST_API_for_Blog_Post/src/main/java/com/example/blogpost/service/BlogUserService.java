package com.example.blogpost.service;

import com.example.blogpost.dto.request.BlogUserCommentRequest;
import com.example.blogpost.dto.request.BlogUserLoginRequest;
import com.example.blogpost.dto.request.BlogUserSignupRequest;
import com.example.blogpost.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;

public interface BlogUserService {
    BlogUserSignupResponse signupBlogUser (BlogUserSignupRequest blogUserSignupRequest);
    BlogUserLoginResponse loginBlogUser (BlogUserLoginRequest blogUserLoginRequest, HttpServletRequest request);
    BlogUserCommentResponse makeComment (BlogUserCommentRequest blogUserCommentRequest, Long postId, Long blogUserId);
    BlogUserLikeResponse likePost (Long postId, Long blogUserId);
    CommentDeleteResponse deleteComment (Long blogUserId, Long commentId);
}
