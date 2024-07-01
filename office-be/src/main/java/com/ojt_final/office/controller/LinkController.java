package com.ojt_final.office.controller;

import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.service.LinkChangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/link")
@RestController
public class LinkController {

    private final LinkChangeService linkChangeService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponse createLink(@RequestBody CreateLinkRequest createLinkRequest) {

        log.info(createLinkRequest.toString());
        return linkChangeService.create(createLinkRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public BaseResponse removeLink(@RequestParam(name = "deleteProductCode") List<String> deleteProductCode) {

        log.info(deleteProductCode.toString());
        return linkChangeService.delete(deleteProductCode);
    }
}
