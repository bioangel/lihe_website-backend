package com.lenovo.smarthr.security.test;

import com.zhp.security.utils.SecurityUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.AssertionErrors;

/**
 * Created by zhh on 16/12/18.
 */
@RunWith(SpringRunner.class)
@Import({SecurityUtils.class})
@TestPropertySource({"classpath:application-security-test.properties"})
@SpringBootTest(classes = {SecurityTest.class})
public class SecurityTest {

    @Autowired
    private SecurityUtils securityUtils;

    @Ignore
    @Test
    public void generateEncryptPassword() {
        String text = "1412";
        System.out.println("==================== Encrypt is [" + securityUtils.encrypt(text) + "]");
    }

    @Test
    @Ignore
    public void encrypt_passwd_should_match_passwd() {
        String passwd = "root123456";
        String encrypt = securityUtils.encrypt(passwd);
        String decrypt = securityUtils.decrypt(encrypt);
        AssertionErrors.assertTrue("failed", passwd.equalsIgnoreCase(decrypt));
    }
}
