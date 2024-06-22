package com.exam.kafka;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import org.apache.kafka.common.serialization.Serializer;
import org.eclipse.microprofile.metrics.annotation.Counted;
import com.exam.model.Event;

public class EventSerializer implements Serializer<Event> {

    @Override
    @Counted(
        name = "event-produced-kafka",
        description = "Number of event produced kafka"
    )
    public byte[] serialize(String topic, Event data) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(data);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
