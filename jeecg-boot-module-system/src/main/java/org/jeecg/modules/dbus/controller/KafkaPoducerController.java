package org.jeecg.modules.dbus.controller;

import org.jeecg.modules.dbus.producer.KafkaProducerUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/kafka/test/")
public class KafkaPoducerController {
//    @Resource
//    private KafkaProducerUtil kafkaProducerUtil;
//
//    @PostMapping("push")
//    public String testPush(@RequestBody Message message){
//        kafkaProducerUtil.sendMessage(message.getTopic(),message.getMessage());
//        return "Poducer push message success,topic= "+message.getTopic()+",message ="+message.getMessage();
//    }
}
