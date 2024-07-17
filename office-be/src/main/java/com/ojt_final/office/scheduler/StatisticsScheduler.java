package com.ojt_final.office.scheduler;

import com.ojt_final.office.service.module.StaticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticsScheduler {

    private StaticsService staticsService;
    private String hi = "";

    // 매일 23시 55분 통계 갱신
    @Scheduled(cron = "0 55 23 * * *")
    public void run() {
        staticsService.add();
    }
}
