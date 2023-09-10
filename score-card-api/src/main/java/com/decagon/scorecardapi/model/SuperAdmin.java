package com.decagon.scorecardapi.model;

import com.decagon.scorecardapi.enums.Gender;
import com.decagon.scorecardapi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue(value = "super_admin")
@Entity
public class SuperAdmin extends User{

    @OneToMany
    @JoinColumn(name = "superadmin_squad", referencedColumnName = "id")
    private List<Squad> squad;
    @OneToMany
    @JoinColumn(name = "superadmin_stack", referencedColumnName = "id")
    private List<Stack> stack;
    @OneToMany
    @JoinColumn(name = "superadmin_pod", referencedColumnName = "id")
    private List<Pod> pod;


    public SuperAdmin(String firstName, String lastName, Gender gender, String email, Role role, String password, Boolean isAccountActive) {
//        super();
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setGender(gender);
        this.setEmail(email);
        this.setRole(role);
        this.setPassword(password);
        this.setIsAccountActive(isAccountActive);
    }
}
