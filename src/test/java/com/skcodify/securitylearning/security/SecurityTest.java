package com.skcodify.securitylearning.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class SecurityTest {

    @Test
    public void test(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(16);
        String result = passwordEncoder.encode("mypassword");
        System.out.println(result);
        assert(passwordEncoder.matches("mypassword", result));
    }
}
