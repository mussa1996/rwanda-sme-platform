-- =========================================================
-- Enable UUID generation (PostgreSQL)
-- =========================================================
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =========================================================
-- MERCHANTS
-- =========================================================
CREATE TABLE merchants (
    merchant_id        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    business_name      TEXT NOT NULL,
    business_category  TEXT,
    phone_number       TEXT NOT NULL,
    email              TEXT,
    address_text       TEXT,
    district           TEXT,
    sector             TEXT,
    cell               TEXT,
    village            TEXT,
    status             TEXT NOT NULL,
    created_at         TIMESTAMPTZ NOT NULL,
    updated_at         TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_merchants_phone  ON merchants(phone_number);
CREATE INDEX idx_merchants_status ON merchants(status);

-- =========================================================
-- MERCHANT USERS
-- =========================================================
CREATE TABLE merchant_users (
    merchant_user_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    merchant_id      UUID NOT NULL,
    full_name        TEXT NOT NULL,
    phone_number     TEXT NOT NULL,
    email            TEXT,
    role             TEXT NOT NULL,
    password_hash    TEXT NOT NULL,
    is_active        BOOLEAN NOT NULL,
    created_at       TIMESTAMPTZ NOT NULL,
    updated_at       TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_merchant_user_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id)
        ON DELETE CASCADE,

    CONSTRAINT uk_merchant_user_phone
        UNIQUE (merchant_id, phone_number)
);

-- =========================================================
-- MERCHANT QR CODES
-- =========================================================
CREATE TABLE merchant_qr_codes (
    merchant_qr_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    merchant_id    UUID NOT NULL,
    qr_type        TEXT NOT NULL,
    qr_payload     TEXT NOT NULL,
    is_active      BOOLEAN NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_qr_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id)
        ON DELETE CASCADE,

    CONSTRAINT uk_qr_payload UNIQUE (qr_payload)
);

-- =========================================================
-- PRODUCTS
-- =========================================================
CREATE TABLE products (
    product_id    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    merchant_id   UUID NOT NULL,
    product_name  TEXT NOT NULL,
    sku           TEXT,
    unit_price    NUMERIC(12,2) NOT NULL,
    is_active     BOOLEAN NOT NULL,
    created_at    TIMESTAMPTZ NOT NULL,
    updated_at    TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_product_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id)
        ON DELETE CASCADE,

    CONSTRAINT uk_product_name_per_merchant
        UNIQUE (merchant_id, product_name)
);

-- =========================================================
-- INVENTORY BALANCES
-- =========================================================
CREATE TABLE inventory_balances (
    inventory_balance_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    product_id           UUID NOT NULL,
    quantity_on_hand     NUMERIC(14,3) NOT NULL,
    last_updated_at      TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_inventory_balance_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_inventory_product ON inventory_balances(product_id);

-- =========================================================
-- INVENTORY MOVEMENTS
-- =========================================================
CREATE TABLE inventory_movements (
    inventory_movement_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    merchant_id           UUID NOT NULL,
    product_id            UUID NOT NULL,
    movement_type         TEXT NOT NULL,
    quantity_change       NUMERIC(14,3) NOT NULL,
    reference_type        TEXT NOT NULL,
    reference_id          UUID NOT NULL,
    created_at            TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_inventory_movement_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_inventory_movement_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_inventory_movements_merchant ON inventory_movements(merchant_id);
CREATE INDEX idx_inventory_movements_product  ON inventory_movements(product_id);

-- =========================================================
-- SALES
-- =========================================================
CREATE TABLE sales (
    sale_id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    merchant_id       UUID NOT NULL,
    sold_by_user_id   UUID,
    sale_channel      TEXT NOT NULL,
    subtotal_amount   NUMERIC(12,2) NOT NULL,
    discount_amount   NUMERIC(12,2),
    tax_amount        NUMERIC(12,2),
    total_amount      NUMERIC(12,2) NOT NULL,
    currency          TEXT NOT NULL,
    status            TEXT NOT NULL,
    created_at        TIMESTAMPTZ NOT NULL,
    updated_at        TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_sale_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id),

    CONSTRAINT fk_sale_user
        FOREIGN KEY (sold_by_user_id) REFERENCES merchant_users(merchant_user_id)
);

CREATE INDEX idx_sales_merchant ON sales(merchant_id);
CREATE INDEX idx_sales_status   ON sales(status);

-- =========================================================
-- SALE ITEMS
-- =========================================================
CREATE TABLE sale_items (
    sale_item_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sale_id      UUID NOT NULL,
    product_id   UUID NOT NULL,
    quantity     NUMERIC(14,3) NOT NULL,
    unit_price   NUMERIC(12,2) NOT NULL,
    line_total   NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_sale_item_sale
        FOREIGN KEY (sale_id) REFERENCES sales(sale_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_sale_item_product
        FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- =========================================================
-- PAYMENTS
-- =========================================================
CREATE TABLE payments (
    payment_id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sale_id          UUID,
    merchant_id      UUID NOT NULL,
    provider         TEXT NOT NULL,
    network          TEXT,
    provider_txn_id  TEXT NOT NULL,
    amount           NUMERIC(12,2) NOT NULL,
    currency         TEXT NOT NULL,
    status           TEXT NOT NULL,
    paid_at          TIMESTAMPTZ,
    created_at       TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_payment_sale
        FOREIGN KEY (sale_id) REFERENCES sales(sale_id),

    CONSTRAINT fk_payment_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id),

    CONSTRAINT uk_provider_txn UNIQUE (provider, provider_txn_id)
);

-- =========================================================
-- PAYMENT WEBHOOK EVENTS (AUDIT)
-- =========================================================
CREATE TABLE payment_webhook_events (
    webhook_event_id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    provider          TEXT NOT NULL,
    event_type        TEXT,
    provider_txn_id   TEXT,
    signature_valid   BOOLEAN NOT NULL,
    payload_json      JSONB NOT NULL,
    received_at       TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_webhook_provider_txn
    ON payment_webhook_events(provider, provider_txn_id);

-- =========================================================
-- NOTIFICATIONS
-- =========================================================
CREATE TABLE notifications (
    notification_id     UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    merchant_id         UUID,
    channel             TEXT NOT NULL,
    recipient           TEXT NOT NULL,
    message_template    TEXT NOT NULL,
    message_content     TEXT NOT NULL,
    status              TEXT NOT NULL,
    provider_message_id TEXT,
    created_at          TIMESTAMPTZ NOT NULL,
    sent_at             TIMESTAMPTZ,

    CONSTRAINT fk_notification_merchant
        FOREIGN KEY (merchant_id) REFERENCES merchants(merchant_id)
);

CREATE INDEX idx_notifications_merchant ON notifications(merchant_id);
CREATE INDEX idx_notifications_status   ON notifications(status);
