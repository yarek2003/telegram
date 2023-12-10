-- liquibase formatted sql

-- changeset yarek2003:1
create table notification_task(
                                  notification_task_id bigserial    primary key ,
                                  chat_id bigint     not null ,
                                  text_message text not null ,
                                  schedule_date_time timestamp not null
);

