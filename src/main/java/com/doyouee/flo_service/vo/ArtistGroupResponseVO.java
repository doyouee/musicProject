package com.doyouee.flo_service.vo;

import java.util.List;

import com.doyouee.flo_service.entity.ArtistGroupInfoEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtistGroupResponseVO {
    private List<ArtistGroupInfoEntity> list;
    @Schema(description = "총 아티스트 그룹 수", example = "122")
    private Long total;
    @Schema(description = "총 페이지 수", example = "11")
    private Integer totalPage;
    @Schema(description = "조회한 페이지 (1 페이지 -> 0 / 2 페이지 -> 1)", example = "0")
    private Integer currentPage;
}

