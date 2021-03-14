package dev.avyguzov.randomwordsconsumer.config;

import dev.avyguzov.randomwordsconsumer.domain.RandomWordMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;

@Configuration
public class KafkaConfig {
    @Value("${topic-name}")
    public String topicName;

    @Bean
    public DefaultKafkaConsumerFactory<String, RandomWordMessage> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                new HashMap<>(),
                new StringDeserializer(),
                new JsonDeserializer<>(RandomWordMessage.class)
        );
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topicName).build();
    }
}
