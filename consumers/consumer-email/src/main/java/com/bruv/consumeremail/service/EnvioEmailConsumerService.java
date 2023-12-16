package com.bruv.consumeremail.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * 
 * Consumir mensagens do tópico ECOMMERCE_ENVIO_EMAIL
 * 
 */

public class EnvioEmailConsumerService {

    /**
     * Ação executada a cada consumo
     * 
     * @param record
     */

    private void parse(ConsumerRecord<String, String> record) {
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

    public void enviaEmail() {

        EnvioEmailConsumerService envioEmailService = new EnvioEmailConsumerService();

        //Passo qual o tópico que eu vou me subscrever, e qual a função que eu vou executar para cada mensagem que eu recebo
        KafkaConsumerService kafkaService = new KafkaConsumerService("ECOMMERCE_ENVIO_EMAIL", envioEmailService::parse);

        kafkaService.run();
    }
    
}
