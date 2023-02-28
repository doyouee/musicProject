package com.doyouee.flo_service.security.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "permission")
@Data
public class PermitSettings { // 임의로 만듦 -> 
    String[] permitAllUrls;
}
