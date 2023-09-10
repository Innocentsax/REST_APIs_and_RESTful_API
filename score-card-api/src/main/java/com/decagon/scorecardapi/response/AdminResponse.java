package com.decagon.scorecardapi.response;

import com.decagon.scorecardapi.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
    private String firstName;
    private String lastName;
    private String email;
    private List<Squad> squads;
    private List<Stack> stacks;
    private List<Pod> pods;


    public AdminResponse(Admin admin) {
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.email = admin.getEmail();
        this.squads = admin.getSquads();
        this.stacks = admin.getStacks();
        this.pods = admin.getPods();

    }
}
