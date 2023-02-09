package com.doyouee.flo_service.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doyouee.flo_service.entity.AdminEntity;
import com.doyouee.flo_service.repository.AdminRepository;
import com.doyouee.flo_service.vo.AdminAddVO;
import com.doyouee.flo_service.vo.AdminInfoVO;
import com.doyouee.flo_service.vo.AdminLoginVO;
import com.doyouee.flo_service.vo.AdminUpdateVO;

@Service
public class AdminService {
    @Autowired AdminRepository adminRepo;
    public Map<String, Object> loginAdmin(AdminLoginVO data) { // 관리자 계정 로그인
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        AdminEntity entity = adminRepo.findByAdminIdAndAdminPwd(data.getAdmin_id(), data.getAdmin_pwd());
        if(entity == null) {
            map.put("status", false);
            map.put("message", "아이디 혹은 비밀번호 오류입니다.");
        }
        else if(entity.getAdminStatus() == 1) {
            map.put("status", false);
            map.put("message", "등록 대기중인 관리자 계정입니다.");
        }
        else if(entity.getAdminStatus() == 3) {
            map.put("status", false);
            map.put("message", "이용 정지 된 관리자 계정입니다.");
        }
        else {
            map.put("status", true);
            map.put("message", "정상 로그인 되었습니다.");
            //AdminInfoVO adminInfo = new AdminInfoVO(entity); // 이렇게 변수 만들지 말고 아래에 바로 데이터 넣기
            map.put("login", new AdminInfoVO(entity));
        }
        
        return map;
    }

    public Map<String, Object> addAdmin(AdminAddVO data) { // 관리자 계정 추가
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        // 입력칸에 빈칸이 있는지 + 중복아이디 중복검사
        if(data.getId() == null || data.getId().equals("")) {
            map.put("status", false);
            map.put("message", "아이디를 입력하세요.");
        }
        else if(adminRepo.countByAdminId(data.getId()) != 0) {
            map.put("status", false);
            map.put("message", data.getId() + "은/는 이미 사용중입니다.");
        }
        else if(data.getPwd() == null || data.getPwd().equals("")) {
            map.put("status", false);
            map.put("message", "비밀번호를 입력하세요.");
        }
        else if(data.getName() == null || data.getName().equals("")) {
            map.put("status", false);
            map.put("message", "이름을 입력하세요.");
        }
        else { // 검사 조건 만족하면 관리자 계정 등록하기
            AdminEntity entity = AdminEntity.builder()
                .adminId(data.getId())
                .adminPwd(data.getPwd())
                .adminName(data.getName())
                .adminGrade(data.getType()).build(); // .adminStatus(1) 는 default 값 설정이 되어있으니 우리가 직접 넣어 줄 필요는 없다.
            adminRepo.save(entity);
            map.put("status", true);
            map.put("message", "관리자 계정 등록 신청 완료");
        }

        return map;
    }


    public Map<String, Object> getAdminList(String keyword, Pageable pageable) {
        Page<AdminEntity> page = adminRepo.findByAdminIdContains(keyword, pageable);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("list", page.getContent());
        map.put("total", page.getTotalElements());
        map.put("totalPage", page.getTotalPages());
        map.put("currentPage", page.getNumber());
        return map;
    }


    public void updateAdminStatus(Integer value, Long admin_no) {
        AdminEntity entity = adminRepo.findById(admin_no).get();
        entity.setAdminStatus(value);
        adminRepo.save(entity);
    }

    public void deleteAdmin(Long admin_no) {
        adminRepo.deleteById(admin_no);
    }

    public AdminEntity getAdminInfo(Long admin_no) {
        return adminRepo.findById(admin_no).get();
    }

    public Map<String, Object> updateAdminInfo(AdminUpdateVO data) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        //유효성 검사
        
        String pwdPattern = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$"; // 특수문자, 공백문자 제외하고 허용
        
        // 찾을 수 없는 관리자 seq 검사
        Optional<AdminEntity> entity = adminRepo.findById(data.getSeq());
        if(entity.isEmpty()) {
            // if(adminRepo.findById(data.getSeq()) == null) {  // 원래 이 코드였는데 위의 두 줄로 인해서 깔끔하게 고쳐짐
            resultMap.put("status", false);
            resultMap.put("message", "잘못된 관리자 정보가 입력되었습니다.");
        }
        // 비밀번호 길이 검사
        else if(data.getPwd().length() > 16 || data.getPwd().length() < 8) {
            resultMap.put("status", false);
            resultMap.put("message", "비밀번호는 8~16자리를 넘을 수 없습니다.");
        }
        // 비밀번호에 공백만 입력했을때 혹은 특수문자 사용시 검사
        else if(data.getPwd().replaceAll(" ", "").length() == 0 || !Pattern.matches(pwdPattern, data.getPwd())) {
            resultMap.put("status", false);
            resultMap.put("message", "비밀번호에 특수문자 또는 공백문자를 사용할 수 없습니다.");
        }
        // 관리자 이름에 공백만 입력했을때 혹은 특수문자 사용시 검사
        else if(data.getName().replaceAll(" ", "").length() == 0 || !Pattern.matches(pwdPattern, data.getName())) {
            resultMap.put("status", false);
            resultMap.put("message", "관리자 이름에 특수문자 또는 공백문자를 사용할 수 없습니다.");
        }
        else {
            AdminEntity adEntity = entity.get();
            adEntity.setAdminPwd(data.getPwd());
            adEntity.setAdminName(data.getName());
            adEntity.setAdminStatus(data.getStatus());
            adEntity.setAdminGrade(data.getGrade());
            adminRepo.save(adEntity);
            resultMap.put("status", true);
        }
        return resultMap;
    }
}
