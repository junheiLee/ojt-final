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

    /**
     * Converts CreateLinkRequest to a list of {@link Link} entities.
     *
     * @return a list of {@link Link} entities
     */
    public List<Link> toEntities() {

        return partnerProdCodes.stream()
                .map(this::createLink)
                .toList();
    }

    private Link createLink(String partnerProdCode) {

        return Link.builder()
                .partnerProdCode(partnerProdCode)
                .standardProdCode(standardProdCode)
                .build();
    }
}
