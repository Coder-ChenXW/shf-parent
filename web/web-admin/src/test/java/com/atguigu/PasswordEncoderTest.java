package com.atguigu;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

    @Test
    public void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println("encode = " + encode);
        // 密码匹配
        boolean matches = bCryptPasswordEncoder.matches("123456", "$2a$10$sbyD1c3a5mEGH0Ib/XoBQu4cFOkSZuqLzJ0KzSa9ZZ7/X6M60GcCi");
        System.out.println("matches = " + matches);
    }

}
