package com.bruv.consumerfraude.service;

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
 * Consumir mensagens do tópico ECOMMERCE_NOVO_PEDIDO
 * 
 */

public class DetectarFraudeService {
    
    public void detectarFraude() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties());
        
        //É possível ouvir mensagens de mais de um tópico, no entanto, isso não é comum.
        consumer.subscribe(Collections.singletonList("ECOMMERCE_NOVO_PEDIDO"));

        //Necessário para ficar sempre "escutando" novas mensagens
        while(true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Checagem se tem alguma nova mensagem. Pode devolver vários registros.
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if(!records.isEmpty()) {
                System.out.println("--------------------------------------------------");
                System.out.println("Encontrei " + records.count() + " records");

                for(ConsumerRecord<String, String> record : records) {
                    System.out.println("Processando nova compra, checando se é fraude");
                    System.out.println("key: " + record.key());
                    System.out.println("value: " + record.value());
                    System.out.println("partition: " + record.partition());
                    System.out.println("offset: " + record.offset());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Nova compra processada com sucesso");
                }
            }
        }
    }

    private static Properties properties() {
        Properties properties = new Properties();

        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        //Se faz necessário informar um grupo para que este grupo receba TODAS as mensagens.
        //Qualquer outro consumer é um grupo distinto (email, log, etc...)
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, DetectarFraudeService.class.getSimpleName());

        //Quero consumir no máximo 1 record por vez
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");

        return properties;
    }
}
