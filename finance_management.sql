CREATE DATABASE finance_db;
USE finance_db;

CREATE TABLE user (
    user_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE category (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE TABLE account (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    balance DOUBLE NOT NULL
);

CREATE TABLE transaction (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50),
    type VARCHAR(20) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    category_id VARCHAR(50),
    description TEXT,
    account_id VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE budget (
    id VARCHAR(50) PRIMARY KEY,
    category_id VARCHAR(50),
    amount DOUBLE NOT NULL,
    spent DOUBLE NOT NULL DEFAULT 0,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE reminder (
    id VARCHAR(50) PRIMARY KEY,
    bill_name VARCHAR(100) NOT NULL,
    amount DOUBLE NOT NULL,
    due_date DATE NOT NULL,
    is_paid BOOLEAN NOT NULL
);

-- Dữ liệu mẫu
INSERT INTO user (user_id, name, username, password, role) 
VALUES ('user1', 'Admin User', 'admin', '$2a$10$8bW8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8z9z8', 'ROLE_ADMIN');
-- Mật khẩu: admin123 (đã mã hóa bằng BCrypt)