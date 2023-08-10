package com.example.blogpost.controller;

import com.example.blogpost.dto.request.AdminRequestDto;
import com.example.blogpost.dto.response.DeletePostDto;
import com.example.blogpost.dto.request.PostRequestDto;
import com.example.blogpost.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/path")
public class AdminController {
    private final AdminService adminService;

    @PostMapping(path = "/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid AdminRequestDto request) {
        var response = adminService.createAdmin(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AdminRequestDto adminRequestDto, HttpServletRequest request){
        var loginResponse = adminService.loginAdmin(adminRequestDto, request);
        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping(path = "/create-post/{id}")
    public ResponseEntity<?> createPost(@PathVariable("id") Long id, @RequestBody @Valid PostRequestDto postRequestDto) {
        var response = adminService.createPost(postRequestDto, id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/editPost/{AdminId}/{PostId}")
    public ResponseEntity<?> editPost (@RequestBody @Valid PostRequestDto postRequestDto, @PathVariable("AdminId") Long adminId,
                                       @PathVariable ("PostId")Long postId){
        var response = adminService.updatePost(postRequestDto, adminId, postId);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-task/{id}/{postId}")
    public ResponseEntity<DeletePostDto> deletePost(@PathVariable("id") Long adminId, @PathVariable("postId") Long postId){
        DeletePostDto deleteDto = adminService.deletePost(adminId, postId);
        return ResponseEntity.ok(deleteDto);
    }
//

}
