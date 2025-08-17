INSERT INTO trips (id, driver_id, passenger_id, from_address, to_address, status, order_time, price,deleted)
VALUES ('trip-1', 'driver-1', 'passenger-1', 'Street A, 1', 'Street B, 10', 'CREATED', toTimestamp(now()), 15.50,false)
IF NOT EXISTS;

INSERT INTO trips (id, driver_id, passenger_id, from_address, to_address, status, order_time, price,deleted)
VALUES ('trip-2', 'driver-2', 'passenger-2', 'Street C, 5', 'Street D, 20', 'CREATED', toTimestamp(now()), 22.75, false)
IF NOT EXISTS;