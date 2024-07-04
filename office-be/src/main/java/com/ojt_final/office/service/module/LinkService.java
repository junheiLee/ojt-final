package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.LinkDao;
import com.ojt_final.office.dto.request.CreateLinkRequest;
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
    public int create(CreateLinkRequest createLinkRequest) {

        return linkDao.saveAll(createLinkRequest.toEntities());
    }

    public List<Integer> findAllByProdCodes(List<String> partnerProdCodes) {

        return linkDao.findAllByPartnerProdCodes(partnerProdCodes);
    }

    @Transactional
    public int delete(List<String> partnerProdCodes) {

        return linkDao.deleteAll(partnerProdCodes);
    }

}
