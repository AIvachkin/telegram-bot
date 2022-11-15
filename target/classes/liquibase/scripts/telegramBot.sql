-- liquibase formatted sql

-- changeset aivachkin:1

create table updates (
    ID SERIAL,
    ChatID SERIAL,
    Response TEXT,
    DateAndTime TIMESTAMP
);

-- changeset aivachkin:2

create table notification_task (
                         ID SERIAL,
                         ChatID SERIAL,
                         Response TEXT,
                         DateAndTime TIMESTAMP
);

-- changeset aivachkin:3

alter table notification_task rename column chatid to chat_id;

-- changeset aivachkin:4

alter table notification_task rename column dateandtime to date_and_time;

-- changeset aivachkin:5

drop table updates;