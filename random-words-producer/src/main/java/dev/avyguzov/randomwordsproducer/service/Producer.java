package dev.avyguzov.randomwordsproducer.service;

import dev.avyguzov.randomwordsproducer.domain.RandomWordMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private final KafkaTemplate<String, RandomWordMessage> kafkaTemplate;
    private final WordsGenerator wordsGenerator;

    @Value("${topic-name}")
    private String topicName;
    @Value("${message-timeout}")
    private Integer timeout;

    private volatile boolean isContinue = true;

    @Autowired
    public Producer(KafkaTemplate<String, RandomWordMessage> kafkaTemplate, WordsGenerator wordsGenerator) {
        this.kafkaTemplate = kafkaTemplate;
        this.wordsGenerator = wordsGenerator;
    }

    public void startProducing() {
        isContinue = true;
        startNewThread();
    }

    private void startNewThread() {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            logger.info("Starting producing messages");

            service.execute(() -> {
                while (isContinue) {
                    String word = wordsGenerator.generateWord();
                    logger.info("Message: {} is going to be published", word);

                    ListenableFuture<SendResult<String, RandomWordMessage>> future = kafkaTemplate.send(topicName, new RandomWordMessage(word));
                    future.addCallback(new KafkaSendCallback<>() {

                        @Override
                        public void onSuccess(SendResult<String, RandomWordMessage> result) {
                            handleSuccess(result);
                        }

                        @Override
                        public void onFailure(KafkaProducerException ex) {
                            handleSuccess(ex);
                        }
                    });
                    try {
                        Thread.sleep(timeout);
                    } catch (InterruptedException e) {
                        logger.error("Error while generating words! {}", e.getMessage());
                        break;
                    }
                }

                logger.info("End of generating words");
            });
        } finally {
            if(service != null) service.shutdown();
        }
    }

    public void stopProducing() {
        logger.info("Stopping producing messages");
        isContinue = false;
    }

    private void handleSuccess(SendResult<String, RandomWordMessage> result) {
        logger.info("Word with key {} has successfully sent", result.getProducerRecord().key());
    }

    private void handleSuccess(KafkaProducerException ex) {
        logger.error("Word with key {} couldn't send. Value: {}",  ex.getFailedProducerRecord().key(), ex.getFailedProducerRecord().value());
    }
}
