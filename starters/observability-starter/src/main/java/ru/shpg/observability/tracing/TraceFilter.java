package ru.shpg.observability.tracing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class TraceFilter extends OncePerRequestFilter {

    private static final String HEADER = "X-Trace-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String traceId = request.getHeader(HEADER);

        if (traceId == null) {
            traceId = UUID.randomUUID().toString();
        }

        MdcUtils.putTraceId(traceId);

        try {
            response.setHeader(HEADER, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MdcUtils.clear();
        }
    }

}
