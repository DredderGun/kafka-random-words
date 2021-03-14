package dev.avyguzov.randomwordsconsumer.domain;

import java.util.Objects;

public class RandomWordMessage {
    private String word;

    public RandomWordMessage() { }

    public RandomWordMessage(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomWordMessage that = (RandomWordMessage) o;
        return word.equals(that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
