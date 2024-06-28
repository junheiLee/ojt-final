package com.ojt_final.office.service.module;

import com.ojt_final.office.dao.LinkDao;
import com.ojt_final.office.dto.request.CreateLinkRequest;
import com.ojt_final.office.dto.response.BaseResponse;
import com.ojt_final.office.dto.response.constant.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LinkService {

    private final LinkDao linkDao;

    @Transactional
    public BaseResponse create(CreateLinkRequest createLinkRequest) {

        int row = linkDao.saveAll(createLinkRequest.toEntity());
        // partnerProduct link 여부 업데이트
        // 존재 확인 후, update, insert 따로 진행할 지 고민 필요
        return 0 <= row ? new BaseResponse(ResultCode.FAILED) : new BaseResponse(ResultCode.SUCCESS);
    }

}
