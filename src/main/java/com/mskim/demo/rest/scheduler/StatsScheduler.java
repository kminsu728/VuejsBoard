package com.mskim.demo.rest.scheduler;

import com.mskim.demo.rest.stat.PostByAuthorStat;
import com.mskim.demo.rest.stat.PostByTypeStat;
import com.mskim.demo.rest.stat.StatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsScheduler {

    private final StatSchedulerService statSchedulerService;
    private final StatRepository statRepository;

    @Scheduled(cron = "0 * * * * *")
    public void collectPostStats() {
        log.info("📊 게시글 통계를 수집하여 InfluxDB에 저장 시작!");

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime startTime = now.minusMinutes(1); // 이전 분

        List<PostByTypeStat> typeCounts = statRepository.countPostsByType(startTime, now);
        List<PostByAuthorStat> authorCounts = statRepository.countPostsByAuthor(startTime, now);

        statSchedulerService.savePostByTypeStats(typeCounts, now);
        statSchedulerService.savePostByAuthorStats(authorCounts, now);

        log.info("✅ 게시글 및 사용자 통계 저장 완료!");
    }
}
