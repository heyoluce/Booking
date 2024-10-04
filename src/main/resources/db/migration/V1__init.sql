CREATE TABLE hotel
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255)   NOT NULL,
    city            VARCHAR(255)   NOT NULL,
    description     TEXT,
    price_per_night DECIMAL(10, 2) NOT NULL,
    rating          DECIMAL(2, 1)  CHECK (rating >= 1.0 AND rating <= 5.0) NOT NULL
);

CREATE TABLE my_user  -- Updated table name for consistency
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(50) UNIQUE  NOT NULL,
    password   VARCHAR(255)        NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(100),
    last_name  VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE booking
(
    id             SERIAL PRIMARY KEY,
    user_id        INT            NOT NULL,
    hotel_id       INT            NOT NULL, -- Changed room_id to hotel_id for consistency with the review table
    check_in_date  DATE           NOT NULL,
    check_out_date DATE           NOT NULL,
    total_price    DECIMAL(10, 2) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES my_user (id) ON DELETE CASCADE, -- Updated to my_user
    FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE -- Added reference to hotel
);

CREATE TABLE review
(
    id         SERIAL PRIMARY KEY,
    hotel_id   INT NOT NULL,
    user_id    INT NOT NULL,
    rating     INT CHECK (rating >= 1 AND rating <= 5) NOT NULL,
    comment    TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES my_user (id) ON DELETE CASCADE -- Updated to my_user
);

CREATE TABLE image
(
    id         SERIAL PRIMARY KEY,
    hotel_id   INT          NOT NULL,
    image_url  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE
);
