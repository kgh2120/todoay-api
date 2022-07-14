package com.todoay.api.domain.auth.controller;

import com.todoay.api.domain.auth.exception.SampleApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {


    @GetMapping("/sample")
    public String sampleApiException() {
        throw new SampleApiException();
    }

    @PostMapping("/invalid")
    public ResponseEntity<SampleArgumentErrorDto> sampleArgumentError(@RequestBody @Validated SampleArgumentErrorDto dto) {
        return ResponseEntity.ok(dto);
    }
}
