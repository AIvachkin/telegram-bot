-- liquibase formatted sql

-- changeset aivachkin:1

create table updates (
    ID SERIAL,
    ChatID SERIAL,
    Response TEXT,
    DateAndTime TIMESTAMP
);


