CREATE TABLE drivers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE cars (
    id BIGSERIAL PRIMARY KEY,
    color VARCHAR(50) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    license_plate VARCHAR(10) NOT NULL UNIQUE,
    driver_id BIGINT,
    deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_driver FOREIGN KEY (driver_id) REFERENCES drivers(id)
);
