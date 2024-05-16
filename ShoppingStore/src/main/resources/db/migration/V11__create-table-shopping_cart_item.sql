CREATE TABLE shopping_cart_item (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price_purchase DECIMAL(10, 2) NOT NULL,
    product_id BIGINT NOT NULL,
    shopping_cart_id BIGINT NOT NULL,
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);