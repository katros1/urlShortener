package com.katros.urlshortener.controller;

import com.katros.urlshortener.service.ShortenUrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ShortenUrlController.class)
class ShortenUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ShortenUrlService shortenUrlService;

    @Test
    void shortenUrl() {

    }

    @Test
    void redirectToOriginal() {
    }

    @Test
    void testGetPaginatedUrls() {

    }

    @Test
    void deleteShortUrl() {
    }
}