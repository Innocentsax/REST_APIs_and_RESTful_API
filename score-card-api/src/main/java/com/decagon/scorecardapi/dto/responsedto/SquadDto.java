package com.decagon.scorecardapi.dto.responsedto;

import com.decagon.scorecardapi.enums.Gender;
import com.decagon.scorecardapi.enums.Role;
import com.decagon.scorecardapi.model.Stack;
import lombok.*;

import java.util.List;

@Data
public class SquadDto {
    private String squadName;
    private List<String> stackNames;
}

