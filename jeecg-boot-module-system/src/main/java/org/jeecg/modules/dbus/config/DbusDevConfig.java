package org.jeecg.modules.dbus.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
@Slf4j
public class DbusDevConfig {

//    @Bean
//    public KafkaProducer<String, byte[]> kafkaProducer( KafkaProperties kafkaProperties){
//
//        return new KafkaProducer<String,byte[]>(kafkaProperties.buildProducerProperties());
//    }
//
//    @Bean
//    public KafkaConsumer<String, byte[]>  kafkaConsumer( KafkaProperties kafkaProperties){
//
//        return   new KafkaConsumer<String,byte[]>(kafkaProperties.buildConsumerProperties());
//    }



}
