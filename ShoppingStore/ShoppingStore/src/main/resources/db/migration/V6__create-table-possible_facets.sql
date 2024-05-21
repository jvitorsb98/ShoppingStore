CREATE TABLE possible_facets (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    category_id BIGINT,
    FOREIGN KEY (category_id) REFERENCES category (id)
);