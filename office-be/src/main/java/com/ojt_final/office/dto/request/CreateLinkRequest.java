package com.ojt_final.office.dto.request;

import com.ojt_final.office.domain.Link;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "EMPTY")
    @Positive(message = "RANGE_OVER")
    private int standardProdCode;

    @NotEmpty(message = "EMPTY")
    private List<
            @NotEmpty(message = "EMPTY")
            @Size(max = 50, message = "SIZE_OVER_50")
                    String> partnerProdCodes;

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
