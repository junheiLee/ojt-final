package com.ojt_final.office.dto.request;

import com.ojt_final.office.domain.Link;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ToString
@Getter
@NoArgsConstructor
public class CreateLinkRequest {

    private int standardProdCode;
    private List<String> partnerProdCodes;

    public List<Link> toEntities() {

        return partnerProdCodes.stream()
                .map(
                        code -> Link.builder()
                                .partnerProdCode(code)
                                .standardProdCode(standardProdCode)
                                .build())
                .toList();
    }
}
