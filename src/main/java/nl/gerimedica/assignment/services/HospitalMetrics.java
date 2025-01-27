package nl.gerimedica.assignment.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * For metrics let's use micrometer + prometheus
 */
@Slf4j
@Component
public class HospitalMetrics {
    @Autowired
    private MeterRegistry registry;

    /**
     * Attention!
     * Make sure the context doesn't have a lot of unique values
     */
    public void recordUsage(String context) {
        Counter.builder("appointments.usage")
                .tag("context", context)
                .register(registry);
    }
}
