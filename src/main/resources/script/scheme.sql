CREATE DATABASE spring_mini_project_db;


CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users(
                      user_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY ,
                      email VARCHAR(255),
                      password VARCHAR(255),
                      profile_image VARCHAR(255)
);

CREATE TABLE otps(
                     opt_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                     otp_code VARCHAR,
                     issued_at TIMESTAMP,
                     expiration TIMESTAMP,
                     verify BOOLEAN,
                     user_id UUID,
                     CONSTRAINT opts_users_FK FOREIGN KEY (user_id) REFERENCES users(user_id)
                         ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE categories(
                           category_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                           name VARCHAR,
                           description VARCHAR,
                           user_id UUID,
                           CONSTRAINT FK_categories_users FOREIGN KEY (user_id) REFERENCES users(user_id)
                               ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE expenses(
                         expense_id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
                         amount FLOAT,
                         description VARCHAR,
                         date DATE,
                         user_id UUID,
                         category_id UUID,
                         CONSTRAINT FK_expenses_users FOREIGN KEY (user_id) REFERENCES users(user_id)
                             ON DELETE CASCADE ON UPDATE CASCADE,
                         CONSTRAINT FK_expenses_categories FOREIGN KEY (category_id) REFERENCES categories(category_id)
                             ON DELETE CASCADE ON UPDATE CASCADE
);