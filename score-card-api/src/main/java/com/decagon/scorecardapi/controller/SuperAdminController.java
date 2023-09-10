
package com.decagon.scorecardapi.controller;

import com.decagon.scorecardapi.dto.StackDto;
import com.decagon.scorecardapi.dto.requestdto.AdminDto;
import com.decagon.scorecardapi.dto.responsedto.APIResponse;
import com.decagon.scorecardapi.dto.responsedto.PodResponseDto;
import com.decagon.scorecardapi.dto.responsedto.SquadDto;
import com.decagon.scorecardapi.dto.responsedto.StackResponseDto;
import com.decagon.scorecardapi.model.*;
import com.decagon.scorecardapi.response.AdminResponse;
import com.decagon.scorecardapi.service.AdminService;
import com.decagon.scorecardapi.service.SuperAdminService;
import com.decagon.scorecardapi.serviceImpl.SquadImpl;
import com.decagon.scorecardapi.utility.Responder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/super-admin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final SuperAdminService superAdminService;
    private final SquadImpl squadImpl;
    private final AdminService adminService;

    @GetMapping("/pods")
    public ResponseEntity<List<Pod>>getAllPods(){
        List<Pod>allPods = superAdminService.listOfPods();
        return new ResponseEntity<>(allPods,HttpStatus.OK);
    }

    @GetMapping("/pods/{id}")
    public ResponseEntity<List<PodResponseDto>>getAllPodsInAStack(@PathVariable("id") Long stackId){
        List<PodResponseDto>allPods = superAdminService.getAllPodsInAStack(stackId);
        return new ResponseEntity<>(allPods,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deleteAdmin(@PathVariable("id") Long id){
        try{
             return new ResponseEntity<>(new APIResponse<>(true, "Admin deleted successfully", superAdminService.removeAdminById(id)), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(new APIResponse(false,"User not found",null),HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/create-admin")
    public ResponseEntity<APIResponse<?>> createAdmin(@RequestBody AdminDto adminDto ) {

            User admin = superAdminService.CreateAdmin(adminDto);
            return new ResponseEntity<>(new APIResponse<>(true, "Admin created successfully", admin), HttpStatus.CREATED);
    }

    @PostMapping("/create-squad")
    public ResponseEntity<APIResponse<String>> createSquad (@RequestBody SquadDto squadDto) {
        return new ResponseEntity<>(new APIResponse<>(true,  superAdminService.createSquad(squadDto)), HttpStatus.CREATED);
    }


    @PostMapping("/get-squad/{id}")
    public ResponseEntity<APIResponse>getSquad(@PathVariable Long id) {
        return new ResponseEntity<>(new APIResponse<>(true, "Squad found", squadImpl.getSquad(id)), HttpStatus.OK);
    }

    @GetMapping("/get-admin{id}")
    public ResponseEntity<APIResponse> getAdmin(@PathVariable (value = "id")Long id){
        return new ResponseEntity<>(superAdminService.getAdmin(id),HttpStatus.OK);

    }

    @GetMapping(value ="/get-pod/{podId}")
    public ResponseEntity<APIResponse<?>> getPod(@PathVariable(value = "podId")Long id){
        return  Responder.successful(superAdminService.getPod(id));
    }

    @GetMapping("/squads/{offset}/{pageSize}")
    public ResponseEntity<Page<Squad>> getAllSquads(@PathVariable("offset") int offset,
                                                    @PathVariable("pageSize") int pageSize){
        Page<Squad> squads = superAdminService.getAllSquads(offset, pageSize);
        return new ResponseEntity<>(squads, HttpStatus.OK);
    }

    @GetMapping("/{squadId}/stacks")
    public ResponseEntity<List<StackResponseDto>> getDetailsOfAllStacks(@PathVariable("squadId") Long squadId) {
        List<StackResponseDto> stacks = superAdminService.getDetailsOfAllStacks(squadId);
        return new ResponseEntity<>(stacks, HttpStatus.OK);
    }

    @GetMapping("/admins")
    public ResponseEntity<?> getAllAdmin(){
        List<AdminResponse> admins = adminService.getAllAdmin();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PutMapping("/update-stack/{stackId}")
    public ResponseEntity<APIResponse<String>> updateAStack(@RequestBody StackDto stackDto,
                                                            @PathVariable Long stackId) {
        return new ResponseEntity<>(superAdminService.updateStack(stackDto, stackId), HttpStatus.OK);

    }

    @GetMapping("/get-stack/{stackId}")
    public ResponseEntity<APIResponse<Stack>> getStackById(@PathVariable("stackId") Long stackId){
            Stack stack = superAdminService.getStackUsingId(stackId);
            return new ResponseEntity<>(new APIResponse<>(true,"Success", stack), HttpStatus.OK);
    }
    @PutMapping("/update-admin/{adminId}")
    public ResponseEntity<APIResponse<?>> updateAdmin(@RequestBody AdminDto adminDto, @PathVariable("adminId") Long adminId) {
        try {
            APIResponse<Admin> admin = superAdminService.updateAdmin(adminDto, adminId);
            return new ResponseEntity<>(new APIResponse<>(true, "Admin updated successfully", admin), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new APIResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

        @PutMapping("/activate-admin/{adminId}")
        public ResponseEntity<APIResponse<?>> activateAdmin(@PathVariable("adminId") Long adminId) {
            try {
                APIResponse<User> admin = superAdminService.activateAdmin(adminId);
                return new ResponseEntity<>(new APIResponse<>(true, "Admin activated successfully", admin), HttpStatus.CREATED);
            } catch (Exception ex) {
                return new ResponseEntity<>(new APIResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
            }

        }

        @PutMapping("/deactivate-admin/{adminId}")
        public ResponseEntity<APIResponse<?>> deactivateAdmin(@PathVariable("adminId") Long adminId) {
            try {
                APIResponse<User> admin = superAdminService.deactivateAdmin(adminId);
                return new ResponseEntity(new APIResponse<>(true, "Admin deactivated successfully", admin), HttpStatus.CREATED);
            } catch (Exception ex) {
                return new ResponseEntity<>(new APIResponse<>(false, ex.getMessage(), null), HttpStatus.BAD_REQUEST);
            }
        }
}


