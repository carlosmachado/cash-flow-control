CREATE SCHEMA IF NOT EXISTS transaction;

CREATE TABLE IF NOT EXISTS transaction.transaction
(
    id               UUID         NOT NULL,
    type             varchar(50)  NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    transaction_date TIMESTAMP    NOT NULL,
    currency         varchar(10)  NOT NULL,
    amount           NUMERIC(19, 2) NOT NULL,
    description      text         NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS transaction_idx_type ON transaction.transaction (type);
CREATE INDEX IF NOT EXISTS transaction_idx_created_at ON transaction.transaction (created_at);
CREATE INDEX IF NOT EXISTS transaction_idx_transaction_date ON transaction.transaction (transaction_date);

CREATE TABLE IF NOT EXISTS transaction.outbox
(
    id            UUID         NOT NULL,
    aggregate     varchar(500) NOT NULL,
    aggregate_id  varchar(500),
    created_at    TIMESTAMP    NOT NULL,
    dispatched    boolean      NOT NULL,
    dispatched_at TIMESTAMP,
    message       text         NOT NULL,
    operation     varchar(500) NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS outbox_idx_dispatched_created_at ON transaction.outbox (dispatched, created_at);
