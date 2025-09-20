-- 1) Eliminar FK existente si está presente
SET @fk_estado_exists := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'cita'
    AND CONSTRAINT_NAME = 'fk_cita__estado'
    AND CONSTRAINT_TYPE = 'FOREIGN KEY'
);

SET @sql_drop_fk := IF(@fk_estado_exists > 0,
  'ALTER TABLE cita DROP FOREIGN KEY fk_cita__estado',
  'SELECT 1');
PREPARE stmt FROM @sql_drop_fk; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 2) Cambiar tipo de PK en cita_estado
ALTER TABLE cita_estado
    MODIFY cita_estado_id INT NOT NULL AUTO_INCREMENT;

-- 3) Cambiar tipo de columna en cita
ALTER TABLE cita
    MODIFY cita_estado_id INT NULL;

-- 4) Volver a crear la FK
ALTER TABLE cita
    ADD CONSTRAINT fk_cita__estado
    FOREIGN KEY (cita_estado_id) REFERENCES cita_estado(cita_estado_id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;
