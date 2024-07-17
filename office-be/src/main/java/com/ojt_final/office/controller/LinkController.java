package com.ojt_final.office.controller;

import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.service.IntegratedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/link")
@RestController
public class LinkController {

    private final IntegratedService integratedService;

    /**
     * 기준 상품과 파트너 상품들 간 링크를 생성하는 API
     *
     * @param createLinkRequest the request containing link creation details
     * @return a {@link BaseResponse} indicating the result of the operation
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public BaseResponse create(@Valid @RequestBody CreateLinkRequest createLinkRequest) {

        return integratedService.createLink(createLinkRequest);
    }

//    /**
//     * 기준 상품과 파트너 상품들 간 링크를 삭제하는 API
//     *
//     * @param deleteLinkRequest the request containing link removal details
//     * @return a {@link BaseResponse} indicating the result of the operation
//     */
//    @ResponseStatus(HttpStatus.OK)
//    @DeleteMapping
//    public BaseResponse remove(@Valid @RequestBody DeleteLinkRequest deleteLinkRequest) {
//
//        return integratedService.deleteLink(deleteLinkRequest);
//    }

}
