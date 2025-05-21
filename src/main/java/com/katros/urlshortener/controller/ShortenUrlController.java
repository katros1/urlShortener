package com.katros.urlshortener.controller;

import com.katros.urlshortener.dtos.PaginatedResponseDto;
import com.katros.urlshortener.dtos.ShortenUrlRequestDto;
import com.katros.urlshortener.dtos.ShortenUrlResponseDto;
import com.katros.urlshortener.entity.ShortenUrl;
import com.katros.urlshortener.service.ShortenUrlService;
import com.katros.urlshortener.util.CustomResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping
public class ShortenUrlController {

    private static final Logger logger = LoggerFactory.getLogger(ShortenUrlController.class);

    private final ShortenUrlService shortenUrlService;

    @Autowired
    public ShortenUrlController(ShortenUrlService shortenUrlService) {
        this.shortenUrlService = shortenUrlService;
    }

    @PostMapping("/api/v1/shorten-url")
    public ResponseEntity<CustomResponse<ShortenUrlResponseDto>> shortenUrl(@RequestParam(required = false) Integer ttl,
                                                                            @RequestBody @Valid ShortenUrlRequestDto longUrl) {

        ShortenUrlResponseDto shortUrl = shortenUrlService.createShortUrl(longUrl, ttl);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
                CustomResponse.successResponse("Short URL created successfully",
                        HttpStatus.CREATED.value(), shortUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> redirectToOriginal(@PathVariable String id) {
        ShortenUrl url = shortenUrlService.getShortUrl(id);

        logger.info("Redirecting to: {}", url.getUrl());
        return ResponseEntity.status(302).location(URI.create(url.getUrl())).build();
    }

    @GetMapping("/api/v1/shorten-url")
    public ResponseEntity<CustomResponse<PaginatedResponseDto<ShortenUrl>>> getPaginatedUrls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<ShortenUrl> shortenUrlsPage = shortenUrlService.getAllShortenUrls(pageable);

        List<ShortenUrl> shortenUrls = shortenUrlsPage.getContent();

        PaginatedResponseDto<ShortenUrl> retrievedShortenUrls = new PaginatedResponseDto<>(shortenUrls, page, shortenUrlsPage.getNumberOfElements(), shortenUrlsPage.getTotalElements(), shortenUrlsPage.getTotalPages());

        return ResponseEntity.status(HttpStatus.OK).body(CustomResponse.successResponse("Shorten-urls fetched successfully", HttpStatus.OK.value(), retrievedShortenUrls));
    }

    @DeleteMapping("/api/v1/shorten-url/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteShortUrl(@PathVariable String id) {
        shortenUrlService.deleteShortUrl(id);
        return ResponseEntity.ok(CustomResponse.successResponse("Shorten url deleted successfully", HttpStatus.OK.value()));
    }
}
