CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,
    event_name VARCHAR(150) NOT NULL,
    event_description VARCHAR(255),
    "timestamp" TIMESTAMP,
    user_id BIGINT,
    affected_resource VARCHAR(255),
    origin VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);