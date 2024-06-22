package com.exam;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import com.exam.model.Event;
import io.quarkus.arc.properties.IfBuildProperty;
import io.reactivex.rxjava3.core.Flowable;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@IfBuildProperty(name = "event.generator.enabled", stringValue = "true", enableIfMissing = true)
public class EventGenerator {

    private Random random = new Random();

    @Outgoing("events")
    @Counted(name = "event-produced-generator-kafka",
            description = "Number of event produced via generator kafka")
    public Flowable<Record<String, Event>> generate() {
        return Flowable.interval(10, TimeUnit.SECONDS).map(tick -> {
            Event event = Event.builder().key(UUID.randomUUID().toString())
                    .value(String.valueOf(random.nextDouble())).build();
            log.info("Event generated! {}", event);
            return Record.of(UUID.randomUUID().toString(), event);
        });
    }
}
