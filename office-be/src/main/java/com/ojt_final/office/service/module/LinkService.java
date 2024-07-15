package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.LinkDao;
import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.request.DeleteLinkRequest;
import com.ojt_final.office.service.batch.BatchProcessor;
import com.ojt_final.office.service.batch.BatchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ojt_final.office.global.constant.CommonConst.BATCH_SIZE;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LinkService {

    private final LinkDao linkDao;

    @Transactional
    public int saveAll(CreateLinkRequest createLinkRequest) {

        return linkDao.saveAll(createLinkRequest.toEntities());
    }

    public List<Integer> findStandardCodes(List<String> partnerProdCodes) {

        return linkDao.findStandardCodes(partnerProdCodes);
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
