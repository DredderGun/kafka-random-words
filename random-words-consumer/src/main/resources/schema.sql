create schema if not exists wordstest AUTHORIZATION wordstest;

create table if not exists wordstest.messages (
    id serial PRIMARY KEY,
    msg_word varchar(255) NOT NULL,
    msg_date date NOT NULL DEFAULT CURRENT_DATE
);