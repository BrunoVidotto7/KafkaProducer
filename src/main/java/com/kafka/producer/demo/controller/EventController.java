package com.kafka.producer.demo.controller;

import com.kafka.producer.demo.service.KafkaMessagePublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer-app")
public class EventController {
  private final KafkaMessagePublisher publisher;

  public EventController(KafkaMessagePublisher publisher) {
    this.publisher = publisher;
  }

  @GetMapping(value = "/publish/{message}")
  public ResponseEntity<?> publishMessage(@PathVariable String message) {
    try {
      publisher.sendMessageToTopic(message);
      return ResponseEntity.ok("Message published...");
    } catch (Exception ex) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
