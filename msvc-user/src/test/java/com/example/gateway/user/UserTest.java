package com.example.gateway.user;

import com.example.gateway.user.utils.ValidationRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserTest {
    @Autowired
    ValidationRule rules;

    @Test
    public void testIsUsernameValid() {
        assertTrue(rules.isUserNameValid("username123"));
        /*assertFalse(rules.isUserNameValid(null));
        assertFalse(rules.isUserNameValid(""));
        assertFalse(rules.isUserNameValid(" "));*/
    }
}
