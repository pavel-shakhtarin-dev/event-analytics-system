package ru.shpg.observability.tracing;

import org.slf4j.MDC;

public class MdcUtils {

    public static final String TRACE_ID = "traceId";

    public static void putTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static void clear() {
        MDC.clear();
    }

}
