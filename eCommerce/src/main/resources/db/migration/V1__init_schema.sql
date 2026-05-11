-- src/main/resources/db/migration/V1__init_schema.sql

CREATE TABLE users (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  NOT NULL DEFAULT 'BUYER',
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE products (
    id              UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(255)   NOT NULL,
    description     TEXT,
    price           NUMERIC(10, 2) NOT NULL,
    stock_quantity  INTEGER        NOT NULL DEFAULT 0,
    seller_id       UUID           NOT NULL REFERENCES users(id),
    version         BIGINT         NOT NULL DEFAULT 0,  -- optimistic lock
    created_at      TIMESTAMP      NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP      NOT NULL DEFAULT now()
);

CREATE INDEX idx_products_seller ON products(seller_id);
CREATE INDEX idx_products_price  ON products(price);

-- interview: why not use SERIAL/BIGSERIAL for id?
-- UUID prevents enumeration attacks and works in distributed systems