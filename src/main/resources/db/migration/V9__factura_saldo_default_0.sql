-- V9__factura_saldo_default_0.sql
-- Aseguro saldo NOT NULL con default 0.00 y normalizo valores existentes
UPDATE factura SET saldo = 0.00 WHERE saldo IS NULL;

ALTER TABLE factura
    MODIFY saldo DECIMAL(12,2) NOT NULL DEFAULT 0.00;
