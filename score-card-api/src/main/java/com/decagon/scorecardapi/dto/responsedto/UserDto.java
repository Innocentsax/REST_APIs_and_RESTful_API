package com.decagon.scorecardapi.dto.responsedto;

import com.decagon.scorecardapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;

}
