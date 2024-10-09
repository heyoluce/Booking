create table if not exists users
(
    id       bigserial primary key,
    name     varchar(255) not null,
    username varchar(255) not null unique,
    password varchar(255) not null
);

CREATE TABLE if not exists hotel
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255)   NOT NULL,
    city            VARCHAR(255)   NOT NULL,
    description     TEXT,
    price_per_night DECIMAL(10, 2) NOT NULL,
    rating          DECIMAL(2, 1)  CHECK (rating >= 1.0 AND rating <= 5.0) NOT NULL
);

CREATE TABLE if not exists booking
(
    id             SERIAL PRIMARY KEY,
    user_id        INT            NOT NULL,
    hotel_id INT NOT NULL,
    check_in_date  DATE           NOT NULL,
    check_out_date DATE           NOT NULL,
    total_price    DECIMAL(10, 2) NOT NULL,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE, -- Updated to my_user
    FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE -- Added reference to hotel
);

CREATE TABLE if not exists review
(
    id         SERIAL PRIMARY KEY,
    hotel_id   INT NOT NULL,
    user_id    INT NOT NULL,
    rating     INT CHECK (rating >= 1 AND rating <= 5) NOT NULL,
    comment    TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hotel_id) REFERENCES hotel (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE -- Updated to my_user
);


create table if not exists hotels_images
(
    hotel_id bigint       not null,
    image    varchar(255) not null,
    constraint fk_hotels_images_hotels foreign key (hotel_id) references hotel (id) on delete cascade on update no action
);
