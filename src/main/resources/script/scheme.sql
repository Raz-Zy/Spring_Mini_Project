CREATE DATABASE spring_mini_project_db;

CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    profile_image VARCHAR(255)
);

CREATE TABLE otps(
    opt_id SERIAL PRIMARY KEY,
    opt_code VARCHAR,
    issued_at DATE,
    expiration DATE,
    verify BOOLEAN,
    user_id INT,
    CONSTRAINT FK_opts_users FOREIGN KEY (user_id) REFERENCES users(user_id)
                 ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE categories(
    category_id SERIAL PRIMARY KEY,
    name VARCHAR,
    description VARCHAR,
    user_id INT,
    CONSTRAINT FK_categories_users FOREIGN KEY (user_id) REFERENCES users(user_id)
                ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE expenses(
    expense_id SERIAL PRIMARY KEY,
    amount FLOAT,
    description VARCHAR,
    date DATE,
    user_id INT,
    category_id INT,
    CONSTRAINT FK_expenses_users FOREIGN KEY (user_id) REFERENCES users(user_id)
                ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_expenses_categories FOREIGN KEY (category_id) REFERENCES categories(category_id)
                ON DELETE CASCADE ON UPDATE CASCADE
);