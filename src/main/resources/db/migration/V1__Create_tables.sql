CREATE SCHEMA IF NOT EXISTS cash_flow;

CREATE TABLE IF NOT EXISTS cash_flow.transaction
(
    id UUID NOT NULL,
    type varchar(50) not null,
    created_at TIMESTAMP NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    currency varchar(10) NOT NULL,
    amount  NUMERIC(19, 2) not null,
    description text not null,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS transaction_idx_type ON cash_flow.transaction (type);
CREATE INDEX IF NOT EXISTS transaction_idx_created_at ON cash_flow.transaction (created_at);
CREATE INDEX IF NOT EXISTS transaction_idx_transaction_date ON cash_flow.transaction (transaction_date);

CREATE TABLE IF NOT EXISTS cash_flow.balance
(
    id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    currency varchar(10) NOT NULL,
    amount  NUMERIC(19, 2) not null,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cash_flow.daily_transaction
(
    id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    currency varchar(10) NOT NULL,
    amount  NUMERIC(19, 2) not null,
    transaction_id UUID NOT NULL,
    transaction_date timestamp NOT NULL,
    date date NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT daily_transaction_unq_transaction_id UNIQUE (transaction_id)
);

CREATE INDEX IF NOT EXISTS daily_transaction_idx_date ON cash_flow.daily_transaction (date);


CREATE TABLE IF NOT EXISTS cash_flow.outbox
(
    id UUID NOT NULL,
    aggregate varchar(500) NOT NULL,
    aggregate_id varchar(500),
    created_at TIMESTAMP NOT NULL,
    dispatched boolean NOT NULL,
    dispatched_at TIMESTAMP,
    message text NOT NULL,
    operation varchar(500) NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS OUTBOX_IDX_DISPATCHED_CREATED_AT on cash_flow.outbox (dispatched, created_at);
