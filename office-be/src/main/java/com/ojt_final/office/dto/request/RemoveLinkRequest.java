package com.ojt_final.office.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class RemoveLinkRequest {

    private List<String> partnerProdCodes;

}
