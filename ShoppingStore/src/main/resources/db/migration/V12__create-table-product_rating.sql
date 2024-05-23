CREATE TABLE product_rating (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT,
    rating_stars DECIMAL(4,2) NOT NULL,
    review TEXT,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);