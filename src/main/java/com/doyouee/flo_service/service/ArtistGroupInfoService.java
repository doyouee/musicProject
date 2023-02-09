package com.doyouee.flo_service.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.doyouee.flo_service.repository.ArtistGroupInfoRepository;
import com.doyouee.flo_service.vo.ArtistGroupInfoVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistGroupInfoService {
    
    private final ArtistGroupInfoRepository agiRepo;

    public Map<String, Object> addArtistGroupInfo(ArtistGroupInfoVO data, MultipartFile img) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        resultMap.put("data", data);
        resultMap.put("file", img.getOriginalFilename());

        return resultMap;
    }
}
