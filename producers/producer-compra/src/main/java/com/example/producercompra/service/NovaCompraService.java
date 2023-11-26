package com.example.producercompra.service;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class NovaCompraService {
    public void produzirNovaMensagem() {
        
        //ID aleatório necessário p/ testar varios consumers em varias particoes
        String chave = UUID.randomUUID().toString();
        
        //Mensagem a ser enviada. ID do pedido, ID do usuário e o valor da compra.
        String valor = chave + "42,789.89";
        
        //O construtor recebe por generics o tipo da chave e valor, nesse caso, ambos String
        //pq vou mandar a mesma informação na chave e no valor
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties());

        //O que vai na mensagem é sempre um record
        ProducerRecord<String, String> record = new ProducerRecord<String,String>("ECOMMERCE_NOVO_PEDIDO", chave, valor);

        //O send devolve um future, então com o get() dele, a gente "espera" terminar o envio da mensagem.
        //O segundo parâmetro do send recebe uma callback, onde eu posso usar um observer para quando terminar me avisar.
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

        //onde está rodando
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        //Chave - Serializador de String p/ byte
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //Valor - Serializador de String p/ byte
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
