DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100001, TIMESTAMP '2011-05-16 09:00:00', 'Завтрак админ', 300),
  (100001, TIMESTAMP '2011-05-16 12:00:00', 'Обед админ', 1500),
  (100001, TIMESTAMP '2011-05-16 20:00:00', 'Ужин админ', 2200),
  (100001, TIMESTAMP '2011-05-17 15:00:00', 'Обед админ', 1500),

  (100000, TIMESTAMP '2011-05-16 09:00:00', 'Завтрак', 200),
  (100000, TIMESTAMP '2011-05-16 12:00:00', 'Обед', 2000),
  (100000, TIMESTAMP '2011-05-16 16:00:00', 'Ужин', 500),
  (100000, TIMESTAMP '2011-05-17 09:00:00', 'Завтрак', 300),
  (100000, TIMESTAMP '2011-05-17 12:00:00', 'Обед', 1500),
  (100000, TIMESTAMP '2011-05-17 20:00:00', 'Ужин', 300),
  (100000, TIMESTAMP '2011-05-18 09:00:00', 'Завтрак', 1500),
  (100000, TIMESTAMP '2011-05-18 22:00:00', 'Ужин', 200);
