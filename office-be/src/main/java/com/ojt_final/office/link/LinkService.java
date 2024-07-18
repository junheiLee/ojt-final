package com.ojt_final.office.link;

import com.ojt_final.office.link.dto.CreateLinkRequest;
import com.ojt_final.office.link.dto.DeleteLinkRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LinkService {

    private final LinkDao linkDao;

    @Transactional
    public int saveAll(CreateLinkRequest createLinkRequest) {

        return linkDao.insertAll(createLinkRequest.toEntities());
    }

    public List<Integer> findStandardCodes(List<String> partnerProdCodes) {

        return linkDao.selectStandardCodes(partnerProdCodes);
    }

    @Transactional
    public int deleteAll(int standardCode) {

        return linkDao.deleteAllByStandardCode(standardCode);
    }

    @Transactional
    public int deleteAll(DeleteLinkRequest deleteLinkRequest) {

        return linkDao.deleteByPartnerProdCodes(deleteLinkRequest.getPartnerProdCodes());
    }

}
