package org.example.stub.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stub.entity.MessageEntity;
import org.example.stub.repository.MessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MessageRepository messageRepository;
    private final DelayServise delayServise;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @KafkaListener(topics = "message-topic",groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message){
        String timestamp = LocalDateTime.now().format(FORMATTER);
        log.info("{} - [Read from Kafka] {}", timestamp, message);

        try {
            JsonNode json = objectMapper.readTree(message);

            String msgUuid = json.get("msg_uuid").asText();
            Boolean head = json.get("head").asBoolean();

            delayServise.sleep();

            Long timeRq = Instant.now().getEpochSecond();


            MessageEntity entity = new MessageEntity();
            entity.setMsgUuid(msgUuid);
            entity.setHead(head);
            entity.setTimeRq(timeRq);

            messageRepository.save(entity);

            log.info("{} - [Write to BD]  {{\"msgUuid\": \"{}\" \"head\": {}, \"timeRq\": \"{}\"}}", timestamp, msgUuid, head, timeRq);
        } catch (InterruptedException e){
            log.error("Задержка была прервана", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Ошибка при обработке сообщения {} - {}", e.getMessage(), message, e);


        }
    }
}
