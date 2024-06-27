CREATE TABLE users
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    status    VARCHAR(10)  NOT NULL DEFAULT 'ACTIVE',
    user_role VARCHAR(20)  NOT NULL
);

CREATE TABLE files
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(255),
    status   VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'
);

CREATE TABLE events
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    file_id BIGINT,
    status  VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_events_on_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_events_on_files FOREIGN KEY (file_id) REFERENCES files (id)
);
