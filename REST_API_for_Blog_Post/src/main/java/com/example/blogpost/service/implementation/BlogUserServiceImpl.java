package com.example.blogpost.service.implementation;

import com.example.blogpost.dto.request.BlogUserCommentRequest;
import com.example.blogpost.dto.request.BlogUserLoginRequest;
import com.example.blogpost.dto.request.BlogUserSignupRequest;
import com.example.blogpost.dto.response.*;
import com.example.blogpost.entities.BlogUser;
import com.example.blogpost.entities.Comment;
import com.example.blogpost.entities.Likes;
import com.example.blogpost.entities.Post;
import com.example.blogpost.exception.ResourceNotFoundException;
import com.example.blogpost.repository.BlogUserRepository;
import com.example.blogpost.repository.CommentRepository;
import com.example.blogpost.repository.PostRepository;
import com.example.blogpost.service.BlogUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BlogUserServiceImpl implements BlogUserService {

    private final BlogUserRepository blogUserRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Override
    public BlogUserSignupResponse signupBlogUser(BlogUserSignupRequest blogUserSignupRequest) {
        Optional<BlogUser> blogUser = blogUserRepository.findBlogUserByEmail(blogUserSignupRequest.getEmail());
        if (blogUser.isPresent()) {
            throw new ResourceNotFoundException("User already exist");
        }
        BlogUser newBlogUser = BlogUser.builder()
                .name(blogUserSignupRequest.getName())
                .email(blogUserSignupRequest.getEmail())
                .password(blogUserSignupRequest.getPassword())
                .build();

        BlogUser saveBlogUser = blogUserRepository.save(newBlogUser);
        return BlogUserSignupResponse.builder()
                .id(saveBlogUser.getId())
                .email(saveBlogUser.getEmail())
                .name(saveBlogUser.getName())
                .build();
    }

    @Override
    public BlogUserLoginResponse loginBlogUser(BlogUserLoginRequest blogUserLoginRequest, HttpServletRequest request) {
        Optional<BlogUser> optionalBlogUser = blogUserRepository.findBlogUserByEmail(blogUserLoginRequest.getEmail());
        if (optionalBlogUser.isEmpty()) {
            throw new ResourceNotFoundException("Invalid request, please enter the right credentials");
        }
        BlogUser blogUser = optionalBlogUser.get();
        if (!blogUserLoginRequest.getPassword().equals(blogUser.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        } else {
            if (!blogUserLoginRequest.getEmail().equals(blogUser.getEmail())) {
                throw new ResourceNotFoundException("Invalid email or password");
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("BlogUser", blogUser);
        return BlogUserLoginResponse.builder()
                .id(blogUser.getId())
                .email(blogUser.getEmail())
                .message("Welcome " + blogUser.getName())
                .build();
    }

    @Override
    public BlogUserCommentResponse makeComment(BlogUserCommentRequest blogUserCommentRequest, Long postId, Long blogUserId) {
        Optional<Post> optionalPost = postRepository.findPostById(postId);
        if (optionalPost.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, the post doesn't exist");
        }
        Optional<BlogUser> optionalBlogUser = blogUserRepository.findBlogUserById(blogUserId);
        if (optionalBlogUser.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, the BlogUser doesn't exist");
        }
        Post post = optionalPost.get();
        BlogUser blogUser = optionalBlogUser.get();
// Creating new comment
        Comment newComment = Comment.builder()
                .content(blogUserCommentRequest.getComment())
                .post(post)
                .blogUser(blogUser)
                .build();
        Comment savedComment = commentRepository.save(newComment);
        return BlogUserCommentResponse.builder()
                .post(post)
                .comment(savedComment.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public BlogUserLikeResponse likePost(Long postId, Long blogUserId) {
        Optional<Post> optionalPost = postRepository.findPostById(postId);
        if (optionalPost.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, the post doesn't exist");
        }
        Optional<BlogUser> optionalBlogUser = blogUserRepository.findBlogUserById(blogUserId);
        if (optionalBlogUser.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, the BlogUser doesn't exist");
        }
        Post post = optionalPost.get();
        BlogUser blogUser = optionalBlogUser.get();

// Check if the user has already liked the post
        boolean hasLiked = post.getLikes().stream()
                .anyMatch(like -> like.getBlogUser().getId().equals(blogUserId));
        if (hasLiked) {
            throw new ResourceNotFoundException("You have already liked this post");
        }
// Create a new like
        Likes newLike = Likes.builder()
                .post(post)
                .blogUser(blogUser)
                .build();

        post.getLikes().add(newLike);
        blogUserRepository.save(blogUser);
        postRepository.save(post);

        return BlogUserLikeResponse.builder()
                .postId(postId)
                .blogUserId(blogUserId)
                .likedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public CommentDeleteResponse deleteComment(Long blogUserId, Long commentId) {
        Optional<BlogUser> optionalBlogUser = blogUserRepository.findBlogUserById(blogUserId);
        if (optionalBlogUser.isEmpty()) {
            throw new ResourceNotFoundException("You are not authorized");
        }
        BlogUser blogUser = optionalBlogUser.get();
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            throw new ResourceNotFoundException("Sorry, Comment doesn't exist");
        }
        Comment comment = optionalComment.get();
        if (!blogUser.getId().equals(comment.getBlogUser().getId())) {
            throw new ResourceNotFoundException("You are not authorized to delete this comment");
        }
        return null;
    }
}