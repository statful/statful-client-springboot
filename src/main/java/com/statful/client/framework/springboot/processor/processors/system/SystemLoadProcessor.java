package com.statful.client.framework.springboot.processor.processors.system;

import com.statful.client.domain.api.Aggregation;
import com.statful.client.domain.api.AggregationFreq;
import com.statful.client.domain.api.Tags;
import com.statful.client.framework.springboot.common.AggregationDetails;
import com.statful.client.framework.springboot.common.ExportedMetric;
import com.statful.client.framework.springboot.common.MetricType;
import com.statful.client.framework.springboot.common.ProcessedMetric;
import com.statful.client.framework.springboot.processor.MetricProcessor;

public class SystemLoadProcessor implements MetricProcessor {

    @Override
    public ProcessedMetric process(ExportedMetric exportedMetric) {
        // Example: systemload.average=1.11765
        String[] metricSplit = exportedMetric.getName().split("\\.");

        return new ProcessedMetric.Builder().withName(SYSTEM_METRICS_PREFIX + metricSplit[0])
                .withMetricType(MetricType.GAUGE)
                .withValue(exportedMetric.getValue().doubleValue())
                .withTimestamp(exportedMetric.getTimestamp().getTime())
                .aggregatedBy(new AggregationDetails(Aggregation.AVG, AggregationFreq.FREQ_60))
                .build();
    }
}
