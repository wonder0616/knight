package org.jeecg.modules.dbus.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
public class KafkaProducerUtil {
//
//    @Resource
//    private KafkaProducer<String, byte[]> kafkaProducer;
//
//    public void sendMessage(String topic,String message) {
//
//        kafkaProducer.send(new ProducerRecord(topic, message.getBytes(StandardCharsets.UTF_8)));
//    }

}
