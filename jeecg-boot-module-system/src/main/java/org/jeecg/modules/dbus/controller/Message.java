package org.jeecg.modules.dbus.controller;

import lombok.Data;

@Data
public class Message {
    private String topic;
    private String message;
}
