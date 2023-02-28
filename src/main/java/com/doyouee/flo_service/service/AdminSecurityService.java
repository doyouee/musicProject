package com.doyouee.flo_service.service;

import com.doyouee.flo_service.entity.AdminEntity;
import com.doyouee.flo_service.repository.AdminRepository;
import com.doyouee.flo_service.security.provider.JwtTokenProvider;
import com.doyouee.flo_service.security.service.CustomUserDetailService;
import com.doyouee.flo_service.utils.AESAlgorithm;
import com.doyouee.flo_service.vo.AdminInfoVO;
import com.doyouee.flo_service.vo.LoginVO;
import com.doyouee.flo_service.vo.admin.response.AdminLoginResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 클래스 내부에 있는 private final 지정된 객체들의 의존성 주입 (@Autowired 따로 안붙여줘도됨 -> Autowired 말고 이 방법을 써야함.)
public class AdminSecurityService {
    private final AdminRepository adminRepository;
    private final AuthenticationManagerBuilder authBuilder;
    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailService userDetailService;
    public AdminLoginResponseVO login(LoginVO login) {
//        login.setPwd(AESAlgorithm.Encrypt(login.getPwd()));
        AdminEntity admin = adminRepository.findByAdminIdAndAdminPwd(login.getId(), login.getPwd());
        if(admin == null) {
            return AdminLoginResponseVO.builder()
                    .status(false)
                    .message("아이디 또는 비밀번호 오류입니다.")
                    .code(HttpStatus.FORBIDDEN)
                    .build();
        }
        else if (!admin.isEnabled()) {
            return AdminLoginResponseVO.builder()
                    .status(false)
                    .message("이용정지된 사용자입니다.")
                    .code(HttpStatus.FORBIDDEN)
                    .build();
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(admin.getAdminId(), admin.getAdminPwd());
        Authentication authentication = authBuilder.getObject().authenticate(authenticationToken);
        return AdminLoginResponseVO.builder()
                .status(true)
                .message("정상 로그인 되었습니다.")
                .token(tokenProvider.generateToken(authentication))
                .code(HttpStatus.OK)
                .build();
    }

    public AdminInfoVO getAdminDetailInfo(String id) {
        try {
            // Exception 발생 시 id에 해당하는 사용자가 시큐리티에 등록되어있지 않은 상태
            userDetailService.loadUserByUsername(id);
            AdminEntity entity = adminRepository.findByAdminId(id);
            AdminInfoVO vo = new AdminInfoVO(entity);
            return vo;
        }
        catch (UsernameNotFoundException e) {
            return null;
        }
    }
}
