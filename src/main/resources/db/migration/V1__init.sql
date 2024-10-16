CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS hotels
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(255)                                          NOT NULL,
    city            VARCHAR(255)                                          NOT NULL,
    description     TEXT,
    price_per_night DECIMAL(10, 2)                                        NOT NULL,
    rating          DECIMAL(2, 1) CHECK (rating >= 1.0 AND rating <= 5.0) NOT NULL,
    total_rooms INT NOT NULL,
    booked_rooms INT

);

CREATE TABLE IF NOT EXISTS bookings
(
    id             BIGSERIAL PRIMARY KEY,
    user_id        BIGINT         NOT NULL,
    hotel_id       BIGINT         NOT NULL,
    check_in_date  DATE           NOT NULL,
    check_out_date DATE           NOT NULL,
    total_price    DECIMAL(10, 2) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN default true,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews
(
    id         BIGSERIAL PRIMARY KEY,
    hotel_id   BIGINT                                  NOT NULL,
    user_id    BIGINT                                  NOT NULL,
    rating     INT CHECK (rating >= 1 AND rating <= 5) NOT NULL,
    comment    TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hotels_images
(
    hotel_id BIGINT       NOT NULL,
    image    VARCHAR(255) NOT NULL,
    CONSTRAINT fk_hotels_images_hotels FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT       NOT NULL,
    role    VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_users_roles_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
