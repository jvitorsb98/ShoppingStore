CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    activated BOOLEAN
);

INSERT INTO users (login, name, email, password, activated) VALUES ('admin', 'admin', 'admin@admin.com', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.',true);

