CREATE DATABASE finance_db;
USE finance_db;

CREATE TABLE user (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE transaction (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    category_id INT,
    description TEXT,
    account_id INT,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE budget (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    amount DOUBLE NOT NULL,
    spent DOUBLE NOT NULL DEFAULT 0,
    user_id INT NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE reminder (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    bill_name VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    due_date DATE NOT NULL,
    is_paid BOOLEAN NOT NULL,
    is_notified BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);

INSERT INTO `user` (name, username, password, role) VALUES
('Admin User', 'admin', '$2a$10$8bW8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8', 'ROLE_ADMIN'), -- admin123
('John Doe', 'john', '$2a$10$123456789012345678901uQv0x.JRCZJiP3xzMR5hULC02cD7OkzO', 'ROLE_USER'), -- john123
('Jane Smith', 'jane', '$2a$10$abcdefghijABCDEFGHIJzOKzV19cJzAhFDN3H/XyLMgWQoXuQXoX0Ku', 'ROLE_USER'); -- jane123

INSERT INTO category (name, type, user_id) VALUES
('Food', 'expense', 2),
('Transport', 'expense', 2),
('Entertainment', 'expense', 2),
('Salary', 'income', 2),
('Freelance', 'income', 3),
('Health', 'expense', 3),
('Shopping', 'expense', 3);

INSERT INTO account (name, balance, user_id) VALUES
('Cash Wallet', 500.0, 2),
('Bank Account', 1500.0, 3),
('Savings', 3000.0, 2);


INSERT INTO `transaction` (user_id, type, amount, date, category_id, description, account_id) VALUES
(2, 'expense', 50.0, '2025-05-01', 1, 'Lunch', 1),
(2, 'expense', 20.0, '2025-05-02', 2, 'Bus ticket', 1),
(2, 'income', 2000.0, '2025-05-03', 4, 'Monthly salary', 2),
(2, 'expense', 100.0, '2025-05-04', 3, 'Movie night', 2),
(3, 'income', 500.0, '2025-05-05', 5, 'Freelance project', 2),
(3, 'expense', 80.0, '2025-05-06', 6, 'Medical checkup', 1),
(3, 'expense', 150.0, '2025-05-07', 7, 'Online shopping', 2);


INSERT INTO budget (category_id, amount, spent, user_id, status) VALUES
(1, 300.0, 70.0,2, 5000),
(2, 100.0, 20.0, 3, 4000),
(6, 200.0, 80.0, 2, 3000),
(7, 400.0, 150.0, 3, 2000);

INSERT INTO reminder (user_id, bill_name, amount, due_date, is_paid) VALUES
(2, 'Electricity Bill', 200.0, '2025-06-01', false),
(3, 'Water Bill', 100.0, '2025-06-05', true),
(2, 'Internet Bill', 150.0, '2025-06-10', false),
<<<<<<< HEAD
(3, 'Netflix Subscription', 200.0, '2025-06-15', true);
=======
(3, 'Netflix Subscription', 200.0, '2025-06-15', true);
>>>>>>> c63f22338caace770f0d0f672001a6dbc40ff4e7
