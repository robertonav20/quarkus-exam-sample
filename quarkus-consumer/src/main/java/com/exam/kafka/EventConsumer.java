package com.exam.kafka;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.infinispan.client.hotrod.RemoteCache;
import com.exam.model.Event;
import com.exam.model.EventEntity;
import io.quarkus.infinispan.client.Remote;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class EventConsumer {

    @Remote("${cache.name}")
    private RemoteCache<String, String> cache;

    @Incoming("events")
    @Transactional
    public void process(Event event) {
        log.info("Event received! {}", event);
        cache.put(event.getKey(), event.getValue());

        EventEntity entity = new EventEntity();
        entity.setKey(event.getKey());
        entity.setValue(event.getValue());
        entity.persist();
    }
}
