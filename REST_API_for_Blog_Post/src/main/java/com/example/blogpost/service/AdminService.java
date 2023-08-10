package com.example.blogpost.service;

import com.example.blogpost.dto.request.AdminRequestDto;
import com.example.blogpost.dto.request.PostRequestDto;
import com.example.blogpost.dto.response.AdminResponse;
import com.example.blogpost.dto.response.DeletePostDto;
import com.example.blogpost.dto.response.PostResponseDto;
import com.example.blogpost.exception.InvalidEmailOrPasswordException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface AdminService {

    public AdminResponse createAdmin(AdminRequestDto request) throws InvalidEmailOrPasswordException;

    AdminResponse loginAdmin(AdminRequestDto adminRequestDto, HttpServletRequest request);
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long id);
    public PostResponseDto updatePost(PostRequestDto postRequestDto, Long adminId, Long postId);
    public DeletePostDto deletePost(Long postId, Long adminId);

    public List<PostResponseDto> viewAllPost(Long adminId);
    public PostResponseDto viewAPost(Long postId, Long adminId);

}
