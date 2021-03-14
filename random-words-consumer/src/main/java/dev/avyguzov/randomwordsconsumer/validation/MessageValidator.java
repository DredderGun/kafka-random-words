package dev.avyguzov.randomwordsconsumer.validation;

import dev.avyguzov.randomwordsconsumer.domain.RandomWordMessage;
import org.springframework.stereotype.Component;

@Component
public class MessageValidator implements Validation<RandomWordMessage>{

    public void test(RandomWordMessage msg) throws ValidationError {
        if (msg.getWord().length() > 100) {
            throw new ValidationError("Size must be less that 100");
        }
        if (!msg.getWord().matches("[A-Za-z]*")) {
            throw new ValidationError("Text must contains only letters");
        }
    }
}
