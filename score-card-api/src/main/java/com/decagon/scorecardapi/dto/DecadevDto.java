package com.decagon.scorecardapi.dto;
import com.decagon.scorecardapi.enums.Gender;
import com.decagon.scorecardapi.enums.Role;
import com.decagon.scorecardapi.model.Decadev;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecadevDto {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String decadevId;
    private Role role;


    public static DecadevDto getDecadevFromAPodDto(Decadev decadev) {
        DecadevDto decadevDto = new DecadevDto();
        decadevDto.setFirstName(decadev.getFirstName());
        decadevDto.setLastName(decadev.getLastName());
        decadevDto.setGender(decadev.getGender());
        decadevDto.setEmail(decadev.getEmail());
        decadevDto.setDecadevId(decadev.getDecadevId());
        decadevDto.setRole(decadev.getRole());
        return decadevDto;
    }

}
