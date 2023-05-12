CREATE TABLE record
(
    id      SERIAL,
    user_id BIGINT NOT NULL,
    text    varchar,
    PRIMARY KEY (id)
);