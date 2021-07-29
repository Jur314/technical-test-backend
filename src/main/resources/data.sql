DROP TABLE IF EXISTS wallets;

CREATE TABLE wallets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    current_balance NUMERIC
);

INSERT INTO wallets (current_balance) VALUES
    (9.00),
    (5.00);