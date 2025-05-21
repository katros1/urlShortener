package com.katros.urlshortener.repository;

import com.katros.urlshortener.entity.ShortenUrl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ShortenUrlRepositoryTest {

    private final ShortenUrlRepository shortenUrlRepository;

    @Autowired
    public ShortenUrlRepositoryTest(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    @AfterEach
    void tearDown() {
        shortenUrlRepository.deleteAll();
    }

    @Test
    void findByTtlBeforeTest() {

        ShortenUrl shortenUrl = new ShortenUrl();
        shortenUrl.setId("2a9a566c-409e-479c-b8ec-0d13af0fb0dd");
        shortenUrl.setUrl("https://github.com/amigoscode/spring-boot-fullstack-professional/");

        shortenUrlRepository.save(shortenUrl);

        LocalDateTime threshold = LocalDateTime.now();
        List<ShortenUrl> shortenUrls = shortenUrlRepository.findByTtlBefore(threshold);

        assertThat(shortenUrls.size()).isZero();
    }
}
