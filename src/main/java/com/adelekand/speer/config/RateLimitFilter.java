package com.adelekand.speer.config;
import com.adelekand.speer.exception.TooManyRequestsException;
import com.adelekand.speer.utils.HttpRequestUtil;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final HttpRequestUtil httpRequestUtil;

    @Value("${bucket4j.limits.capacity}")
    private Integer capacity;

    @Value("${bucket4j.limits.tokens}")
    private Integer tokens;

    @Value("${bucket4j.limits.duration}")
    private Integer duration;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String ipAddress = httpRequestUtil.getIpAddress(request);
        log.info("ip address: {}", ipAddress);
        Bucket bucket = getBucket(ipAddress);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                throw new TooManyRequestsException("API Request Limit Exhausted");
            } catch (TooManyRequestsException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Bucket getBucket(String ipAddress) {
        return buckets.computeIfAbsent(ipAddress, k ->
                Bucket.builder()
                        .addLimit(Bandwidth.classic(capacity, Refill.intervally(tokens, Duration.ofMinutes(duration))))
                        .build()
        );
    }
}
