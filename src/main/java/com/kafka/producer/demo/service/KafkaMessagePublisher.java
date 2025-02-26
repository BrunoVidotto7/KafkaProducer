package com.kafka.producer.demo.service;

import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessagePublisher {

  private final KafkaTemplate<String,Object> template;

  public KafkaMessagePublisher(KafkaTemplate<String, Object> template) {
    this.template = template;
  }

  public void sendMessageToTopic(String message) {
    CompletableFuture<SendResult<String, Object>> future = template.send("topic-1", message);
    future.whenComplete((result,ex)-> {
      if (ex == null) {
        System.out.printf(
            "Sent message=[%s] with offset=[%d]%n%n", message, result.getRecordMetadata().offset());
      } else {
        System.out.printf("Unable to send message=[%s] due to error: %s%n", message, ex.getMessage());
      }
    });
  }
}
