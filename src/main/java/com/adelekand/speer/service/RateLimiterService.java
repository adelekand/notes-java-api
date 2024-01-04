//package com.adelekand.speer.service;
//
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Refill;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Service
//public class RateLimiterService {
//    Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
//
//    @Value("${bucket4j.limits.tokens}")
//    private Integer tokens;
//
//    @Value("${bucket4j.limits.capacity}")
//    private Integer capacity;
//
//    @Value("${bucket4j.limits.duration}")
//    private Integer duration;
//
//    public Bucket resolveBucket(String ipAddress) {
//
//        log.info("bucketCache size = {}", bucketCache.size());
//
//        return bucketCache.computeIfAbsent(ipAddress, this::newBucket);
//    }
//
//    private Bucket newBucket(String s) {
//
//        Refill refill = Refill.intervally(tokens, Duration.ofSeconds(duration));
//        return Bucket.builder().addLimit(Bandwidth.classic(capacity, refill)).build();
//    }
//}
