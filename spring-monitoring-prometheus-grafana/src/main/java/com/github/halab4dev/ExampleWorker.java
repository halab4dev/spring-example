package com.github.halab4dev;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author halab
 */
@Component
@AllArgsConstructor
public class ExampleWorker {

    private final ExampleQueue exampleQueue;

    @Scheduled(cron = "0/5 * * * * *")
    public void add() {
        double randomNumber = Math.random();
        exampleQueue.add(String.valueOf(randomNumber));
        System.out.println("Add. Size: " + exampleQueue.size());
    }

    @Scheduled(cron = "0/6 * * * * *")
    public void remove() {
        exampleQueue.poll();
        System.out.println("Poll. Size: " + exampleQueue.size());
    }
}
