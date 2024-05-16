CREATE TABLE shopping_cart (
    id BIGSERIAL PRIMARY KEY,
    total_price DECIMAL(10, 2) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);