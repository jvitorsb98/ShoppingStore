CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    shopping_cart_id BIGINT NOT NULL,
    payment_type VARCHAR(50) NOT NULL,
    disabled BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id)
);