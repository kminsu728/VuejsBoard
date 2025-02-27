package com.mskim.demo.rest.scheduler;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.mskim.demo.base.config.InfluxConfiguration;
import com.mskim.demo.rest.stat.PostByAuthorStat;
import com.mskim.demo.rest.stat.PostByTypeStat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatSchedulerService {


    private final InfluxDBClient influxDBClient;
    private final InfluxConfiguration influxConfiguration;

    public void savePostByTypeStats(List<PostByTypeStat> typeCounts, LocalDateTime time) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        List<Point> points = typeCounts.stream()
                .map(tc -> Point.measurement("post_by_type")
                        .addTag("type", tc.get_id())
                        .addField("count", tc.getCount())
                        .time(time.atZone(ZoneOffset.ofHours(9)).toInstant(), WritePrecision.NS))
                .collect(Collectors.toList());

        writeApi.writePoints(influxConfiguration.getBucket(), influxConfiguration.getOrg(), points);
    }

    public void savePostByAuthorStats(List<PostByAuthorStat> authorCounts, LocalDateTime time) {
        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();

        List<Point> points = authorCounts.stream()
                .map(ac -> Point.measurement("post_by_author")
                        .addTag("author", ac.get_id())
                        .addField("count", ac.getCount())
                        .time(time.atZone(ZoneOffset.ofHours(9)).toInstant(), WritePrecision.NS))
                .collect(Collectors.toList());

        writeApi.writePoints(influxConfiguration.getBucket(), influxConfiguration.getOrg(), points);
    }
}
