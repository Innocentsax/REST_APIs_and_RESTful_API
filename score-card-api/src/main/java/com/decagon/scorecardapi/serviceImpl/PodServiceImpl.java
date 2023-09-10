package com.decagon.scorecardapi.serviceImpl;
import com.decagon.scorecardapi.dto.PodResponseDto;
import com.decagon.scorecardapi.dto.PodRequestDto;
import com.decagon.scorecardapi.exception.CustomException;
import com.decagon.scorecardapi.exception.ResourceNotFoundException;
import com.decagon.scorecardapi.model.Admin;
import com.decagon.scorecardapi.model.Pod;
import com.decagon.scorecardapi.model.Stack;
import com.decagon.scorecardapi.repository.AdminRepository;
import com.decagon.scorecardapi.repository.PodRepository;
import com.decagon.scorecardapi.repository.StackRepository;
import com.decagon.scorecardapi.service.PodService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PodServiceImpl implements PodService {
    private final PodRepository podRepository;
    private final StackRepository stackRepository;
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;



    @Override
    public PodResponseDto createPod(Long stackId, PodRequestDto requestDto) {
        List<Admin> adminList = new ArrayList<>();
        Stack stack = stackRepository.findById(stackId).orElseThrow(()->new ResourceNotFoundException("stackname", "id", stackId));
        Admin stackAssociate = adminRepository.findAdminByEmail(requestDto.getStackAssociateByEmail()).orElseThrow(()->new ResourceNotFoundException("stack Associate", "id", stackId));

        Admin programAssociate = adminRepository.findAdminByEmail(requestDto.getProgramAssociateByEmail()).orElseThrow(()->new ResourceNotFoundException("program Associate", "id", stackId));
        adminList.add(stackAssociate);
        adminList.add(programAssociate);

        if(podRepository.existsByPodName(requestDto.getPodName())) {
            throw new CustomException("Pod already exist");
        }

        Pod pod = new Pod();
        pod.setStack(stack);
        pod.setPodName(requestDto.getPodName());
        pod.setAdmin(adminList);
        Pod newPod = podRepository.save(pod);
        List<Pod> stackAssociatePods = stackAssociate.getPods();
        List<Pod> programAssociatePods = programAssociate.getPods();
        if(stackAssociatePods == null ){
            stackAssociatePods = new ArrayList<>();
        }
        stackAssociatePods.add(pod);
        stackAssociate.setPods(stackAssociatePods);
        adminRepository.save(stackAssociate);

        if(programAssociatePods == null ){
            programAssociatePods = new ArrayList<>();
        }
        programAssociatePods.add(pod);
        programAssociate.setPods(programAssociatePods);
        adminRepository.save(programAssociate);

        System.out.println(newPod.getAdmin().size());
        return modelMapper.map(newPod, PodResponseDto.class);

    }

    @Override
    public PodRequestDto updatePod(Long PodId, PodRequestDto requestDto) {
       Optional <Pod>  podUpdate  = podRepository.findById(PodId);
       if( podUpdate.isEmpty()) {
          throw new ResourceNotFoundException("pod not found ", "id", PodId);
       }
            List<Admin> newAdminList = new ArrayList();
            Admin stackAssociate = adminRepository.findAdminByEmail(requestDto.getStackAssociateByEmail()).orElseThrow(()->new CustomException("Stack Associate does not exist"));
            Admin programAssociate = adminRepository.findAdminByEmail(requestDto.getProgramAssociateByEmail()).orElseThrow(()->new CustomException("Program Associate does not exist"));
            newAdminList.add(stackAssociate);
            newAdminList.add(programAssociate);
            podUpdate.get().setAdmin(newAdminList);
            podUpdate.get().setPodName(requestDto.getPodName());
            podRepository.save(podUpdate.get());
            return requestDto;

    }
}








