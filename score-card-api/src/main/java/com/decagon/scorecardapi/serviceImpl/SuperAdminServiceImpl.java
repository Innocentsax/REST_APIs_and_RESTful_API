package com.decagon.scorecardapi.serviceImpl;

import com.decagon.scorecardapi.dto.*;
import com.decagon.scorecardapi.dto.responsedto.PodResponseDto;
import com.decagon.scorecardapi.enums.AssignRole;
import com.decagon.scorecardapi.model.*;
import com.decagon.scorecardapi.repository.*;
import com.decagon.scorecardapi.dto.requestdto.AdminDto;
import com.decagon.scorecardapi.dto.responsedto.APIResponse;
import com.decagon.scorecardapi.dto.responsedto.SquadDto;
import com.decagon.scorecardapi.dto.responsedto.StackResponseDto;
import com.decagon.scorecardapi.enums.Role;
import com.decagon.scorecardapi.exception.CustomException;
import com.decagon.scorecardapi.exception.ResourceNotFoundException;
import com.decagon.scorecardapi.exception.SquadAlreadyExistException;
import com.decagon.scorecardapi.exception.UserNotFoundException;
import com.decagon.scorecardapi.exception.*;
import com.decagon.scorecardapi.service.EmailService;
import com.decagon.scorecardapi.service.SuperAdminService;
import com.decagon.scorecardapi.utility.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {


    private final PodRepository podRepository;
    private final UserRepository userRepository;
    private final SquadRepository squadRepository;
    private final StackRepository stackRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final StackTemplateRepository stackTemplateRepository;

    @Override
    public List<Pod> listOfPods() {
        return podRepository.findAll();
    }

    @Override
    public List<PodResponseDto> getAllPodsInAStack(Long stackId) {
//        String stackName = stackTemplateRepository.findById(stackId)
//                .orElseThrow(()-> new CustomException("Stack not found")).getStackName();

        Stack stack = stackRepository.findById(stackId)
                .orElseThrow(()-> new StackNotFoundException("Stack not found"));

        List<Pod> pods = stack.getPods();

        List<PodResponseDto> podResponseDtos = new ArrayList<>();
        pods.forEach(pod-> {
            PodResponseDto podResponseDto = new PodResponseDto();
            podResponseDto.setPodName(pod.getPodName());
            podResponseDto.setTotalDecadevs(pod.getDecadev().size());
            podResponseDto.setPodHealth(pod.getPodHealth());
            System.out.println("----"+pod.getAdmin().size());
            pod.getAdmin().forEach((admin)->{
                if(AssignRole.STACK_ASSOCIATE.equals(admin.getAssignRole())){
                    podResponseDto.setSA(admin.getFirstName()+ " "+admin.getLastName());
                }
                else{
                    podResponseDto.setPa(admin.getFirstName()+ " "+admin.getLastName());
                }
            });
            podResponseDtos.add(podResponseDto);
        });

        return podResponseDtos;


    }
    @Override
    public String removeAdminById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new CustomException("User not found");
        } else {
            userRepository.deleteById(id);
            return "Admin deleted successfully";
        }

    }

    @Override
    public User CreateAdmin(AdminDto adminDto) {
        if (userRepository.findByEmail(adminDto.getEmail()).isPresent()) {
            throw new CustomException("User email already exist");
        }
        Squad squad = squadRepository.findById(adminDto.getSquadId()).orElseThrow(()->new SquadNotFoundException("Squad not found"));
        Stack stack = stackRepository.findById(adminDto.getStackId()).orElseThrow(()-> new StackNotFoundException(" Stack not found"));
        Pod pod = null;
       if(adminDto.getPodId() !=  null && adminDto.getPodId() > 0 ){
           pod = podRepository.findById(adminDto.getPodId()).orElseThrow(()->new PodNotFoundException("pod mot found"));
       }
        StringBuilder password = Generator.generatePassword(10);
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());
        admin.setRole(adminDto.getRole());
        admin.setGender(adminDto.getGender());
        admin.setIsAccountActive(true);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setAssignRole(adminDto.getAssignRole());
        List<Squad> squads = new ArrayList<>();
        squads.add(squad);
        admin.setSquads(squads);
        List<Stack> stacks = new ArrayList<>();
        stacks.add(stack);
        admin.setStacks(stacks);
        List<Pod> pods = new ArrayList<>();
        if(pod != null ){
            pods.add(pod);
        }
        admin.setPods(pods);
        emailService.sendEmail("ScoreCard login details " + "password: " + password + " Email: " + admin.getEmail() + "\n",
                "You have been added as an admin", admin.getEmail());
        return userRepository.save(admin);
    }

    @Override

    public String createSquad(SquadDto squadDto) {
        if (squadRepository.existsBySquadName(squadDto.getSquadName())) {
            throw new SquadAlreadyExistException("Squad already exist");
        }
        Squad newSquad = new Squad();
        List<Stack> stacks = new ArrayList<>();

        List<String> stackNames = squadDto.getStackNames();
        for (String stackName : stackNames) {
            Stack stack = new Stack();
            stack.setStackName(stackName);
            stackRepository.save(stack);
            stacks.add(stack);
        }
        newSquad.setSquadName(squadDto.getSquadName());
        newSquad.setStacks(stacks);
        squadRepository.save(newSquad);
        return "Squad created successfully";

    }

    @Override
    public APIResponse getAdmin(Long id) {

        User admin = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("admin not found"));
        if (admin.getRole().equals(Role.ADMIN)) {
            return new APIResponse<>(true, "Successfully found an admin", admin);

        }
        return new APIResponse<>(true, "This person is not an admin", admin);
    }

    public Page<Squad> getAllSquads(int offset, int pageSize) {
        Pageable pageable = PageRequest.of(offset, pageSize);
        return squadRepository.findAll(pageable);
    }

    public List<StackResponseDto> getDetailsOfAllStacks(Long squadId) {
        List<Stack> stacks = stackRepository.findAllStackBySquadId(squadId);
        List<StackResponseDto> stackResponseDtos = new ArrayList<>();
        for (Stack stack : stacks){
            StackResponseDto stackResponseDto = new StackResponseDto();
            stackResponseDto.setStackName(stack.getStackName());
            stackResponseDto.setPodCount(stack.getPods().size());
            stackResponseDtos.add(stackResponseDto);
        }
        return stackResponseDtos;
    }


    @Override
    public APIResponse<String> updateStack(StackDto stackDto, Long id) {
        Optional<Stack> optionalStack = stackRepository.findById(id);
        if (optionalStack.isEmpty()) {
            throw new ResourceNotFoundException("Stack", "", id);
        }
        Stack stack = optionalStack.get();
        stack.setStackName(stackDto.getStackName());
        stackRepository.save(stack);

        return new APIResponse<>(true, "Stack Updated Successfully");

    }

    public Stack getStackUsingId(Long id) {
       Optional<Stack> optionalStack = stackRepository.findById(id);
        if (optionalStack.isEmpty()) {
            throw new ResourceNotFoundException("Stack not found", "", id);
        }
       return optionalStack.get();
    }

    @Override
    public APIResponse<Admin> updateAdmin(AdminDto adminDto, Long adminId) {
        Admin adminName = (Admin) userRepository.findById(adminId).orElseThrow(() -> new CustomException("Admin not found"));
        adminName.setFirstName(adminDto.getFirstName());
        adminName.setLastName(adminDto.getLastName());
        adminName.setEmail(adminDto.getEmail());
        adminName.setRole(adminDto.getRole());
        adminName.setAssignRole(adminDto.getAssignRole());
        userRepository.save(adminName);
        return new APIResponse<>(true, "Admin updated successfully", adminName);
    }

    @Override
    public APIResponse<User> activateAdmin(Long adminId) {
        Admin admin = (Admin) userRepository.findById(adminId).orElseThrow(() -> new CustomException("Admin not found"));
         admin.activateUser();
        userRepository.save(admin);
        return new APIResponse<>(true, "Admin activated successfully", admin);
    }

    @Override
    public APIResponse<User> deactivateAdmin(Long adminId) {
        Admin admin = (Admin) userRepository.findById(adminId).orElseThrow(() -> new CustomException("Admin not found"));
        admin.deactivateUser();
        userRepository.save(admin);
        return new APIResponse<>(true, "Admin deactivated successfully", admin);
    }


    public Pod getPod(Long id) {

        return podRepository.findById(id).orElseThrow(()-> new PodNotFoundException(String.format("Pod with id %d not found",id)));
    }

    @Override
    public APIResponse<?> forgotPassword(ForgetPasswordRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else {
            StringBuilder password = Generator.generateOTP(5);
            user.get().setUserOTP(passwordEncoder.encode(password));
            user.get().setUpdateDate(LocalDateTime.now());
            userRepository.save(user.get());
            emailService.sendEmail("You can now reset your password for this email " + user.get().getEmail() + " and this token " + password + "\n",
                    "Password reset", user.get().getEmail());
            return new APIResponse<>(true, "Verify OTP");
        }
    }

    @Override
    public APIResponse<?> resetPassword(ResetPasswordRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        else {
            if (passwordEncoder.matches(request.getUserOTP(), user.get().getUserOTP())) {
                if (LocalDateTime.now().isBefore(user.get().getUpdateDate().plusMinutes(10))){
                    user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                    user.get().setUserOTP(null);
                    userRepository.save(user.get());
                    return new APIResponse<>(true, "Password changed successfully");
                }else
                    throw new TokenExpiredException("Token has expired");
            }
            else {
                throw new PasswordNotMatchException("invalid token provided");
            }
        }
    }
    @Override
    public APIResponse<?> changePassword(ChangePasswordRequest request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordNotMatchException("New password and confirm password do not match");
        }
        if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return new APIResponse<>(true, "Password changed successfully");
        }
        else {
            throw new PasswordNotMatchException("Old password does not match");
        }
    }



}

