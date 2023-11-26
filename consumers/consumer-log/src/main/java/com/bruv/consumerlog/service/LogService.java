package com.bruv.consumerlog.service;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * 
 * Consumir mensagens de todos os tópicos que começam com o termo "ECOMMERCE"
 * utilizando expressões regulares.
 * 
 * Nesse caso, são apenas 2 os tópicos:
 * 
 * ECOMMERCE_NOVO_PEDIDO
 * ECOMMERCE_ENVIO_EMAIL
 * 
 */

public class LogService {

    public void geraLog() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties());
        
        consumer.subscribe(Pattern.compile("ECOMMERCE.*"));

        while(true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()) {
                System.out.println("--------------------------------------------------");
                System.out.println("Encontrei " + records.count() + " records");

                for(ConsumerRecord<String, String> record : records) {
                    System.out.println("LOG: " + record.topic());
                    System.out.println("key: " + record.key());
                    System.out.println("value: " + record.value());
                    System.out.println("partition: " + record.partition());
                    System.out.println("offset: " + record.offset());
                }
            }
        }
    }
    

    private static Properties properties() {
        Properties properties = new Properties();
    
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());
    
        return properties;
    }
}