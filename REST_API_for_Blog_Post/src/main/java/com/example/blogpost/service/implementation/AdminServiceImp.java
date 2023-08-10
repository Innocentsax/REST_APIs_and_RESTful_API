package com.example.blogpost.service.implementation;

import com.example.blogpost.dto.request.AdminRequestDto;
import com.example.blogpost.dto.request.PostRequestDto;
import com.example.blogpost.dto.response.AdminResponse;
import com.example.blogpost.dto.response.DeletePostDto;
import com.example.blogpost.dto.response.PostResponseDto;
import com.example.blogpost.entities.Admin;
import com.example.blogpost.entities.Post;
import com.example.blogpost.exception.InvalidEmailOrPasswordException;
import com.example.blogpost.exception.ResourceNotFoundException;
import com.example.blogpost.repository.AdminRepository;
import com.example.blogpost.repository.BlogUserRepository;
import com.example.blogpost.repository.PostRepository;
import com.example.blogpost.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {
    private final AdminRepository adminRepository;
    private final BlogUserRepository blogUserRepository;
    private final PostRepository postRepository;

    @Override
    public AdminResponse createAdmin(AdminRequestDto request) throws InvalidEmailOrPasswordException {
        Optional<?> adminValidation= adminRepository.findByEmail(request.getEmail());

        if (adminValidation.isPresent()) {
            throw new InvalidEmailOrPasswordException("Admin already signed up");
        }


//        //Using Build
        Admin newAdmin = Admin.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        Admin saveAdmin = adminRepository.save(newAdmin);
        return AdminResponse.builder()
                .id(saveAdmin.getId())
                .email(saveAdmin.getEmail())
                .build();

    }

    @Override
    public AdminResponse loginAdmin(AdminRequestDto adminRequestDto, HttpServletRequest request) {
      //  Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(loginRequestDto.getEmail());
        Optional<Admin> optionalAdmin =  adminRepository.findByEmail(adminRequestDto.getEmail());
            if(optionalAdmin.isEmpty()){
                throw new InvalidEmailOrPasswordException("invalid Email or Password");
            }
            Admin admin = optionalAdmin.get();
            if(!adminRequestDto.getPassword().equals(optionalAdmin.get().getPassword())){
                throw new InvalidEmailOrPasswordException("Invalid Email or Password");
            }
        HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            return AdminResponse.builder().email(admin.getEmail()).id(admin.getId()).build();
    }

    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long adminId) {

//        AppUser checkIfUserExist = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesnt Exist"));
//
//        Task task = new Task().builder()
//                .title(taskRequestDto.getTitle())
//                .description(taskRequestDto.getDescription())
//                .taskStatus(TaskStatus.PENDING)
//                .appUser(checkIfUserExist)
//                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//        Task savetask = taskRepository.save(task);
//
//        return TaskResponse.builder()
//                .id(savetask.getId())
//                .title(savetask.getTitle())
//                .description(savetask.getDescription())
//                .taskStatus(savetask.getTaskStatus())
//                .createdAt(savetask.getCreatedAt())
//                .updatedAt(savetask.getUpdatedAt())
//                .completedAt(savetask.getCompletedAt())
//                .build();
//        Admin checkAdmin = (Admin) adminRepository.findById(id).orElseThrow(()
//                -> new ResourceNotFoundException("Admin does not exist"));

//        Admin checkIfUserExist = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesnt Exist"));

        Optional<Admin> optionalAdmin = adminRepository.findAdminById(adminId);
        if (optionalAdmin.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Admin doesn't not exist");
        }
        Admin admin = optionalAdmin.get();
            Post post = Post.builder()
                    .title(postRequestDto.getTitle())
                    .category(postRequestDto.getCategory())
                    .content(postRequestDto.getContent())
                    .admin(admin)
                    .build();

            Post savePost = postRepository.save(post);

            return PostResponseDto.builder()
                    .id(savePost.getId())
                    .title(savePost.getTitle())
                    .category(savePost.getCategory())
                    .content(savePost.getContent())
                    .createdAt(LocalDateTime.now())
                    .build();
    }

    @Override
    public PostResponseDto updatePost(PostRequestDto postRequestDto, Long adminId, Long postId) {
        Optional<Admin> optionalAdmin = adminRepository.findAdminById(adminId);
        if (optionalAdmin.isEmpty()) {
            throw new ResourceNotFoundException("You are not an Admin");
        } else {
            Admin admin = optionalAdmin.get();
            Optional<Post> optionalPost = postRepository.findPostById(postId);
            if (optionalPost.isEmpty()) {
                throw new ResourceNotFoundException("Sorry, Post doesn't exist");
            } else {
                Post post = optionalPost.get();
                if (!admin.getId().equals(post.getAdmin().getId())) {
                    throw new ResourceNotFoundException("You are not authorized to edit this post");
                }
                post.setTitle(postRequestDto.getTitle());
                post.setContent(postRequestDto.getContent());
                post.setCategory(postRequestDto.getCategory());
                Post editedPost = postRepository.save(post);

                return PostResponseDto.builder()
                        .id(editedPost.getId())
                        .title(editedPost.getTitle())
                        .content(editedPost.getContent())
                        .category(editedPost.getCategory())
                        .build();
            }
        }
    }

    @Override
    public DeletePostDto deletePost(Long postId, Long adminId) {
        Optional<Admin>optionalAdmin = adminRepository.findById(adminId);
        if(optionalAdmin.isEmpty()){
            throw new ResourceNotFoundException("You do not have permission to do this");
        } else {
            Admin admin = optionalAdmin.get();
            Optional<Post> optionalPost = postRepository.findById(adminId);
            if(optionalPost.isEmpty()){
                throw new ResourceNotFoundException(("this task is not available"));
            }else {
                Post post = optionalPost.get();
                if(!post.getAdmin().getId().equals(admin.getId())) {
                    throw new ResourceNotFoundException("This task does not belong to the user");

                }
                postRepository.delete(post);
                DeletePostDto deleteDto = new DeletePostDto();
                deleteDto.setId(postId);
                deleteDto.setMessage("Task has been removed");
                return deleteDto;
            }
        }
    }

    @Override
    public List<PostResponseDto> viewAllPost(Long adminId) {
        return null;
    }

    @Override
    public PostResponseDto viewAPost(Long postId, Long adminId) {
        return null;
    }
}
