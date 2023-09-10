package com.decagon.scorecardapi.dto.requestdto;

import com.decagon.scorecardapi.enums.AssignRole;
import com.decagon.scorecardapi.enums.Gender;
import com.decagon.scorecardapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDto {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private AssignRole assignRole;
    private Long squadId;
    private Long stackId;
    private Long podId;
    private Role role;
}