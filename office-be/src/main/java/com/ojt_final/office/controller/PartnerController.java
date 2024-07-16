package com.ojt_final.office.controller;

import com.ojt_final.office.dto.response.PartnerListResponse;
import com.ojt_final.office.service.module.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/partners")
@RestController
public class PartnerController {

    private final PartnerService partnerService;

    /**
     * 협력사 목록 조회 API
     *
     * @return a {@link PartnerListResponse} containing partner list
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PartnerListResponse getList() {
        return partnerService.getList();
    }
}
