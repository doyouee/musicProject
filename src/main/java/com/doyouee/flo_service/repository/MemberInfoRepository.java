package com.doyouee.flo_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.doyouee.flo_service.entity.MemberInfoEntity;

public interface MemberInfoRepository extends JpaRepository<MemberInfoEntity, Integer> {
    public MemberInfoEntity findByMiIdAndMiPwd(String miId, String miPwd);
    public Integer countByMiId(String miId);
}
