package dev.avyguzov.randomwordsconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.avyguzov.randomwordsconsumer.db.MessageDao;
import dev.avyguzov.randomwordsconsumer.domain.RandomWordMessage;
import dev.avyguzov.randomwordsconsumer.validation.MessageValidator;
import dev.avyguzov.randomwordsconsumer.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final ObjectMapper objectMapper;
    private final MessageValidator validator;
    private final MessageDao messageDao;

    @Autowired
    public Consumer(ObjectMapper objectMapper, MessageValidator validator, MessageDao messageDao) {
        this.objectMapper = objectMapper;
        this.validator = validator;
        this.messageDao = messageDao;
    }

    @KafkaListener(topics = "pipe-example-topic", groupId = "group")
    public void listen(String message) {
        logger.info("Start parsing message: {}", message);
        try {
            RandomWordMessage randomWordMessage = objectMapper.readValue(message, RandomWordMessage.class);
            validator.test(randomWordMessage);
            messageDao.save(randomWordMessage);
        } catch (JsonProcessingException jsonProcessingException) {
            logger.error("Error while parsing message: {}", jsonProcessingException.getMessage());
            jsonProcessingException.printStackTrace();
        } catch (ValidationError validationError) {
            logger.error("Error while validating message: {}", validationError.getMessage());
        } catch (Exception ex) {
            logger.error("Error while processong message: {}", ex.getMessage());
        }
    }


}
