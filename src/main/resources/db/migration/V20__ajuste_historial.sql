-- V18__ajuste_cita_estado_id.sql

-- 1) Eliminar la FK desde cita -> cita_estado (si existe)
ALTER TABLE cita
    DROP FOREIGN KEY fk_cita__estado;

-- 2) Cambiar tipo de PK en cita_estado
ALTER TABLE cita_estado
    MODIFY cita_estado_id INT NOT NULL AUTO_INCREMENT;

-- 3) Cambiar tipo de columna en cita (para que coincida con INT)
ALTER TABLE cita
    MODIFY cita_estado_id INT NULL;

-- 4) Volver a crear la FK con el nuevo tipo
ALTER TABLE cita
    ADD CONSTRAINT fk_cita__estado
    FOREIGN KEY (cita_estado_id) REFERENCES cita_estado(cita_estado_id);
