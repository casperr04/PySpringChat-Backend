package com.casperr04.pyspringchatbackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisabledIfEnvironmentVariable(named = "CI", matches = "true")
class PySpringChatBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
