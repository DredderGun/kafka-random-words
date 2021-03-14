package dev.avyguzov.randomwordsproducer.api;

import dev.avyguzov.randomwordsproducer.service.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class ProducerController {
    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private final Producer producer;

    @Autowired
    ProducerController(Producer producer) {
        this.producer = producer;
    }

    @PostMapping(value = "/start")
    public String start() {
        logger.info("Request for generating words");
        this.producer.startProducing();
        return "Producer has been started";
    }

    @PostMapping(value = "/stop")
    public String stop() {
        logger.info("Request for stop generating words");
        this.producer.stopProducing();
        return "Producer has been stopped";
    }
}
