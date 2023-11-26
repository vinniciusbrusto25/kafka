package com.example.produceremail.service;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class EnvioEmailService {

    public void produzirNovaMensagem() {
        
        String chave = UUID.randomUUID().toString();

        String email = "Obrigado! Sua compra está sendo processada.";

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());

        ProducerRecord<String, String> record = new ProducerRecord<String,String>("ECOMMERCE_ENVIO_EMAIL", chave, email);

        try {
            producer.send(record, (dadosSucesso, ex) -> {
                if(ex != null) {
                    ex.printStackTrace();
                    return;
                }

                System.out.println("sucesso enviando " + dadosSucesso.topic() + ":::partition " + dadosSucesso.partition() +
                    "/ offset " + dadosSucesso.offset() + "/ timestamp " + dadosSucesso.timestamp());
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Properties properties() {
        Properties properties = new Properties();

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
