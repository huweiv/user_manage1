package com.huweiv.util;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName BCryptPasswordEncoderConfig
 * @Description TODO
 * @CreateTime 2022/4/15 8:56
 */

@Component
public class BCryptPasswordEncoderConfig {

    @Bean("bCryptPasswordEncoder")
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
