CREATE TABLE tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT,
    expire_date TIMESTAMP WITH TIME ZONE NOT NULL,
    disabled BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

