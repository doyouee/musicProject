package com.doyouee.flo_service.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.doyouee.flo_service.service.ArtistGroupInfoService;
import com.doyouee.flo_service.vo.ArtistGroupInfoInsertVO;
import com.doyouee.flo_service.vo.ArtistGroupResponseVO;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/api/artist/group")
@RequiredArgsConstructor
public class ArtistGroupInfoAPIController {
    private final ArtistGroupInfoService agiService;

    @Operation(summary = "아티스트 그룹 추가", description="아티스트 그룹을 추가합니다.")
    @PutMapping(value = "/insert", consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    
    public ResponseEntity<Object> putArtistGroupInfo(
            @Parameter(description = "formdata로 데이터를 입력합니다. (name:그룹명, debutYear:데뷔연도, company:기획사 번호)")
        ArtistGroupInfoInsertVO data,
            @Parameter(description = "formdata로 파일 입력합니다.")
        MultipartFile img
    ) {
        Map<String, Object> map = agiService.addArtistGroupInfo(data, img);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ArtistGroupResponseVO> getArtistGroupList(
            @RequestParam @Nullable String keyword,
            @PageableDefault(size = 10, sort = "agiSeq", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return new ResponseEntity<>(agiService.getArtistGroupList(keyword, pageable), HttpStatus.OK);
    }
}
