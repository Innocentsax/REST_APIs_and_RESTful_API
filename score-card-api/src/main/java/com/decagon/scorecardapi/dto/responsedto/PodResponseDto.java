package com.decagon.scorecardapi.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PodResponseDto {
   private String podName;
   private String podHealth;
   private int totalDecadevs;
   private String SA;
   private String Pa;
}
