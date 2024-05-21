CREATE TABLE mail (
    id BIGSERIAL PRIMARY KEY,
    "from" VARCHAR(255),
    "to" VARCHAR(255),
    content TEXT,
    subject VARCHAR(255)
);
