package com.statful.client.framework.springboot.processor.processors.system;

import com.statful.client.framework.springboot.common.ExportedMetric;
import com.statful.client.framework.springboot.common.MetricType;
import com.statful.client.framework.springboot.common.ProcessedMetric;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static com.statful.client.framework.springboot.processor.MetricProcessor.SYSTEM_METRICS_PREFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UptimeProcessorTest {

    private UptimeProcessor subject;

    @Before
    public void before() {
        subject = new UptimeProcessor();
    }

    @Test
    public void shouldProcessMetricWithoutType() {
        // Given
        ExportedMetric exportedMetric = new ExportedMetric.Builder()
                .withName("uptime")
                .withTimestamp(Date.from(Instant.EPOCH))
                .withValue(1D)
                .build();

        // When
        ProcessedMetric processedMetric = subject.process(exportedMetric);

        // Then
        assertEquals(SYSTEM_METRICS_PREFIX + "uptime", processedMetric.getName());
        assertEquals(MetricType.GAUGE, processedMetric.getMetricType());
        assertEquals(Double.valueOf(1D), processedMetric.getValue());
        assertEquals(Instant.EPOCH.toEpochMilli(), processedMetric.getTimestamp());
        assertEquals("vm", processedMetric.getTags().get().getTagValue("type"));
        assertFalse(processedMetric.getAggregations().isPresent());
        assertFalse(processedMetric.getAggregationDetails().isPresent());
    }

    @Test
    public void shouldProcessMetricWithType() {
        // Given
        ExportedMetric exportedMetric = new ExportedMetric.Builder()
                .withName("instance.uptime")
                .withTimestamp(Date.from(Instant.EPOCH))
                .withValue(1D)
                .build();

        // When
        ProcessedMetric processedMetric = subject.process(exportedMetric);

        // Then
        assertEquals(SYSTEM_METRICS_PREFIX + "uptime", processedMetric.getName());
        assertEquals(MetricType.GAUGE, processedMetric.getMetricType());
        assertEquals(Double.valueOf(1D), processedMetric.getValue());
        assertEquals(Instant.EPOCH.toEpochMilli(), processedMetric.getTimestamp());
        assertEquals("instance", processedMetric.getTags().get().getTagValue("type"));
        assertFalse(processedMetric.getAggregations().isPresent());
        assertFalse(processedMetric.getAggregationDetails().isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfInvalidMetric() {
        // Given
        ExportedMetric exportedMetric = new ExportedMetric.Builder()
                .withName("this.is.an.invalid.metric.for.sure")
                .build();

        // When
        subject.process(exportedMetric);
    }
}
