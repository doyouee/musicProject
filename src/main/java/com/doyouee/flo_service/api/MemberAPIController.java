package com.doyouee.flo_service.api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doyouee.flo_service.entity.MemberInfoEntity;
import com.doyouee.flo_service.repository.MemberInfoRepository;
import com.doyouee.flo_service.vo.LoginVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor // @Autowired 자동으로 해줌
public class MemberAPIController { // 얘는 서비스 없이 바로 한대

    private final MemberInfoRepository MemberInfoRepo;

    @PostMapping("/login")
    public Map<String, Object> postMemberLogin(@RequestBody LoginVO login) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

        MemberInfoEntity entity = MemberInfoRepo.findByMiIdAndMiPwd(login.getId(), login.getPwd());
        if(entity == null) {
            resultMap.put("status", false);
            resultMap.put("message", "잘못된 로그인 정보입니다.");
        }
        else {
            resultMap.put("status", true);
            resultMap.put("user", entity);
        }

        return resultMap;
    }
}
