INSERT INTO passengers (
    id, first_name, last_name, email, phone_number, deleted, created_at, updated_at
) VALUES
      (1, 'Иван', 'Иванов', 'ivan.ivanov@example.com',
       '+375333333333', false, '2025-08-12 12:00:00',
       '2025-08-12 12:00:00'
      ),
      (2, 'Пётр', 'Петров', 'petr.petrov@example.com',
       '+375334444444', false, '2025-08-12 12:00:00',
       '2025-08-12 12:00:00'
      );

SELECT setval(pg_get_serial_sequence('passengers','id'), (SELECT MAX(id) FROM passengers));
