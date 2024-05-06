package com.bandall.location_share.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@ActiveProfiles("default")
@SpringBootTest
public class ConfigurationEncryptionTest {

    @Autowired
    private Environment env;

    List<String> dbProperties = List.of("spring.datasource.url", "spring.datasource.username", "spring.datasource.password");
    List<String> redisProperties = List.of("spring.data.redis.host", "spring.data.redis.password");
    List<String> keyProperties = List.of("jwt.secret", "smtp.password", "smtp.username");

    List<String> propertyKeys = Stream.of(dbProperties, redisProperties, keyProperties)
            .flatMap(List::stream)
            .toList();

    @Test
    void encryptYamlConfiguration() {
        for (String key : propertyKeys) {
            String propertyValue = env.getProperty(key);
            String encryptedValue = jasyptEncrypt(propertyValue);
            log.info("{}: ENC({})", key, encryptedValue);
        }
    }

    private String jasyptEncrypt(String input) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        encryptor.setPassword(System.getenv(JasyptConfigAES.JASYPT_ENCRYPTOR_KEY));
        encryptor.setIvGenerator(new RandomIvGenerator());
        return encryptor.encrypt(input);
    }
}
