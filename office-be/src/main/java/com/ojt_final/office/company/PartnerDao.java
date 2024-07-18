package com.ojt_final.office.company;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PartnerDao {

    /**
     * 협력사 목록 조회
     *
     * @return 협력사 코드, 이름 목록
     */
    List<Partner> selectAll();
}
