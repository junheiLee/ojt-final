package com.ojt_final.office.statics;

import com.ojt_final.office.global.search.PartnerProdCond;
import com.ojt_final.office.partnerProd.PartnerProdDao;
import com.ojt_final.office.standard.StandardProdDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StaticsService {

    private StaticsDao staticsDao;
    private PartnerProdDao partnerProdDao;
    private StandardProdDao standardProdDao;

    public void add() {
        PartnerProdCond linked = PartnerProdCond.builder().isLinked(true).build();
        PartnerProdCond unLinked = PartnerProdCond.builder().isLinked(false).build();

        int linkedCount = partnerProdDao.countByCond(linked);
        int unlinkedCount = partnerProdDao.countByCond(unLinked);
        int standardCount = standardProdDao.countAll();


    }
}