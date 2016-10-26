package com.statful.client.framework.springboot.processor;

import com.statful.client.framework.springboot.common.ExportedMetric;
import com.statful.client.framework.springboot.common.ProcessedMetric;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Wrapper class to present processor functionality.
 *
 * Responsible to delegate validation and processing to specialized handlers.
 */
@Component
public class StatfulMetricProcessor {

    @Resource
    private ProcessorMap processorMap;

    /**
     * Validates if there is a processor for a particular exported metric.
     *
     * @param exportedMetric {@link ExportedMetric} Exported metric
     * @return {@link Boolean} True if a processor exist for a particular metric
     */
    public boolean validate(ExportedMetric exportedMetric) {
        return processorMap.validateProcessor(exportedMetric.getName());
    }

    /**
     * Process a particular exported metric.
     *
     * @param exportedMetric {@link ExportedMetric} Exported metric
     * @return {@link ProcessedMetric} Processed metric
     */
    public ProcessedMetric process(ExportedMetric exportedMetric) {
        MetricProcessor metricProcessor = processorMap.getProcessor(exportedMetric.getName());

        return metricProcessor.process(exportedMetric);
    }

    public void setProcessorMap(ProcessorMap processorMap) {
        this.processorMap = processorMap;
    }
}
