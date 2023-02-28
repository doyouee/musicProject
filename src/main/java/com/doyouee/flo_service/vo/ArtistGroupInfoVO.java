package com.doyouee.flo_service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistGroupInfoVO {
    private String name;
    private Integer debutYear;
    private Long company;
}
