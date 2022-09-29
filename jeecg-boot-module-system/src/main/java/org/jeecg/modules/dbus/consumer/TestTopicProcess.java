package org.jeecg.modules.dbus.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component()
@Slf4j
public class TestTopicProcess implements  TopicProcess{

   @Override
    public void process(ConsumerRecord<String, byte[]> record) {
       String value = new String((byte[])record.value(), StandardCharsets.UTF_8);
       log.info("recode message :{}",value);
       log.error("recode message :{}",value);

    }



    @Override
    public String getTopic() {

        return "test";
    }
}
