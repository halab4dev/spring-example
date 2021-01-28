package com.github.halab4dev;

import javax.annotation.PostConstruct;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author halab
 */
@Component
@AllArgsConstructor
public class ExampleQueue {

    private MeterRegistry registry;
    private final Queue<String> QUEUE = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void initMetric() {
        registry.gaugeCollectionSize("example_queue_size", Tags.empty(), QUEUE);
    }

    public void add(String string) {
        QUEUE.add(string);
    }

    public String poll() {
        return QUEUE.poll();
    }

    public int size() {
        return QUEUE.size();
    }
}
