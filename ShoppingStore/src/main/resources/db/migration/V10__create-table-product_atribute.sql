CREATE TABLE product_attribute (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    "value" TEXT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);