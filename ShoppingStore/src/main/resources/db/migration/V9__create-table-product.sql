CREATE TABLE product (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    sku VARCHAR(255) UNIQUE,
    image_url TEXT,
    category_id BIGINT,
    quantity INT,
    manufacturer VARCHAR(255),
    featured BOOLEAN NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id)
);