CREATE TABLE IF NOT EXISTS transaction.shedlock
(
    name       varchar(64)  NOT NULL,
    lock_until TIMESTAMP    NOT NULL,
    locked_at  TIMESTAMP    NOT NULL,
    locked_by  varchar(255) NOT NULL,
    PRIMARY KEY (name)
);
