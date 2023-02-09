package com.doyouee.flo_service.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doyouee.flo_service.service.ArtistGroupInfoService;
import com.doyouee.flo_service.vo.ArtistGroupInfoVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/artist/group")
@RequiredArgsConstructor
public class ArtistGroupInfoAPIController {

    private final ArtistGroupInfoService agiService;
    
    @PutMapping("/insert")
    public ResponseEntity<Object> putArtistGroupInfo(ArtistGroupInfoVO data, MultipartFile img) {
        Map<String, Object> map = agiService.addArtistGroupInfo(data, img);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
