package com.exam.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EventEntity extends PanacheEntity {

    private String key;
    private String value;

    public static EventEntity findByKey(String key) {
        return find("key", key).firstResult();
    }

    public static long countByKey(String key) {
        return find("key", key).count();
    }

    public static void deleteByKey(String key) {
        delete("key", key);
    }

}
