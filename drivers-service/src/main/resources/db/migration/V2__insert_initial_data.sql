INSERT INTO drivers (first_name, last_name, email, phone_number, deleted)
VALUES
    ('Иван', 'Иванов', 'ivan@example.com', '+375291234567', FALSE),
    ('Петр', 'Петров', 'petr@example.com', '+375441112233', FALSE);

INSERT INTO cars (color, brand, license_plate, driver_id, deleted)
VALUES
    ('Красный', 'Toyota', '1234 AB-7', 1, FALSE),
    ('Синий', 'BMW', '5678 CD-7', 2, FALSE);
