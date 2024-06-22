package com.exam.kafka;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.apache.kafka.common.serialization.Deserializer;
import org.eclipse.microprofile.metrics.annotation.Counted;
import com.exam.model.Event;

public class EventDeserializer implements Deserializer<Event> {

    @Override
    @Counted(
        name = "event-consumed-kafka",
        description = "Number of event consumed via kafka"
    )
    public Event deserialize(String topic, byte[] data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            ObjectInputStream objectOutputStream = new ObjectInputStream(inputStream);
            return (Event) objectOutputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
