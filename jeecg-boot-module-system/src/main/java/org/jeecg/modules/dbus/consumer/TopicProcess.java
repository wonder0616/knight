package org.jeecg.modules.dbus.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface TopicProcess {
    void process(ConsumerRecord<String, byte[]> records);
    String getTopic();
}
