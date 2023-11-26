package com.bruv.consumeremail.service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * 
 * Consumir mensagens do tópico ECOMMERCE_ENVIO_EMAIL
 * 
 */

public class EnvioEmailService {

    public void enviaEmail() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties());
        
        consumer.subscribe(Collections.singletonList("ECOMMERCE_ENVIO_EMAIL"));

        while(true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()) {
                System.out.println("--------------------------------------------------");
                System.out.println("Encontrei " + records.count() + " records");

                for(ConsumerRecord<String, String> record : records) {
                    System.out.println("Enviando email");
                    System.out.println("key: " + record.key());
                    System.out.println("value: " + record.value());
                    System.out.println("partition: " + record.partition());
                    System.out.println("offset: " + record.offset());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Email enviado");
                }
            }
        }
    }

    private static Properties properties() {
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EnvioEmailService.class.getSimpleName());

        return properties;
    }
    
}
