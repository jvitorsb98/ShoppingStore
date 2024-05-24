CREATE TABLE mail (
    id BIGSERIAL PRIMARY KEY,
    sender VARCHAR(255),
    recipient VARCHAR(255),
    content TEXT,
    subject VARCHAR(255)
);
