package com.doyouee.flo_service.security.service;

//import com.example.security_test.mapper.MemberMapper;
import com.doyouee.flo_service.repository.AdminRepository;
//import com.example.security_test.vo.entity.MemberInfoVO;
import com.doyouee.flo_service.entity.AdminEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService { // 서버 내부에 만들어져있는 유저 정보를 안쪽에 저장하는 메서드를 오버라이드해서 자동실행
//    private final MemberMapper memberMapper;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return createUserDetails(memberMapper.getMemberInfoByMemberId(username));
        return createUserDetails(adminRepository.findByAdminId(username));
    }
    public UserDetails createUserDetails(AdminEntity member) {
        return User.builder().username(member.getAdminId())
                .password(passwordEncoder.encode(member.getAdminPwd()))
                .roles(member.getAdminRole())
                .build();
    }
}

