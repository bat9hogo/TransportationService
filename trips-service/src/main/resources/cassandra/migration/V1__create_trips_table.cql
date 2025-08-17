CREATE TABLE IF NOT EXISTS trips_db.trips (
    id text PRIMARY KEY,
    driver_id text,
    passenger_id text,
    from_address text,
    to_address text,
    status text,
    order_time timestamp,
    price decimal,
    deleted boolean
);
