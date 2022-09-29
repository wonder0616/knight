/*
package org.jeecg.modules.dbus.consumer;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class KafkaConsumerUtil {

    @Resource
    private List<TopicProcess> topicProcesses;

    @Resource
    private KafkaConsumer<String, byte[]> kafkaConsumer;

    private Map<String, TopicProcess> topicProcessMap = new HashMap<>();

    @PostConstruct
    public void init() {
        Executors.newSingleThreadExecutor().submit(() -> {
            Thread.currentThread().setName("operate-dbus-thread");
            List<String> currentTopicList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(topicProcesses)) {
                currentTopicList.clear();
                List<String> topicList = new ArrayList<>();
                for (TopicProcess topicProcess : topicProcesses) {
                    String topic = topicProcess.getTopic();
                    if (StringUtils.isNotEmpty(topic)) {
                        if (topic.contains(",")) {
                            topicList.addAll(Arrays.asList(topic.split(",")));
                            currentTopicList.addAll(Arrays.asList(topic.split(",")));
                        } else {
                            topicList.add(topic);
                            currentTopicList.add(topic);
                        }
                    }
                    currentTopicList.forEach(topicTmp -> topicProcessMap.put(topicTmp, topicProcess));
                }
                kafkaConsumer.subscribe(topicList);
                consume(kafkaConsumer);
            }
        });
    }

    public void consume(KafkaConsumer<String, byte[]> kafkaConsumer) {
        while (true) {
            try {
                ConsumerRecords<String, byte[]> records = kafkaConsumer.poll(Duration.ofSeconds(60000L));
                for (ConsumerRecord<String, byte[]> record : records) {
                    String topic = record.topic();
                    topicProcessMap.get(topic).process(record);
                }
                kafkaConsumer.commitAsync();
            } catch (Exception e) {
                log.error("consume msg fail: {}", e);
            } finally {
                try {
                    kafkaConsumer.commitSync();
                } catch (Exception e) {
                    log.error("sync commit fail: {}", e);
                }
            }
        }
    }
}
*/
