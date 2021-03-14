package dev.avyguzov.randomwordsconsumer.validation;

import dev.avyguzov.randomwordsconsumer.domain.RandomWordMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationTest {
    private final MessageValidator validation = new MessageValidator();

    @Test
    public void mustThrowWhenNumbersInString() {
        RandomWordMessage msg = new RandomWordMessage("123sdgdfg");
        assertThrows(ValidationError.class, () -> validation.test(msg));
    }

    @Test
    public void lengthMustBeLessThan100() {
        RandomWordMessage msg = new RandomWordMessage("4qhAoNIiFRYG9lRurz48gfUsH29DDeHNjruNAMYIm6toJONsDHnVbi3hmBdALcdFLoJSbqhDA7toyATPyOYk9LRGDM7JuCoR0AJsXZ\n");
        assertThrows(ValidationError.class, () -> validation.test(msg));
    }
}
