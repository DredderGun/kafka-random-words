package dev.avyguzov.randomwordsconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.avyguzov.randomwordsconsumer.db.MessageDao;
import dev.avyguzov.randomwordsconsumer.domain.RandomWordMessage;
import dev.avyguzov.randomwordsconsumer.validation.MessageValidator;
import dev.avyguzov.randomwordsconsumer.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsumerTest {
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private MessageValidator messageValidator;
    @Mock
    private MessageDao messageDao;
    private Consumer consumer;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        consumer = new Consumer(objectMapper, messageValidator, messageDao);
    }

    @Test
    public void consumerInvokeObjectMapperForDeserialization() throws JsonProcessingException {
        String msg = "TEST";
        RandomWordMessage wordMessage = new RandomWordMessage(msg);

        Mockito.when(objectMapper.readValue(Mockito.eq(msg), Mockito.eq(RandomWordMessage.class))).thenReturn(wordMessage);
        consumer.listen(msg);

        Mockito.verify(objectMapper, Mockito.times(1)).readValue(Mockito.eq(msg), Mockito.eq(RandomWordMessage.class));
    }

    @Test
    public void ifValidationErrorThenDontProcess() throws ValidationError, JsonProcessingException {
        String msg = "TEST";
        RandomWordMessage wordMessage = new RandomWordMessage(msg);

        Mockito.when(objectMapper.readValue(Mockito.eq(msg), Mockito.eq(RandomWordMessage.class))).thenReturn(wordMessage);
        Mockito.doThrow(ValidationError.class).when(messageValidator).test(Mockito.eq(wordMessage));

        consumer.listen(msg);

        Mockito.verify(messageValidator, Mockito.times(1)).test(Mockito.eq(wordMessage));
        Mockito.verify(messageDao, Mockito.times(0)).save(Mockito.eq(wordMessage));
    }

    @Test
    public void ifOkThenWriteToDb() throws JsonProcessingException, ValidationError {
        String msg = "TEST";
        RandomWordMessage wordMessage = new RandomWordMessage(msg);

        Mockito.when(objectMapper.readValue(Mockito.eq(msg), Mockito.eq(RandomWordMessage.class))).thenReturn(wordMessage);

        consumer.listen(msg);
        Mockito.verify(messageDao, Mockito.times(1)).save(Mockito.eq(wordMessage));
    }

}
