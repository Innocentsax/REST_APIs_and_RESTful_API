package com.decagon.scorecardapi.dto;

import com.decagon.scorecardapi.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodResponseDto {
    private  String podName;
    private List<Admin> admin = new ArrayList<>();
}
