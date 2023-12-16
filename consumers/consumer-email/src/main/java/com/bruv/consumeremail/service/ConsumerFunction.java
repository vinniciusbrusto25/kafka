package com.bruv.consumeremail.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction {
    void consumir(ConsumerRecord<String, String> record);
}
