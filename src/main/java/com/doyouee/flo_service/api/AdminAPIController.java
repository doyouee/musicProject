package com.doyouee.flo_service.api;

import com.doyouee.flo_service.service.AdminSecurityService;
import com.doyouee.flo_service.vo.AdminInfoVO;
import com.doyouee.flo_service.vo.LoginVO;
import com.doyouee.flo_service.vo.admin.response.AdminLoginResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAPIController {
    private final AdminSecurityService adminSecurityService;
    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponseVO> postAdminLogin(@RequestBody LoginVO login) {
        AdminLoginResponseVO response = adminSecurityService.login(login);
        return new ResponseEntity<>(response, response.getCode());
    }
    @GetMapping("/details/{id}")
    public ResponseEntity<AdminInfoVO> getAdminDetailInfo(@PathVariable String id) {
        return new ResponseEntity<>(adminSecurityService.getAdminDetailInfo(id), HttpStatus.OK);
    }
}
