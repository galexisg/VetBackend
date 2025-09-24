-- 1) Buscar si existe la FK
SET @fk_exists := (
  SELECT COUNT(*)
  FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA = DATABASE()
    AND TABLE_NAME = 'cita'
    AND CONSTRAINT_TYPE = 'FOREIGN KEY'
    AND CONSTRAINT_NAME = 'fk_cita_estado'
);

-- 2) Solo eliminar si existe
SET @sql := IF(@fk_exists > 0,
               'ALTER TABLE cita DROP FOREIGN KEY fk_cita_estado',
               'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 3) Modificar columna
ALTER TABLE cita MODIFY cita_estado_id INT NULL;

-- 4) Agregar FK nueva
ALTER TABLE cita ADD CONSTRAINT fk_cita_estado
FOREIGN KEY (cita_estado_id) REFERENCES cita_estado(cita_estado_id)
ON UPDATE CASCADE
ON DELETE SET NULL;
