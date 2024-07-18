package com.ojt_final.office.company;

import com.ojt_final.office.company.dto.PartnersResponse;
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
     * @return a {@link PartnersResponse} containing partner list
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PartnersResponse getList() {
        return partnerService.getList();
    }
}
