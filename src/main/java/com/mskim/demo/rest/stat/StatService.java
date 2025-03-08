package com.mskim.demo.rest.stat;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.mskim.demo.base.config.InfluxConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatService {

//    private final InfluxDBClient influxDBClient;
//    private final InfluxConfiguration influxConfiguration;

    public List<PostByTypeStat> getPostByTypeStat() {
//        String fluxQuery = "from(bucket: \"" + influxConfiguration.getBucket() + "\") " +
//                "|> range(start: -1h, stop: now()) " + // 최근 1시간 데이터 조회
//                "|> filter(fn: (r) => r._measurement == \"post_by_type\") " + // post_by_type 측정항목 필터링
//                "|> aggregateWindow(every: 10m, fn: sum, createEmpty: false) " + // 10분 단위로 그룹화 및 합산
//                "|> group(columns: [\"_time\", \"type\"]) " + // 시간과 타입별로 그룹화
//                "|> sort(columns: [\"_time\", \"type\"], desc: false)"; // 정렬
//
//        QueryApi queryApi = influxDBClient.getQueryApi();
//        List<FluxTable> tables = queryApi.query(fluxQuery, influxConfiguration.getOrg());

        List<PostByTypeStat> postByTypeStats = new ArrayList<>();

//        for (FluxTable table : tables) {
//            for (FluxRecord record : table.getRecords()) {
//                PostByTypeStat postByTypeStat = PostByTypeStat.builder()
//                        ._id((String) record.getValueByKey("type"))
//                        .count((long) record.getValueByKey("_value"))
//                        .time(record.getTime())
//                        .build();
//
//                postByTypeStats.add(postByTypeStat);
//            }
//        }

        return postByTypeStats;
    }

    public List<PostByAuthorStat> getPostByAuthorStat() {
//        String fluxQuery = "from(bucket: \"" + influxConfiguration.getBucket() + "\") " +
//                "|> range(start: -1h, stop: now()) " + // 최근 1시간 데이터 조회
//                "|> filter(fn: (r) => r._measurement == \"post_by_author\") " + // post_by_type 측정항목 필터링
//                "|> aggregateWindow(every: 10m, fn: sum, createEmpty: false) " + // 10분 단위로 그룹화 및 합산
//                "|> group(columns: [\"_time\", \"author\"]) " + // 시간과 타입별로 그룹화
//                "|> sort(columns: [\"_time\", \"author\"], desc: false)"; // 정렬
//
//        QueryApi queryApi = influxDBClient.getQueryApi();
//        List<FluxTable> tables = queryApi.query(fluxQuery, influxConfiguration.getOrg());

        List<PostByAuthorStat> postByAuthorStats = new ArrayList<>();

//        for (FluxTable table : tables) {
//            for (FluxRecord record : table.getRecords()) {
//                PostByAuthorStat postByAuthorStat = PostByAuthorStat.builder()
//                        ._id((String) record.getValueByKey("author"))
//                        .count((long) record.getValueByKey("_value"))
//                        .time(record.getTime())
//                        .build();
//
//                postByAuthorStats.add(postByAuthorStat);
//            }
//        }

        return postByAuthorStats;
    }
}
