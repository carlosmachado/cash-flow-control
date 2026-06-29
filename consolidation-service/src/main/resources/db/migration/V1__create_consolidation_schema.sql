CREATE SCHEMA IF NOT EXISTS consolidation;

CREATE TABLE IF NOT EXISTS consolidation.balance
(
    id         UUID         NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL,
    currency   varchar(10)  NOT NULL,
    amount     NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS consolidation.daily_transaction
(
    id               UUID         NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    currency         varchar(10)  NOT NULL,
    amount           NUMERIC(19, 2) NOT NULL,
    transaction_id   UUID         NOT NULL,
    transaction_date TIMESTAMP    NOT NULL,
    date             date         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT daily_transaction_unq_transaction_id UNIQUE (transaction_id)
);

CREATE INDEX IF NOT EXISTS daily_transaction_idx_date ON consolidation.daily_transaction (date);

-- Seed the single running-balance row so consumers only ever lock+update it.
INSERT INTO consolidation.balance (id, created_at, updated_at, currency, amount)
VALUES ('00000000-0000-0000-0000-000000000001', now(), now(), 'BRL', 0.00)
ON CONFLICT (id) DO NOTHING;

-- Idempotency marker: a transaction is applied to the balance at most once.
CREATE TABLE IF NOT EXISTS consolidation.balance_applied
(
    transaction_id UUID      NOT NULL,
    applied_at     TIMESTAMP NOT NULL,
    PRIMARY KEY (transaction_id)
);
