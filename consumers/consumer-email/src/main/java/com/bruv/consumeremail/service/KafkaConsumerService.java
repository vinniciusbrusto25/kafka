package com.bruv.consumeremail.service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaConsumerService {

    private final KafkaConsumer<String, String> consumer;
    private final ConsumerFunction parse;

    public KafkaConsumerService(String topico, ConsumerFunction parse) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<String, String>(properties());
        consumer.subscribe(Collections.singletonList(topico));
    }

    public void run() {
        while(true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()) {
                System.out.println("--------------------------------------------------");
                //Como a config. max poll está 1, então sempre no máximo vai encontrar por vez.
                System.out.println("Encontrei " + records.count() + " records");

                for(ConsumerRecord<String, String> record : records) {
                    this.parse.consumir(record);
                }
            }
        }
    }

    private static Properties properties() {
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EnvioEmailConsumerService.class.getSimpleName());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return properties;
    }
    
}
