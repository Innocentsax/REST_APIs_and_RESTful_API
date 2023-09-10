package com.decagon.scorecardapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  Stack extends  BaseClass{
    private String stackName;


   @JsonManagedReference
    @OneToMany(mappedBy = "stack")
    private List<Pod> pods = new ArrayList<>();

    @JsonBackReference
    @OneToMany(mappedBy = "stack")
    private List<Decadev> decadev = new ArrayList<>();

    @JsonBackReference
    @ManyToMany(mappedBy = "stacks")
    private List<Admin> admin = new ArrayList<>();

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Squad squad ;



}
