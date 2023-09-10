package com.decagon.scorecardapi.service;
import com.decagon.scorecardapi.dto.*;
import com.decagon.scorecardapi.dto.requestdto.AdminDto;
import com.decagon.scorecardapi.dto.responsedto.APIResponse;
import com.decagon.scorecardapi.dto.responsedto.PodResponseDto;
import com.decagon.scorecardapi.dto.responsedto.SquadDto;
import com.decagon.scorecardapi.dto.responsedto.StackResponseDto;
import com.decagon.scorecardapi.model.Admin;
import com.decagon.scorecardapi.model.Pod;
import com.decagon.scorecardapi.model.Squad;
import com.decagon.scorecardapi.model.Stack;
import com.decagon.scorecardapi.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SuperAdminService {

    List<Pod> listOfPods();

    String removeAdminById(Long id);

    User CreateAdmin(AdminDto adminDto);

    String createSquad(SquadDto squadDto);

    APIResponse getAdmin(Long id);

    Page<Squad> getAllSquads(int offset, int pageSize);

    List<StackResponseDto> getDetailsOfAllStacks(Long squadId);

    Stack getStackUsingId(Long id);

    APIResponse<String> updateStack(StackDto stackDto, Long id);
    APIResponse<Admin> updateAdmin(AdminDto adminDto, Long adminId);

    APIResponse<User>activateAdmin(Long adminId);
    APIResponse<User> deactivateAdmin(Long adminId);

    Pod getPod(Long id);

    APIResponse<?> forgotPassword(ForgetPasswordRequest request);

    APIResponse<?> resetPassword(ResetPasswordRequest request);


    APIResponse<?> changePassword(ChangePasswordRequest request, String email);

    List<PodResponseDto> getAllPodsInAStack(Long stackId);
}

