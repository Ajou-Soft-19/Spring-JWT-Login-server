package com.bandall.location_share.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class EnvTest {

    @Test
    void printSystemEnvironment() {
        System.getenv().forEach((key, value) -> log.info("{}: {}", key, value));
    }
}
