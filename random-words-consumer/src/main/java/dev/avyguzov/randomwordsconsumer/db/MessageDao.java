package dev.avyguzov.randomwordsconsumer.db;

import dev.avyguzov.randomwordsconsumer.domain.RandomWordMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageDao {
    private static final Logger logger = LoggerFactory.getLogger(MessageDao.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(RandomWordMessage msg) {
        logger.info("Writing to db value: {}", msg.getWord());
        jdbcTemplate.update("insert into messages (msg_word) values (?)", msg.getWord());
    }
}
